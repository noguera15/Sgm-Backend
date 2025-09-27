package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Table(name="Ips")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Ips {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre", nullable=false, unique=true, length=150)
    private String nombre;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Municipio_Id", nullable=false)
    private Municipio municipio;
}
