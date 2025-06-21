package dev.patika.veterinary.dto.request.available_date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDateSaveRequest {


    @NotNull(message = "Tarih boş olamaz")
    private LocalDate availableDate;

    private List<Long> doctorIdList;
}
