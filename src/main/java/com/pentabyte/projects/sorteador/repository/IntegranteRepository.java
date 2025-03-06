package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Integrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Long> {

}
