package com.myportfolio.my_portfolio_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PersonalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre ;
    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos ;
    @NotBlank(message = "El título es obligatorio")
    private String titulo ;
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion ;
    private String imagen ;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    private String email ;
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono ;
    @URL(message = "URL no válida")
    private String linkedinUrl ;

    @Embedded
    private Audit audit = new Audit() ;

    @PrePersist
    public void prePersist() {
        if (audit == null) {
            audit = new Audit();
        }
        audit.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate() {
        if (audit == null) {
            audit = new Audit();
        }
        audit.setUpdatedAt(LocalDateTime.now());
    }
}
