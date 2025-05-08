package com.EchoLearn_backend.infraestructure.rest.controler;

import com.EchoLearn_backend.application.service.auth.AuthService;
import com.EchoLearn_backend.infraestructure.rest.dto.auth.AuthResponse;
import com.EchoLearn_backend.infraestructure.rest.dto.auth.LoginDto;
import com.EchoLearn_backend.infraestructure.rest.dto.auth.RequestResetPassword;
import com.EchoLearn_backend.infraestructure.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private final AuthService authService;

    @Autowired
    private final JwtUtils jwtUtils;
    public AuthController( AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto){
        try{
            return new ResponseEntity<>(this.authService.login(loginDto.getUsername(), loginDto.getPassword()),HttpStatus.ACCEPTED );
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

    @GetMapping("/generateTokenResetPassword/{email}")
    public ResponseEntity<Boolean> generateTokenResetPassword(@PathVariable("email") String email){
        try{
            Boolean response = this.authService.generateTokenResetPassword(email);
            if (response){
                return  new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                return  new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/codeValid/{code}")
    public ResponseEntity<Boolean> codeValid(@Valid @PathVariable("code") Integer code){
        try{
            if(this.authService.codeValidResetToken(code)){
                return new ResponseEntity<>( true, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
           return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody RequestResetPassword requestResetPassword){
        try{
            if(this.authService.resetPassword(requestResetPassword)){
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }


}
