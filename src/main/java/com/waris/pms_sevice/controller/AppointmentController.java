package com.waris.pms_sevice.controller;

import com.waris.pms_sevice.entity.Appointment;
import com.waris.pms_sevice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentService.createAppointment(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        Appointment appointment = appointmentService.updateAppointment(id, updatedAppointment);
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment deleted successfully");
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Appointment> completeAppointment(@PathVariable Long id) {
        Appointment completedAppointment = appointmentService.markAppointmentAsCompleted(id);
        return ResponseEntity.ok(completedAppointment);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Appointment>> getCompletedAppointments() {
        List<Appointment> completedAppointments = appointmentService.getCompletedAppointments();
        return ResponseEntity.ok(completedAppointments);
    }

    @GetMapping("/stats")
    public ResponseEntity<String> getAppointmentStats() {
        long totalAppointments = appointmentService.getTotalAppointmentsCount();
        long completedAppointments = appointmentService.getCompletedAppointmentsCount();
        long activeAppointments = appointmentService.getActiveAppointmentsCount();

        return ResponseEntity.ok(
                "Total Appointments: " + totalAppointments +
                        ", Completed Appointments: " + completedAppointments +
                        ", Active Appointments: " + activeAppointments
        );
    }
}
