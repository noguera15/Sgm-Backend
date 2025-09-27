package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Table(name="Parto")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Parto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Embarazo_Id", nullable=false)
    private Embarazo embarazo;

    @Column(name="Ocurrio")
    private Boolean ocurrio;

    @Column(name="Fecha")
    private LocalDate fecha;

    @Column(name="RN_Vivo")
    private Boolean rnVivo;
}
