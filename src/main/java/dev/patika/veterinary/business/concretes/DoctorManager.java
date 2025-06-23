package dev.patika.veterinary.business.concretes;

import dev.patika.veterinary.business.abstracts.IDoctorService;
import dev.patika.veterinary.core.exception.AlreadyExistsException;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.utils.Msg;
import dev.patika.veterinary.dao.DoctorRepo;
import dev.patika.veterinary.entities.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorManager implements IDoctorService {

    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @Override
    public Doctor save(Doctor doctor) {
        if (this.doctorRepo.existsByName(doctor.getName())){
            throw new AlreadyExistsException();
        }
        return this.doctorRepo.save(doctor);
    }

    @Override
    public Doctor get(long id) {
        return this.doctorRepo.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    @Override
    public List<Doctor> getAll() {
        return this.doctorRepo.findAll();
    }

    @Override
    public Doctor update(Doctor doctor) {
        //check if there is data, if not throw message
        this.get(doctor.getId());
        return this.doctorRepo.save(doctor);
    }

    @Override
    public boolean delete(long id) {
        //check if there is data, if not throw message
        Doctor doctor = this.get(id);
        this.doctorRepo.delete(doctor);
        return true;
    }
}
