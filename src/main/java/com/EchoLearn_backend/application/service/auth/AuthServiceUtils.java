package com.EchoLearn_backend.application.service.auth;

import com.EchoLearn_backend.infraestructure.adapter.entity.TokenResetPassword;
import com.EchoLearn_backend.infraestructure.adapter.repository.TokenResetPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class AuthServiceUtils {
    @Autowired
    private final TokenResetPasswordRepository tokenResetPasswordRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceUtils(TokenResetPasswordRepository tokenResetPasswordRepository, PasswordEncoder passwordEncoder) {
        this.tokenResetPasswordRepository = tokenResetPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Integer generateTokenResetPassword(){
        Random random = new Random();
        Integer token = 1000 + random.nextInt(9000);

        if (this.tokenResetPasswordRepository.existsByToken(token)){
            generateTokenResetPassword();
        }

        return token;
    }

    public TokenResetPassword getToken(Integer token){
        return this.tokenResetPasswordRepository.findById(token)
                .orElseThrow(() -> new UsernameNotFoundException("TOKEN NO EXIST"));
    }

    public Boolean existTokenByEmail(String email) {
        return this.tokenResetPasswordRepository.existsByEmail(email);
    }
    public Boolean existToken(Integer code) {
        return this.tokenResetPasswordRepository.existsByToken(code);
    }

    public LocalDateTime generateExpirationDate() {
        return LocalDateTime.now().plusMinutes(10);
    }

    public void saveTokenResetPassword(TokenResetPassword tokenResetPassword){
        this.tokenResetPasswordRepository.save(tokenResetPassword);
    }

    public void deleteTokenUsed(Integer token){
        this.tokenResetPasswordRepository.deleteById(token);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteAllTokenExpired(){
         this.tokenResetPasswordRepository.deleteByExpiredTimeBefore(LocalDateTime.now());
    }

    public String encriptPasword(String password){
        return this.passwordEncoder.encode(password);
    }
}
