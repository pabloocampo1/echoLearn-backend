package com.EchoLearn_backend.application.service.auth;


import com.EchoLearn_backend.Exception.ResourceNotFoundException;
import com.EchoLearn_backend.domain.model.User;
import com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl.UserSpringJpaAdapter;
import com.EchoLearn_backend.infraestructure.adapter.entity.TokenResetPassword;
import com.EchoLearn_backend.infraestructure.adapter.entity.UserEntity;
import com.EchoLearn_backend.infraestructure.adapter.mapper.UserDboMapper;
import com.EchoLearn_backend.infraestructure.externalServices.SendEmailResetToken;
import com.EchoLearn_backend.infraestructure.rest.dto.auth.AuthResponse;
import com.EchoLearn_backend.infraestructure.rest.dto.auth.RequestResetPassword;
import com.EchoLearn_backend.infraestructure.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthService {

    private final UserSpringJpaAdapter userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final AuthServiceUtils authServiceUtils;
    private final SendEmailResetToken sendEmailResetToken;
    private final UserDboMapper userDboMapper;

    @Autowired
    public AuthService(UserSpringJpaAdapter userRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager, AuthServiceUtils authServiceUtils, SendEmailResetToken sendEmailResetToken, UserDboMapper userDboMapper) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.authServiceUtils = authServiceUtils;
        this.sendEmailResetToken = sendEmailResetToken;
        this.userDboMapper = userDboMapper;
    }

    public AuthResponse login(@Valid String username, @Valid String password) {
        try {

            Authentication authentication = this.isAuthenticate(username, password);

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String rolesAndAuthorities = user.getAuthorities().toString();
            UserEntity userEntity = this.userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("No user find"));

            String jwt = this.generateToken(authentication, rolesAndAuthorities);
            return AuthResponse
                    .builder()
                    .username(username)
                    .user_id(userEntity.getId())
                    .message("Se auntentico correctamente")
                    .jwt(jwt)
                    .isAuthenticate(true)
                    .roles(rolesAndAuthorities)
                    .timestamp(LocalDateTime.now())
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("username or password are incorrect");
        } catch (DisabledException e) {
            throw new DisabledException("usuario disable " + username);
        } catch (LockedException e) {
            throw new LockedException("user locked " + username);
        } catch (Exception e) {
            log.error(e.getMessage());
            return AuthResponse.builder()
                    .username(username)
                    .message("logged not success")
                    .jwt(null)
                    .isAuthenticate(false)
                    .roles(null)
                    .build();
        }
    }

    public Authentication isAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        return this.authenticationManager.authenticate(login);
    }

    public String generateToken(Authentication authentication, String rolesAndAuthories) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return this.jwtUtils.createJwt(user.getUsername(), rolesAndAuthories);
    }


    public Boolean generateTokenResetPassword(String email) {
        this.authServiceUtils.deleteAllTokenExpired();
        // search if the user exist with that email
        if (!this.userRepository.existByEmail(email)) {
            throw new IllegalArgumentException("no exits");
        } else {
            try {
                if (this.authServiceUtils.existTokenByEmail(email)) {
                    throw new LockedException("user already have token");

                }
                // create the token
                Integer token = this.authServiceUtils.generateTokenResetPassword();
                LocalDateTime expirationTimeToken = this.authServiceUtils.generateExpirationDate();
                // create the token after save
                TokenResetPassword tokenResetPassword = new TokenResetPassword(token, email, expirationTimeToken);

                this.authServiceUtils.saveTokenResetPassword(tokenResetPassword);

                return this.sendEmailResetToken.sendHtmlEmail(token, email);
            } catch (Exception e) {
                return false;
            }
        }

    }


    public Boolean resetPassword(@Valid RequestResetPassword requestResetPassword) {

        this.authServiceUtils.deleteAllTokenExpired();
        if (requestResetPassword.getPassword().length() <= 5) {
            throw new IllegalArgumentException("PASSWORD TOO SHORT");
        }
        try {
            TokenResetPassword tokenResetPassword = this.authServiceUtils.getToken(requestResetPassword.getToken());
            User user = this.userRepository.findByEmail(tokenResetPassword.getEmail());
            user.setPassword(this.authServiceUtils.encriptPasword(requestResetPassword.getPassword()));
            this.userRepository.updatePasswordUser(user);
            this.authServiceUtils.deleteTokenUsed(tokenResetPassword.getToken());
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean codeValidResetToken(Integer code){
        return this.authServiceUtils.existToken(code);
    }



}
