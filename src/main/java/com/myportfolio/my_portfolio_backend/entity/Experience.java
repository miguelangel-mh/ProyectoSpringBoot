package com.myportfolio.my_portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "El título es obligatorio")
    private String titulo ;

    @NotBlank(message = "Nombre empresa / Autónomo es obligatorio")
    private String empresa ;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @PastOrPresent(message = "La fecha no puede ser una fecha futura")
    private LocalDate inicio ;

    private LocalDate fin ;

    @NotBlank(message = "Descripción obligatoria")
    private String descripcion ;

    @ManyToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo ;

}
