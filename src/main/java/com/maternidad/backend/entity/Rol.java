package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="Rol")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Rol {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nombre", unique=true, nullable=false, length=50)
    private String nombre;
}
