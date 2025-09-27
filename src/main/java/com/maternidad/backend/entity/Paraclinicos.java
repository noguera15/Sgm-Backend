package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "Paraclinicos",
        uniqueConstraints = @UniqueConstraint(columnNames = {"Embarazo_Id", "TipoParaclinico_Id", "Fecha"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paraclinicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Embarazo_Id", nullable = false)
    private Embarazo embarazo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TipoParaclinico_Id", nullable = false)
    private TipoParaclinico tipoParaclinico;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "Resultado", length = 200)
    private String resultado;
}
