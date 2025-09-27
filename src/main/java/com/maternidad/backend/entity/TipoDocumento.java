package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="TipoDocumento")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoDocumento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Nombre", nullable=false, unique=true, length=45)
    private String nombre;
}
