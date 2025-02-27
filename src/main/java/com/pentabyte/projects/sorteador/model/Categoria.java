package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aut_categoria")
public class Categoria {

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

    @OneToMany(mappedBy = "categoria")
    private List<CategoriaTope> categoriaTopeList;

    @OneToMany(mappedBy = "categoria")
    private List<Grupo> grupoList;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productoList;

}
