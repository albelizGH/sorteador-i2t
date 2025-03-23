package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.dto.consultas.planificacion.IdSorteoCategoriaDTO;
import com.pentabyte.projects.sorteador.model.Sorteo;
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


}
