package com.maternidad.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity @Table(name="Antecedentes")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Antecedentes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Embarazo_Id", nullable=false)
    private Embarazo embarazo;

    @Column(name="Retencion_Posparto") private Boolean retencionPosparto;
    @Column(name="Peso_Bebe_Fuera_Rango") private Boolean pesoBebeFueraRango;
    @Column(name="Embarazo_Gemelar") private Boolean embarazoGemelar;
    @Column(name="Parto_Dificil") private Boolean partoDificil;

    @Column(name="Gravida") private Integer gravida;
    @Column(name="Partos") private Integer partos;
    @Column(name="Abortos") private Integer abortos;
    @Column(name="Cesareas") private Integer cesareas;
    @Column(name="Obito_Fetal") private Integer obitoFetal;
    @Column(name="Embarazo_Ectopico") private Integer embarazoEctopico;
    @Column(name="Embarazo_Molar") private Integer embarazoMolar;
    @Column(name="Muerte_Neonatal_Tardia") private Integer muerteNeonatalTardia;

    @Column(name="Enfermedades_Infecciosas") private Boolean enfermedadesInfecciosas;
    @Column(name="Familiar_Preeclampsia") private Boolean familiarPreeclampsia;
    @Column(name="Trastorno_Hipertensivo") private Boolean trastornoHipertensivo;
    @Column(name="Enfermedad_Renal_Cronica") private Boolean enfermedadRenalCronica;
    @Column(name="Enfermedad_Autoinmune") private Boolean enfermedadAutoinmune;
    @Column(name="Diabetes_Tipo1") private Boolean diabetesTipo1;
    @Column(name="Diabetes_Tipo2") private Boolean diabetesTipo2;
    @Column(name="Hipertension_Cronica") private Boolean hipertensionCronica;
    @Column(name="Paciente_Desparacitada") private Boolean pacienteDesparacitada;
    @Column(name="Paciente_ASA") private Boolean pacienteAsa;

    @ManyToOne(fetch = LAZY) @JoinColumn(name="Trombembolico_Id")
    private Trombembolico trombembolico;
}
