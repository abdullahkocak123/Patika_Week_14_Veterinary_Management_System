package dev.patika.veterinary.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "vaccines")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long id;

    @NotNull
    @Column(name = "vaccine_name")
    private String name;

    @NotNull
    @Column(name = "vaccine_code")
    private String code;

    @NotNull
    @Column(name = "vaccine_protection_start_date")
    private LocalDate protectionStartDate;

    @NotNull
    @Column(name = "vaccine_protection_finish_date")
    private LocalDate protectionFinishDate;

    @ManyToMany(mappedBy = "vaccineList")
    private List<Animal> animalList;

}
