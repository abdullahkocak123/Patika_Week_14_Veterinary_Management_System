package dev.patika.veterinary.business.abstracts;

import dev.patika.veterinary.entities.Customer;
import dev.patika.veterinary.entities.Vaccine;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {

    Vaccine save (Vaccine vaccine);

    Vaccine get(long id);

    List<Vaccine> getAll();

    Vaccine update (Vaccine vaccine);

    boolean delete (long id);

    List<Vaccine> getVaccinesByProtectionFinishDateBetween(LocalDate start, LocalDate end);

    List<Vaccine> getVaccinesByAnimalId(Long animalId);

}
