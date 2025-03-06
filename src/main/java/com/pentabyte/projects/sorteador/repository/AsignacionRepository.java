package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

}
