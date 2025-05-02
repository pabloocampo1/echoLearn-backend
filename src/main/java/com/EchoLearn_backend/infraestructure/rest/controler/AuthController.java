package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.service.user.UserSecurityService;
import com.EchoLearn_backend.infraestructure.rest.dto.auth.AuthResponse;
import com.EchoLearn_backend.infraestructure.rest.dto.auth.LoginDto;
import com.EchoLearn_backend.infraestructure.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private final UserSecurityService userSecurityService;

    @Autowired
    private final JwtUtils jwtUtils;
    public AuthController(UserSecurityService userSecurityService, JwtUtils jwtUtils) {
        this.userSecurityService = userSecurityService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
        try{
            return new ResponseEntity<>(this.userSecurityService.login(loginDto.getUsername(), loginDto.getPassword()),HttpStatus.ACCEPTED );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/isValidJwt")
    public ResponseEntity<Boolean> isValidJwt(){
        try{
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
