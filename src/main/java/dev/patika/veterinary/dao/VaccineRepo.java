package dev.patika.veterinary.dao;

import dev.patika.veterinary.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {

    boolean existsByNameAndCodeAndAnimalList_IdAndProtectionFinishDateAfter(
            String name, String code, Long animalId, LocalDate today
    );

    List<Vaccine> findByProtectionFinishDateBetween(LocalDate start, LocalDate end);

    boolean existsByNameAndCodeAndProtectionStartDateAndProtectionFinishDate(
            String name,
            String code,
            LocalDate protectionStartDate,
            LocalDate protectionFinishDate
    );

    List<Vaccine> findByAnimalList_Id(Long animalId);

}
