package dev.patika.veterinary.dto.request.doctor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSaveRequest {

    @NotNull(message = "Doktor adı boş olamaz")
    private String name;

    private String phone;

    @Email(message = "Geçerli bir email adresi giriniz")
    private String mail;

    private String address;

    private String city;
}
