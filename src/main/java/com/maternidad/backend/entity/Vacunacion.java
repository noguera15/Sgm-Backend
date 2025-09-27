package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "Vacunacion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"Embarazo_Id", "TipoVacuna_Id", "Fecha"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacunacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Embarazo_Id", nullable = false)
    private Embarazo embarazo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TipoVacuna_Id", nullable = false)
    private TipoVacuna tipoVacuna;

    @Column(name = "Fecha")
    private LocalDate fecha;
}
