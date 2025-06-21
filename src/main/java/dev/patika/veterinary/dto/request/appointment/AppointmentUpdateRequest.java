package dev.patika.veterinary.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {

    @NotNull(message = "Id değeri null olamaz")
    @Positive(message = "Id değeri pozitif değer olmak zorunda")
    private Long id;

    @NotNull(message = "Randevu zamanı boş olamaz")
    private LocalDateTime appointmentDateTime;

    @NotNull(message = "Hayvan ID boş olamaz")
    private Long animalId;

    @NotNull(message = "Doktor ID boş olamaz")
    private Long doctorId;
}
