package dev.patika.veterinary.business.concretes;

import dev.patika.veterinary.business.abstracts.IAnimalService;
import dev.patika.veterinary.core.exception.AlreadyExistsException;
import dev.patika.veterinary.core.exception.NotFoundException;
import dev.patika.veterinary.core.utils.Msg;
import dev.patika.veterinary.dao.AnimalRepo;
import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalManager implements IAnimalService {

    private final AnimalRepo animalRepo;

    public AnimalManager(AnimalRepo animalRepo) {
        this.animalRepo = animalRepo;
    }

    @Override
    public Animal save(Animal animal) {
        if (this.animalRepo.existsByNameAndCustomer_Id(animal.getName(), animal.getCustomer().getId())){
            throw new AlreadyExistsException();
        }
        animal.setId(null); // to pretend modelmapper confusion about trying to set other id's to animal
        return this.animalRepo.save(animal);
    }

    @Override
    public Animal get(long id) {
        return this.animalRepo.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    @Override
    public List<Animal> getAll() {
        return this.animalRepo.findAll();
    }

    @Override
    public Animal update(Animal animal) {
        //check if there is data, if not throw message
        this.get(animal.getId());
        return this.animalRepo.save(animal);
    }

    @Override
    public boolean delete(long id) {
        //check if there is data, if not throw message
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return true;
    }

    @Override
    public List<Animal> getByName(String name) {
        return this.animalRepo.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Animal> getByCustomerId(Long customerId) {
        return this.animalRepo.findByCustomerId(customerId);
    }

}
