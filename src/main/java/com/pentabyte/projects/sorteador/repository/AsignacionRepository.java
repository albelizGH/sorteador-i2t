package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Asignacion;
import com.pentabyte.projects.sorteador.model.Sorteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    @Query("SELECT s FROM Sorteo s WHERE s.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Sorteo> findSorteosEntreFechas(@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}
