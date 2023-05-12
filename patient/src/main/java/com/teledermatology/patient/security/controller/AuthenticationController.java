package com.teledermatology.patient.security.controller;

import com.teledermatology.patient.security.model.AuthenticationRequest;
import com.teledermatology.patient.security.model.AuthenticationResponse;
import com.teledermatology.patient.security.model.RegisterRequest;
import com.teledermatology.patient.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private ResponseEntity sendResponse(AuthenticationResponse authenticationResponse){
        ResponseCookie cookie = ResponseCookie.from("token", authenticationResponse.getToken())
                .path("/")
                .httpOnly(true)
                //.secure(true) //set true when working with browser, set false when working with postman
                .maxAge(86400)
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(authenticationResponse);
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        System.out.println("Logging Registration");
        //return ResponseEntity.ok(authenticationResponse);
        return sendResponse(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        System.out.println("Logging Authentication");
        //return ResponseEntity.ok(authenticationResponse);
        return sendResponse(authenticationResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        System.out.println("Logging user sign out");
        //return ResponseEntity.ok(authenticationResponse);
        ResponseCookie cookie = ResponseCookie.from("token", null)
                .path("/")
                .httpOnly(true)
                //.secure(true) //set true when working with browser, set false when working with postman
                .maxAge(0)
                .build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Signed out");
    }
}
