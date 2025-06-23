package dev.patika.veterinary.dao;

import dev.patika.veterinary.entities.Doctor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    boolean existsByName(@NotNull String name);
}
