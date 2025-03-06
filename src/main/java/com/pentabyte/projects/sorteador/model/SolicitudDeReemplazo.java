package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aut_solicitud_reemplazo")
public class SolicitudDeReemplazo {

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
    private Integrante empleadoSolicitante;

    @ManyToOne
    @JoinColumn(name = "aut_empleado_reemplazo")
    private Integrante empleadoReemplazo;

    @ManyToOne
    @JoinColumn(name = "aut_asignacion_solicitante")
    private Asignacion asignacionDeSolicitante;

    @ManyToOne
    @JoinColumn(name = "aut_asignacion_reemplazo")
    private Asignacion AsignacionDeReemplazo;


}
