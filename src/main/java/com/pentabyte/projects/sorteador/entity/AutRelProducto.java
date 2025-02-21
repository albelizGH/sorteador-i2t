package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_rel_producto")
public class AutRelProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "aut_categoria_id")
    private AutCategoria categoria;

    @OneToMany(mappedBy = "relProducto")
    private List<AutSorteo> sorteoList;


}
