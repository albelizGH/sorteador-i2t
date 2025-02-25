package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_rel_producto")
public class RelProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer orden;

    @ManyToOne
    @JoinColumn(name = "aut_categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "relProducto")
    private List<Sorteo> sorteoList;


}
