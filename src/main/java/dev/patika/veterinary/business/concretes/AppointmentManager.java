package dev.patika.veterinary.business.concretes;

import dev.patika.veterinary.business.abstracts.IAppointmentService;
import dev.patika.veterinary.core.exception.AppointmentNotAvailable;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.utils.Msg;
import dev.patika.veterinary.dao.AppointmentRepo;
import dev.patika.veterinary.dao.DoctorRepo;
import dev.patika.veterinary.entities.Appointment;
import dev.patika.veterinary.entities.Doctor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {

    private final AppointmentRepo appointmentRepo;
    private final DoctorRepo doctorRepo;

    public AppointmentManager(AppointmentRepo appointmentRepo, DoctorRepo doctorRepo) {
        this.appointmentRepo = appointmentRepo;
        this.doctorRepo = doctorRepo;
    }

    @Override
    public Appointment save(Appointment appointment) {
        Long doctorId = appointment.getDoctor().getId();
        LocalDateTime requestedDateTime = appointment.getAppointmentDateTime();
        LocalDate requestedDate = requestedDateTime.toLocalDate();

        // 1. Doctor should work that day.
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()->new NotFoundException(doctorId));
        boolean isAvailable = doctor.getAvailableDateList()
                .stream()
                .anyMatch(date -> date.getAvailableDate().equals(requestedDate));

        if (!isAvailable) {
            throw new AppointmentNotAvailable();
        }

        // 2. There should not be another appointment at that day and time
        boolean isAlreadyReserved = this.appointmentRepo
                .existsByAppointmentDateTimeAndDoctor_Id(requestedDateTime, doctorId);

        if (isAlreadyReserved) {
            throw new AppointmentNotAvailable();
        }

        // 3. Otherwise save the appointment
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public Appointment get(long id) {
        return this.appointmentRepo.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    @Override
    public List<Appointment> getAll() {
        return this.appointmentRepo.findAll();
    }

    @Override
    public Appointment update(Appointment appointment) {
        //check if there is data, if not throw message
        this.get(appointment.getId());
        return this.appointmentRepo.save(appointment);
    }

    @Override
    public boolean delete(long id) {
        //check if there is data, if not throw message
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }

    @Override
    public List<Appointment> getAppointmentsByDateRangeAndDoctor(LocalDateTime startDate, LocalDateTime endDate, Long doctorId) {
        return this.appointmentRepo.findByAppointmentDateTimeBetweenAndDoctor_Id(startDate, endDate, doctorId);
    }

    @Override
    public List<Appointment> getAppointmentsByDateRangeAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Long animalId) {
        return this.appointmentRepo.findByAppointmentDateTimeBetweenAndAnimal_Id(startDate, endDate, animalId);
    }


}
