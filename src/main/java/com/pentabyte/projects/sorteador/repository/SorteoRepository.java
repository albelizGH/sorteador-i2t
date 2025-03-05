package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Sorteo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SorteoRepository extends JpaRepository<Sorteo, Long> {
}
