package com.pentabyte.projects.sorteador.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUT_ASIGNACION")
public class AutAsignacion {
    private Estado estado;

}
