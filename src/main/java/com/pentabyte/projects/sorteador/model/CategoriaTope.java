package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aut_categoria_tope")
public class CategoriaTope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "aut_categoria_id")
    Categoria categoria;

    @Column(name = "cantidad_min")
    Integer cantidadMinima;

    @Column(name = "cantidad_max")
    Integer cantidadMaxima;

    @Column(name = "es_autoridad")
    Boolean esAutoridad;
}
