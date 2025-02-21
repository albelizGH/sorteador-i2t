package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_grupo")
public class AutGrupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aut_categoria_id")
    private AutCategoria autCategoria;

    private String nombre;

    @Column(name="orden_grupo")
    private Integer ordenGrupo;

    @OneToMany(mappedBy = "autGrupoId")
    private List<AutIntegrante> autIntegranteList;

    @OneToMany(mappedBy = "autGrupoId")
    private List<AutAsignacion> autAsignacionList;


}
