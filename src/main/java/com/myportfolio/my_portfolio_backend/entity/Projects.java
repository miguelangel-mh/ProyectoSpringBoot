package com.myportfolio.my_portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @NotBlank(message = "Nombre del proyecto obligatorio")
    private String titulo ;

    @NotBlank(message = "Descripción del proyecto obligatorio")
    private String descripcion ;

    private String imagenUrl ;

    private String proyectoUrl ;

    @ManyToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo ;

}
