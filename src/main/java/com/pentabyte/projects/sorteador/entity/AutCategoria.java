package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_categoria")
public class AutCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "ultima_asignacion_semana")
    private Integer ultimaSemanaDeAsignacion;

    @Column(name = "ultima_asignacion_fecha")
    private LocalDate ultimaFechaDeAsignacion;

    @Column(name = "semanas_a_planificar")
    private Integer semanasAPlanificar;

    @OneToMany(mappedBy = "autCategoria")
    private List<AutCategoriaTope> autCategoriaTopeList;

    @OneToMany(mappedBy = "autCategoria")
    private List<AutGrupo> autGrupoList;

    @OneToMany(mappedBy = "autCategoria")
    private List<AutRelProducto> autRelProductoList;

}
