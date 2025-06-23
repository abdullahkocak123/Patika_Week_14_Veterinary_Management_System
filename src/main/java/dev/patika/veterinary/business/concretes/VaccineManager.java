package dev.patika.veterinary.business.concretes;

import dev.patika.veterinary.business.abstracts.IVaccineService;
import dev.patika.veterinary.core.exception.AlreadyExistsException;
import dev.patika.veterinary.core.exception.AlreadyExistsExceptionValidVaccine;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.utils.Msg;
import dev.patika.veterinary.dao.VaccineRepo;
import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Vaccine;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;

    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    @Transactional
    public Vaccine save(Vaccine vaccine) {

        // Same vaccine should not be duplicated if it is already saved
        boolean sameVaccineExists = this.vaccineRepo.existsByNameAndCodeAndProtectionStartDateAndProtectionFinishDate(
                vaccine.getName(),
                vaccine.getCode(),
                vaccine.getProtectionStartDate(),
                vaccine.getProtectionFinishDate()
        );

        if (sameVaccineExists) {
            throw new AlreadyExistsException();
        }

        //No more vaccine to an animal if that type of vaccine (name and code) has valid protection finish date
        for (Animal animal : vaccine.getAnimalList()) {
            boolean exists = this.vaccineRepo.existsByNameAndCodeAndAnimalList_IdAndProtectionFinishDateAfter(
                    vaccine.getName(),
                    vaccine.getCode(),
                    animal.getId(),
                    LocalDate.now()
            );

            if (exists) {
                throw new AlreadyExistsExceptionValidVaccine();
            }
            //to provide 2-sided relationship and fill vaccine2animal table
            animal.getVaccineList().add(vaccine);
        }



        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public Vaccine get(long id) {
        return this.vaccineRepo.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    @Override
    public List<Vaccine> getAll() {
        return this.vaccineRepo.findAll();
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        //check if there is data, if not throw message
        this.get(vaccine.getId());
        return this.vaccineRepo.save(vaccine);
    }

    @Override
    public boolean delete(long id) {
        //check if there is data, if not throw message
        Vaccine vaccine = this.get(id);

        for (Animal animal : vaccine.getAnimalList()) {
            animal.getVaccineList().remove(vaccine);
        }
        vaccine.getAnimalList().clear();

        this.vaccineRepo.save(vaccine);
        this.vaccineRepo.delete(vaccine);

        return true;
    }

    @Override
    public List<Vaccine> getVaccinesByProtectionFinishDateBetween(LocalDate start, LocalDate end) {
        return this.vaccineRepo.findByProtectionFinishDateBetween(start, end);
    }

    @Override
    public List<Vaccine> getVaccinesByAnimalId(Long animalId) {
        return this.vaccineRepo.findByAnimalList_Id(animalId);
    }


}
