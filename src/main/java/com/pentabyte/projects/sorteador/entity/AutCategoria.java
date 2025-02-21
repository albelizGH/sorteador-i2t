package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_categoria_tope")
public class AutCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "ultima_asignacion_semana")
    private Integer ultimaSemanaDeAsignacion;

    @Column(name = "ultima_asignacion_fecha")
    private String ultimaFechaDeAsignacion;

    @Column(name = "semanas_a_planificar")
    private Integer semanasAPlanificar;

    @OneToMany(mappedBy = "autCategoria")
    private List<AutCategoriaTope> autCategoriasTopes;

}
