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

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "aut_grupo_id")
    private AutGrupo grupo;

    @OneToMany(mappedBy = "empleadoReemplazo")
    private List<AutSolicitudReemplazo> empleadoReemplazoList;

    @OneToMany(mappedBy = "empleadoSolicitante")
    private List<AutSolicitudReemplazo> empleadoSolicitanteList;


}
