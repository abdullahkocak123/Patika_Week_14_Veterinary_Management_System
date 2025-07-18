package dev.patika.veterinary.dao;

import dev.patika.veterinary.entities.Customer;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    boolean existsByName(@NotNull String name);

    List<Customer> findByNameContainingIgnoreCase(String name);
}
