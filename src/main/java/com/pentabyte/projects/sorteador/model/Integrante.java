package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aut_integrante")
public class Integrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Integer legajo;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "aut_grupo_id")
    private Grupo grupo;

    @OneToMany(mappedBy = "empleadoReemplazo")
    private List<SolicitudDeReemplazo> empleadoReemplazoList;

    @OneToMany(mappedBy = "empleadoSolicitante")
    private List<SolicitudDeReemplazo> empleadoSolicitanteList;

    @OneToMany(mappedBy = "integrante")
    private List<Notificacion> notificaciones;


}
