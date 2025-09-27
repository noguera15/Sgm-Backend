package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="Eapb")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Eapb {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre", nullable=false, unique=true, length=120)
    private String nombre;
}
