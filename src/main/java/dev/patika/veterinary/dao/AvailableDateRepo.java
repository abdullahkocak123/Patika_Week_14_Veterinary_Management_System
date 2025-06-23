package dev.patika.veterinary.dao;

import dev.patika.veterinary.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AvailableDateRepo extends JpaRepository<AvailableDate, Long> {

    Optional<AvailableDate> findByAvailableDate(LocalDate availableDate);
}
