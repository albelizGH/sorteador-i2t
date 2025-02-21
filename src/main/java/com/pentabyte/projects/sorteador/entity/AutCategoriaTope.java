package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import jdk.jfr.MemoryAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_categoria_tope")
public class AutCategoriaTope  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_categoria_id")
    private AutCategoria autCategoria;

    @Column(name = "cantidad_min")
    private Integer cantidadMinima;

    @Column(name = "cantidad_max")
    private Integer cantidadMaxima;

    @Column(name = "es_autoridad")
    private Boolean esAutoridad;
}
