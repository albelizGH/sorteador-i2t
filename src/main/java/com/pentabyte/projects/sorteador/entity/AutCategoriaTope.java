package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_categoria_tope")
public class AutCategoriaTope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "aut_categoria_id")
    AutCategoria autCategoria;

    @Column(name = "cantidad_min")
    Integer cantidadMinima;

    @Column(name = "cantidad_max")
    Integer cantidadMaxima;

    @Column(name = "es_autoridad")
    Boolean esAutoridad;
}
