package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUT_INTEGRANTE")
public class AutIntegrante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer legajo;
    private Rol rol;
    @ManyToOne
    @JoinColumn(name = "aut_grupo_id")
    private AutGrupo autGrupo;

}
