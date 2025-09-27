package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "Citas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Embarazo_Id", nullable = false)
    private Embarazo embarazo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Especialidad_Id", nullable = false)
    private Especialidad especialidad;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "Razon", length = 200)
    private String razon;
}
