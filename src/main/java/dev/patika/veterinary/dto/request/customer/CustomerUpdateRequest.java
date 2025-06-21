package dev.patika.veterinary.dto.request.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    @NotNull(message = "Id değeri null olamaz")
    @Positive(message = "Id değeri pozitif değer olmak zorunda")
    private Long id;

    @NotNull(message = "Müşteri adı boş veya null olamaz")
    private String name;

    private String phone;

    private String mail;

    private String address;

    private String city;
}
