package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_asignacion")
public class AutAsignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "aut_grupo_id")
    private AutGrupo grupo;

    @ManyToOne
    @JoinColumn(name = "aut_sorteo_id")
    private AutSorteo sorteo;

    @OneToMany(mappedBy = "asignacionDeSolicitante")
    private List<AutSolicitudReemplazo> asignacionDeReemplazoList;

    @OneToMany(mappedBy = "asignacionDeSolicitante")
    private List<AutSolicitudReemplazo> asignacionDeSolicitanteList;


}
