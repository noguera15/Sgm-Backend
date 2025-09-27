package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String password; // almacenado con BCrypt

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;
}
