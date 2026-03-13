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
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "El nombre es obligatorio")
    private String titulo ;

    @NotBlank(message = "El centro es obligatorio")
    private String centro ;

    @NotNull(message = "La fecha de inicio no puede estar vacía")
    @PastOrPresent(message = "La fecha no puede ser una fecha futura")
    private LocalDate inicio ;

    private LocalDate fin ;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion ;

    @ManyToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo ;
}
