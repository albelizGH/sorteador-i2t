package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.dto.consultas.planificacion.GrupoPlanificacionDTO;
import com.pentabyte.projects.sorteador.model.Asignacion;
import com.pentabyte.projects.sorteador.model.Estado;
import com.pentabyte.projects.sorteador.model.Sorteo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
            AND CASE 
                WHEN :grupoId IS NULL THEN TRUE 
                ELSE a.grupo.id = :grupoId END
            """)
    Page<Asignacion> findAsinacionesPlanificadas(Pageable pageable, Long grupoId);

    @Query(value = """
             SELECT a
             FROM Asignacion a
             WHERE a.estado = 'BORRADOR'
             AND CASE
                 WHEN :grupoId IS NULL THEN TRUE
                 ELSE a.grupo.id = :grupoId END
            """)
    Page<Asignacion> findAsinacionesBorrador(Pageable pageable, Long grupoId);

    @Query("SELECT s FROM Sorteo s WHERE s.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Sorteo> findSorteosEntreFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT DISTINCT s FROM Asignacion a " +
            "JOIN a.sorteo s " +
            "JOIN a.grupo g " +
            "JOIN g.integranteList i " +
            "WHERE i.id = :idSolicitante")
    Page<Sorteo> obtenerFechasAsignadasPorSolicitante(Long idSolicitante, Pageable paginacion);

    @Query("SELECT DISTINCT s FROM Sorteo s " +
            "WHERE s.id NOT IN (" +
            "  SELECT a.sorteo.id FROM Asignacion a " +
            "  JOIN a.grupo g " +
            "  JOIN g.integranteList i " +
            "  WHERE i.id = :idDevolucion" +
            ")" +
            "AND s.id <> :SorteoId")
    Page<Sorteo> obtenerFechasDisponiblesParaDevolucion(Long idDevolucion, Long SorteoId, Pageable paginacion);

    @Query(value = """
        SELECT a.*
        FROM aut_asignacion a
        JOIN aut_grupo g ON g.id = a.aut_grupo_id
        JOIN aut_integrante i ON g.id = i.aut_grupo_id
        WHERE i.id = :idIntegrante AND a.estado = 'PLANIFICADO'
        """, nativeQuery = true)
    Page<Asignacion> obtenerAsignacionesPorIntegrante(Long idIntegrante, Pageable paginacion);

    @Transactional
    @Modifying
    @Query("UPDATE Asignacion a SET a.estado = :nuevoEstado WHERE a.estado = :estadoActual")
    void actualizarEstado(@Param("nuevoEstado") Estado nuevoEstado, @Param("estadoActual") Estado estadoActual);
}

