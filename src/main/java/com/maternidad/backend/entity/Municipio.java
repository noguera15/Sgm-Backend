package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="Municipio")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Municipio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre_Municipio", nullable=false, unique=true, length=80)
    private String nombreMunicipio;
}
