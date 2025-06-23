package dev.patika.veterinary.business.abstracts;

import dev.patika.veterinary.entities.Customer;
import dev.patika.veterinary.entities.Doctor;

import java.util.List;

public interface IDoctorService {

    Doctor save (Doctor doctor);

    Doctor get(long id);

    List<Doctor> getAll();

    Doctor update (Doctor doctor);

    boolean delete (long id);
}
