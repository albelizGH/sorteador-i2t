package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_grupo")
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "orden_grupo")
    private Integer ordenDeGrupo;

    @ManyToOne
    @JoinColumn(name = "aut_categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "grupo")
    private List<Integrante> integranteList;

    @OneToMany(mappedBy = "grupo")
    private List<Asignacion> asignacionList;


}
