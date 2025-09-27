package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Table(name="Embarazo")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Embarazo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Paciente_Documento", nullable=false)
    private Paciente paciente;

    @Column(name="Fecha_Ultima_Menstruacion")
    private LocalDate fechaUltimaMenstruacion;

    @Column(name="Primer_Embarazo")
    private Boolean primerEmbarazo;

    @Column(name="Fecha_Ultimo_Parto")
    private LocalDate fechaUltimoParto;

    @Column(name="Fecha_Probable_Parto")
    private LocalDate fechaProbableParto;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Ips_Id")
    private Ips ips;
}
