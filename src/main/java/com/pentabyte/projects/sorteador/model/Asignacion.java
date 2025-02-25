package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_asignacion")
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "aut_grupo_id")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "aut_sorteo_id")
    private Sorteo sorteo;

    @OneToMany(mappedBy = "asignacionDeSolicitante")
    private List<SolicitudReemplazo> asignacionDeReemplazoList;

    @OneToMany(mappedBy = "asignacionDeSolicitante")
    private List<SolicitudReemplazo> asignacionDeSolicitanteList;


}
