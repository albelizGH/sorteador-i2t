package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    
    @Query(value = """
            SELECT g.id, c.id
            FROM AUT_ASIGNACION a
            JOIN AUT_GRUPO g ON a.aut_grupo_id = g.id
            JOIN AUT_CATEGORIA c ON g.aut_categoria_id = c.id
            WHERE c.id IN :idsCategorias
              AND a.id = (
                SELECT MAX(a2.id)
                FROM AUT_ASIGNACION a2
                JOIN AUT_GRUPO g2 ON a2.aut_grupo_id = g2.id
                WHERE g2.aut_categoria_id = c.id
              )
              AND a.estado = 'PLANIFICADO'
            ORDER BY c.id
            """, nativeQuery = true)
    List<Object[]> findUltimosGruposPorCategoriaPlanificados(List<Long> idsCategorias);


    @Query(value = """
            SELECT a.id
            FROM Asignacion a
            JOIN a.sorteo s
            WHERE a.estado = 'BORRADOR'
            AND s.fecha BETWEEN :ahora AND :fin
            """)
    List<Long> findAsignacionesBorradorEntreFechas(LocalDateTime ahora, LocalDateTime fin);
}
