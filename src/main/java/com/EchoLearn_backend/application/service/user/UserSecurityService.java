package com.EchoLearn_backend.application.service.user;

import com.EchoLearn_backend.infraestructure.rest.dto.auth.AuthResponse;
import com.EchoLearn_backend.infraestructure.adapter.entity.UserEntity;
import com.EchoLearn_backend.infraestructure.adapter.adapterJpaImpl.UserSpringJpaAdapter;
import com.EchoLearn_backend.infraestructure.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserSecurityService implements UserDetailsService {

    private final UserSpringJpaAdapter userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserSecurityService(UserSpringJpaAdapter userRepository, JwtUtils jwtUtils, @Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(@Valid String username, @Valid String password){
        try{
            Authentication authentication = this.isAuthenticate( username,  password);

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String rolesAndAuthorities = user.getAuthorities().toString();


            String jwt = this.generateToken(authentication, rolesAndAuthorities);
            return AuthResponse
                    .builder()
                    .username(username)
                    .message("Se auntentico correctamente")
                    .jwt(jwt)
                    .isAuthenticate(true)
                    .roles(rolesAndAuthorities)
                    .timestamp(LocalDateTime.now())
                    .build();
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("username or password are incorrect");
        }catch (DisabledException e){
            throw new DisabledException("usuario disable " +username);
        }catch (LockedException e ) {
            throw new LockedException("user locked " + username);
        }catch (Exception e) {
            log.error(e.getMessage());
            return  AuthResponse.builder()
                    .username(username)
                    .message("logged not success")
                    .jwt(null)
                    .isAuthenticate(false)
                    .roles(null)
                    .build();
        }
    }

    public Authentication isAuthenticate(String username, String password){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(
                username,
                password
        );
        return this.authenticationManager.authenticate(login);
    }

    public String generateToken(Authentication authentication, String rolesAndAuthories){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return this.jwtUtils.createJwt(user.getUsername(), rolesAndAuthories);
    };

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("NOT FOUND USER"));

        return User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName_role()))
                .build();
    }
}
