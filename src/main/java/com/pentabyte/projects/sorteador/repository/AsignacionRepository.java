package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
    Asignacion findBySorteoFechaAndSorteoId(LocalDateTime sorteoFecha, Long sorteoId);
}
