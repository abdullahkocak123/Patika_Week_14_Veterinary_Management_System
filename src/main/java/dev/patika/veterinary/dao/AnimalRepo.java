package dev.patika.veterinary.dao;

import dev.patika.veterinary.entities.Animal;
import dev.patika.veterinary.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, Long> {

    boolean existsByNameAndCustomer_Id(String name, Long CustomerId);

    List<Animal> findByNameContainingIgnoreCase(String name);

    List<Animal> findByCustomerId(Long customerId);


}
