package dev.patika.veterinary.business.concretes;

import dev.patika.veterinary.business.abstracts.IAvailableDateService;
import dev.patika.veterinary.core.exception.AlreadyExistsException;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.utils.Msg;
import dev.patika.veterinary.dao.AvailableDateRepo;
import dev.patika.veterinary.entities.AvailableDate;
import dev.patika.veterinary.entities.Doctor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailableDateManager implements IAvailableDateService {

    private final AvailableDateRepo availableDateRepo;

    public AvailableDateManager(AvailableDateRepo availableDateRepo) {
        this.availableDateRepo = availableDateRepo;
    }

    @Override
    @Transactional
    public AvailableDate save(AvailableDate availableDate) {
        Optional<AvailableDate> existingDateOpt = availableDateRepo.findByAvailableDate(availableDate.getAvailableDate());
        if (existingDateOpt.isPresent()) {
            AvailableDate existingDate = existingDateOpt.get();
            for (Doctor doctor : availableDate.getDoctorList()) {
                if (existingDate.getDoctorList().stream().anyMatch(d -> d.getId().equals(doctor.getId()))) {
                    throw new AlreadyExistsException();
                } else {
                    existingDate.getDoctorList().add(doctor); // adding doctor to already existing availableDate
                    doctor.getAvailableDateList().add(existingDate); //to provide 2-sided relationship and fill doctor2available_date table
                }
            }
            return availableDateRepo.save(existingDate);
        } else {
            for (Doctor doctor : availableDate.getDoctorList()) {
                doctor.getAvailableDateList().add(availableDate); // same(to provide 2-sided relationship and fill doctor2available_date table)
            }
            return availableDateRepo.save(availableDate); // adding a new availableDate
        }
    }

    @Override
    public AvailableDate get(long id) {
        return this.availableDateRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public List<AvailableDate> getAll() {
        return this.availableDateRepo.findAll();
    }

    @Override
    public AvailableDate update(AvailableDate availableDate) {
        //check if there is data, if not throw message
        this.get(availableDate.getId());
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(long id) {
        //check if there is data, if not throw message
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return true;
    }
}
