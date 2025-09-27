package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="Otros_Aspectos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OtrosAspectos {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name="Embarazo_Id", nullable=false)
    private Embarazo embarazo;

    @Column(name="Curso_Preparacion_Asistido")
    private Boolean cursoPreparacionAsistido;

    @Column(name="Motivo_No_Asistencia", length=300)
    private String motivoNoAsistencia;

    @Column(name="Metodo_Posparto_Definido")
    private Boolean metodoPospartoDefinido;

    @Column(name="Metodo_Posparto_Detalle", length=150)
    private String metodoPospartoDetalle;
}
