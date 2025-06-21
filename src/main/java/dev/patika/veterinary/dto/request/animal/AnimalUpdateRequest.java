package dev.patika.veterinary.dto.request.animal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateRequest {

    @NotNull(message = "Id değeri null olamaz")
    @Positive(message = "Id değeri pozitif değer olmak zorunda")
    private Long id;

    @NotNull(message = "İsim boş olamaz.")
    private String name;

    private String species;

    private String breed;

    private String gender;

    private String colour;

    private LocalDate dateOfBirth;

    @NotNull(message = "Müşteri ID boş olamaz")
    private Long customerId;

    private List<Long> vaccineIdList;

}
