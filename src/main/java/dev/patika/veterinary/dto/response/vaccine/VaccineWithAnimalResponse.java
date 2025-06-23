package dev.patika.veterinary.dto.response.vaccine;

import dev.patika.veterinary.dto.response.animal.AnimalResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineWithAnimalResponse {
    private Long id;
    private String name;
    private String code;
    private LocalDate protectionStartDate;
    private LocalDate protectionFinishDate;
    private List<AnimalResponse> animalList;
}