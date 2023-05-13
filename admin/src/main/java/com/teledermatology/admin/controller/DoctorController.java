package com.teledermatology.admin.controller;

import com.teledermatology.admin.bean.response.ImageResponse;
import com.teledermatology.admin.bean.response.PendingAppointmentsResponse;
import com.teledermatology.admin.service.serviceImplementation.ImageUtil;
import com.teledermatology.admin.service.serviceInterface.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok("test");
    }

    @GetMapping("/view-appointments")
    public ResponseEntity viewAppointments(){
        List<PendingAppointmentsResponse> pendingAppointmentsResponseList =doctorService.getPendingAppointments();
        if(pendingAppointmentsResponseList == null){
            return ResponseEntity.ok("No pending appointments");
        }
        else{
            return ResponseEntity.ok(pendingAppointmentsResponseList);
        }
    }

    @GetMapping("/view-appointment-details/{aid}")
    public ResponseEntity viewAppointmentDetails(@RequestParam String aid){
        ImageResponse imageResponse = doctorService.getImage(aid);
        if(imageResponse == null){
            return ResponseEntity.ok("Not a valid appointment");
        }
        else{
            byte[] image = ImageUtil.decompressImage(imageResponse.getImagedata());
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/png"))
                    .body(image);
        }
    }
}
