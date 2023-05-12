package com.teledermatology.patient.service.serviceInterface;

import com.teledermatology.patient.bean.entity.Appointment;
import com.teledermatology.patient.bean.response.PastAppointmentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PatientService {
    List<PastAppointmentResponse> getPastAppointments(String pid);

    Integer createAppointment(Appointment appointment);

    Integer uploadImage(MultipartFile file);

    byte[] viewImage(Integer aid);
}