package com.teledermatology.admin.security.service;

import com.teledermatology.admin.bean.entity.DoctorEntity;
import com.teledermatology.admin.bean.entity.Role;
import com.teledermatology.admin.repository.DoctorRepository;
import com.teledermatology.admin.security.model.AuthenticationRequest;
import com.teledermatology.admin.security.model.AuthenticationResponse;
import com.teledermatology.admin.security.model.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final DoctorRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var doctor = DoctorEntity.builder()
                .did(registerRequest.getDid())
                .fname(registerRequest.getFname())
                .lname(registerRequest.getLname())
                .email(registerRequest.getEmail())
                .pass(passwordEncoder.encode(registerRequest.getPass()))
                .role(Role.USER)
                .build();
        patientRepository.save(doctor);
        String jwt_token = jwtService.generateToken(doctor);
        return AuthenticationResponse.builder()
                .token(jwt_token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPass()
                    )
            );
        }
        catch(Exception e){
            System.out.println(e);
        }
        DoctorEntity doctor = patientRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        String jwt_token = jwtService.generateToken(doctor);
        return AuthenticationResponse.builder()
                .token(jwt_token)
                .build();
    }
}