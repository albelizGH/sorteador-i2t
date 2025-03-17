package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Asignacion;
import com.pentabyte.projects.sorteador.model.Sorteo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    Asignacion findBySorteoFechaAndSorteoId(LocalDateTime sorteoFecha, Long sorteoId);

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
}
