package com.waris.pms_sevice.service;

import com.waris.pms_sevice.entity.Appointment;
import com.waris.pms_sevice.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existingAppointment.setNotes(updatedAppointment.getNotes());
        return appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment markAppointmentAsCompleted(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("Completed");
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getCompletedAppointments() {
        return appointmentRepository.findByStatus("Completed");
    }

    public long getTotalAppointmentsCount() {
        return appointmentRepository.count();
    }

    public long getCompletedAppointmentsCount() {
        return appointmentRepository.countByStatus("Completed");
    }

    public long getActiveAppointmentsCount() {
        return appointmentRepository.countByStatus("Scheduled");
    }
}
