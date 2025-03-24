package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.dto.consultas.planificacion.IdSorteoCategoriaDTO;
import com.pentabyte.projects.sorteador.model.Sorteo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SorteoRepository extends JpaRepository<Sorteo, Long> {

    @Query("""
            SELECT new com.pentabyte.projects.sorteador.dto.consultas.planificacion.IdSorteoCategoriaDTO(s.id, c.id)
            FROM Sorteo s
            LEFT JOIN s.producto p
            LEFT JOIN p.categoria c
            WHERE s.fecha BETWEEN :inicio AND :fin and s.confirmado = true
            ORDER BY s.id
            """)
    List<IdSorteoCategoriaDTO> findIdSorteosConfirmadosEntreFechas(LocalDateTime inicio, LocalDateTime fin);


    @Query("""
                SELECT s
                FROM Sorteo s
                LEFT JOIN s.producto p
                LEFT JOIN p.categoria c
                WHERE
                    (:categoriaId IS NULL OR c.id = :categoriaId)
                    AND (:fechaInicio IS NULL OR s.fecha >= :fechaInicio)
                    AND (:fechaFin IS NULL OR s.fecha <= :fechaFin)
            """)
    Page<Sorteo> findByCategoriaAndFecha(Pageable pageable, Long categoriaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
