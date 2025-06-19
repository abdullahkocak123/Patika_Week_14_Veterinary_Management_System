package dev.patika.veterinary.dto.request.customer;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {

    @NotNull(message = "Müşteri adı boş veya null olamaz")
    private String name;

    private String phone;

    private String mail;

    private String address;

    private String city;
}
