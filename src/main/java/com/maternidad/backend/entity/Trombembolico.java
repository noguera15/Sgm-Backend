package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="Trombembolico")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Trombembolico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre_Riesgo_Tromboembolico", nullable=false, unique=true, length=200)
    private String nombreRiesgoTromboembolico;
}