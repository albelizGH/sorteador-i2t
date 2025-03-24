package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.dto.consultas.planificacion.GrupoPlanificacionDTO;
import com.pentabyte.projects.sorteador.model.Asignacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    @Query("""
                SELECT new com.pentabyte.projects.sorteador.dto.consultas.planificacion.GrupoPlanificacionDTO(g.id, g.ordenDeGrupo, c.id)
                FROM Asignacion a
                JOIN a.grupo g
                JOIN g.categoria c
                WHERE c.id IN :idsCategorias
                AND a.id = (
                    SELECT MAX(a2.id)
                    FROM Asignacion a2
                    JOIN a2.grupo g2
                    WHERE g2.categoria.id = c.id
                )
                AND a.estado = 'PLANIFICADO'
                ORDER BY c.id
            """)
    List<GrupoPlanificacionDTO> findUltimosGruposPorCategoriaPlanificados(List<Long> idsCategorias);


    @Query(value = """
            SELECT a.id
            FROM Asignacion a
            JOIN a.sorteo s
            WHERE a.estado = 'BORRADOR'
            AND s.fecha BETWEEN :ahora AND :fin
            """)
    List<Long> findAsignacionesBorradorEntreFechas(LocalDateTime ahora, LocalDateTime fin);


    @Query(value = """
            SELECT a.id
            FROM Asignacion a
            JOIN a.sorteo s
            WHERE a.estado = 'BORRADOR'
            AND s.fecha >= :ahora
            """)
    List<Long> findAsignacionesBorradorConFechaMayorA(LocalDateTime ahora);

    @Query(value = """
            SELECT a
            FROM Asignacion a
            WHERE a.estado = 'PLANIFICADO'
            """)
    Page<Asignacion> findAsinacionesPlanificadas(Pageable pageable);

    @Query(value = """
            SELECT a
            FROM Asignacion a
            WHERE a.estado = 'BORRADOR'
            """)
    Page<Asignacion> findAsinacionesBorrador(Pageable pageable);
}
