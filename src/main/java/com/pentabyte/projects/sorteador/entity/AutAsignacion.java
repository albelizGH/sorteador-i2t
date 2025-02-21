package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_asignacion")
public class AutAsignacion {
    private Estado estado;
}
