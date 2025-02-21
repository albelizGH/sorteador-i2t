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

    private String nombre;

    @Column(name = "orden_grupo")
    private Integer ordenDeGrupo;

    @ManyToOne
    @JoinColumn(name = "aut_categoria_id")
    private AutCategoria categoria;

    @OneToMany(mappedBy = "grupo")
    private List<AutIntegrante> integranteList;

    @OneToMany(mappedBy = "grupo")
    private List<AutAsignacion> asignacionList;


}
