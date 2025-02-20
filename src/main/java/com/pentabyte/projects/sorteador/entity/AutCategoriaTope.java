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
}
