package dev.patika.veterinary.dao;

import dev.patika.veterinary.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    boolean existsByAppointmentDateTimeAndDoctor_Id(LocalDateTime appointmentDateTime, Long doctorId);

    List<Appointment> findByAppointmentDateTimeBetweenAndDoctor_Id(LocalDateTime startDate, LocalDateTime endDate, Long doctorId);

    List<Appointment> findByAppointmentDateTimeBetweenAndAnimal_Id(LocalDateTime startDate, LocalDateTime endDate, Long animalId);

}
