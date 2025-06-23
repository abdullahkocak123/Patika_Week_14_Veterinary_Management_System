package dev.patika.veterinary.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "available_dates")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id")
    private Long id;

    @NotNull
    @Column(name = "available_date", unique = true)
    private LocalDate availableDate;

    @ManyToMany(mappedBy = "availableDateList")
    private List<Doctor> doctorList = new ArrayList<>();

    @Override
    public String toString() {
        return "AvailableDate{id=" + id + ", date=" + availableDate + "}";
    }

}
