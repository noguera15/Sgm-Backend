package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Table(name="Paciente")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Paciente {

    @Id
    @Column(name="Documento", nullable=false)
    private Integer documento; // en SQL es INT

    @ManyToOne(fetch = LAZY) @JoinColumn(name="TipoDocumento_Id", nullable=false)
    private TipoDocumento tipoDocumento;

    @Column(name="Primer_Nombre", nullable=false, length=45)
    private String primerNombre;

    @Column(name="Segundo_Nombre", length=45)
    private String segundoNombre;

    @Column(name="Primer_Apellido", nullable=false, length=45)
    private String primerApellido;

    @Column(name="Segundo_Apellido", length=45)
    private String segundoApellido;

    @Column(name="Fecha_Nacimiento", nullable=false)
    private LocalDate fechaNacimiento;

    @Column(name="Edad", nullable=false)
    private Integer edad;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Eapb_Id", nullable=false)
    private Eapb eapb;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Regimen_Id", nullable=false)
    private Regimen regimen;

    @Column(name="Servicio_Salud_Activo")
    private Boolean servicioSaludActivo; // TINYINT

    @Column(name="Telefono_Personal")
    private Long telefonoPersonal; // BIGINT

    @Column(name="Telefono_Familiar")
    private Long telefonoFamiliar; // BIGINT

    @Column(name="Direccion_Residencia", nullable=false, length=200)
    private String direccionResidencia;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Municipio_Id", nullable=false)
    private Municipio municipio;
}
