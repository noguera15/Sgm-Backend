package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Table(name="Control")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Control {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Embarazo_Id", nullable=false)
    private Embarazo embarazo;

    @Column(name="Numero_Control")
    private Integer numeroControl;

    @Column(name="Semanas_Gestacion")
    private Integer semanasGestacion;

    @Column(name="Tension_Sistolica")
    private Integer tensionSistolica;

    @Column(name="Tension_Diastolica")
    private Integer tensionDiastolica;

    @Column(name="Peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name="Talla", precision = 4, scale = 2)
    private BigDecimal talla;

    // Columna generada en MySQL (solo lectura)
    @Column(name="IMC", precision = 5, scale = 2, insertable = false, updatable = false)
    private BigDecimal imc;

    @Column(name="Altura_Uterina", precision = 5, scale = 2)
    private BigDecimal alturaUterina;

    @Column(name="Fecha_Ultimo_Control")
    private LocalDate fechaUltimoControl;

    @Column(name="Fecha_Proximo_Control")
    private LocalDate fechaProximoControl;
}
