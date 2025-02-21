package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aut_solitud_reemplazo")
public class AutSolicitudReemplazo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaDeSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "sol_estado")
    private SolEstado estadoDeSolicitud;

    @ManyToOne
    @JoinColumn(name = "aut_empleado_solicitante")
    private AutIntegrante empleadoSolicitante;

    @ManyToOne
    @JoinColumn(name = "aut_empleado_reemplazo")
    private AutIntegrante empleadoReemplazo;

    @ManyToOne
    @JoinColumn(name = "aut_asignacion_solicitante")
    private AutAsignacion asignacionDeSolicitante;

    @ManyToOne
    @JoinColumn(name = "aut_asignacion_reemplazo")
    private AutAsignacion AsignacionDeReemplazo;


}
