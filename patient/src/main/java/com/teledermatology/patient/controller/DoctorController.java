package com.teledermatology.patient.controller;

import com.teledermatology.patient.bean.response.PastAppointmentResponse;
import com.teledermatology.patient.service.serviceInterface.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/api/v1/doctor")
@AllArgsConstructor
public class DoctorController {
    private final PatientService patientService;
    @GetMapping("/get-pending-appointments")
    public ResponseEntity getPendingAppointments(){
        System.out.println("request reached here");
        List<PastAppointmentResponse> pastAppointmentResponseList= patientService.getPendingAppointments();
        if(isNull(pastAppointmentResponseList)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.ok(pastAppointmentResponseList);
        }
    }
}
