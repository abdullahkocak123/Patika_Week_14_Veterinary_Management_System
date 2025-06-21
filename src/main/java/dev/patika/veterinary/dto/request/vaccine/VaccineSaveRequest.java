package dev.patika.veterinary.dto.request.vaccine;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineSaveRequest {

    @NotNull(message = "Aşı adı boş olamaz")
    private String name;

    @NotNull(message = "Aşı kodu boş olamaz")
    private String code;

    @NotNull(message = "Koruma başlangıç tarihi boş olamaz")
    private LocalDate protectionStartDate;

    @NotNull(message = "Koruma bitiş tarihi boş olamaz")
    private LocalDate protectionFinishDate;
}
