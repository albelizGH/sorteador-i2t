package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aut_notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aut_categoria_id")
    private Integrante integrante;

    private String titulo;

    private String mensaje;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private NotificacionTipo tipo;


}
