package com.pentabyte.projects.sorteador.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "aut_sorteo")
public class Sorteo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private Boolean confirmado;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_descriptivo")
    private DiaDescriptivo diaDescriptivo;

    @ManyToOne
    @JoinColumn(name = "aut_rel_producto_id")
    private RelProducto relProducto;

    @OneToMany(mappedBy = "sorteo")
    private List<Asignacion> asignacionList;

}
