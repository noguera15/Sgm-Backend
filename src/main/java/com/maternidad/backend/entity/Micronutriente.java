package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "Micronutriente",
        uniqueConstraints = @UniqueConstraint(columnNames = {"Embarazo_Id", "Suplemento_Id", "Fecha"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Micronutriente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Embarazo_Id", nullable = false)
    private Embarazo embarazo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Suplemento_Id", nullable = false)
    private Suplemento suplemento;

    @Column(name = "Fecha")
    private LocalDate fecha;
}
