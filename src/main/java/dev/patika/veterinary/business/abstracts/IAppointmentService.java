package dev.patika.veterinary.business.abstracts;

import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.Customer;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    Appointment save (Appointment appointment);

    Appointment get(long id);

    List<Appointment> getAll();

    Appointment update (Appointment appointment);

    boolean delete (long id);

    List<Appointment> getAppointmentsByDateRangeAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Long doctorId);

    List<Appointment> getAppointmentsByDateRangeAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Long animalId);

}
