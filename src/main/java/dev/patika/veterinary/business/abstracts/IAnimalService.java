package dev.patika.veterinary.business.abstracts;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Customer;

import java.util.List;

public interface IAnimalService {

    Animal save (Animal animal);

    Animal get(long id);

    List<Animal> getAll();

    Animal update (Animal animal);

    boolean delete (long id);

    List<Animal> getByName(String name);

    List<Animal> getByCustomerId(Long customerId);

}
