package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
