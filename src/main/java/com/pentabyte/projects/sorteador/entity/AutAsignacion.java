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

    private Estado estado;

    @ManyToOne
    @JoinColumn(name="aut_grupo_id")
    private AutGrupo autGrupoId;

    @OneToMany(mappedBy = "autAsignacionReemplazo")
    private List<AutSolicitudReemplazo> autAsignacionReemplazoList;

    @OneToMany(mappedBy = "autAsignacionSolicitante")
    private List<AutSolicitudReemplazo> autAsignacionSolicitanteList;



}
