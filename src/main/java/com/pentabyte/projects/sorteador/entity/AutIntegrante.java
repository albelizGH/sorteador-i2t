package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aut_integrante")
public class AutIntegrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer legajo;

    private Rol rol;

    @OneToMany(mappedBy = "autEmpleadoReemplazo")
    private List<AutSolicitudReemplazo> autEmpleadoReemplazoList;

    @OneToMany(mappedBy = "autEmpleadoSolicitante")
    private List<AutSolicitudReemplazo> autEmpleadoSolicitanteList;

    @ManyToOne
    @JoinColumn(name = "aut_grupo_id")
    private AutGrupo autGrupoId;

}
