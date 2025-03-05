package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.CategoriaTope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaTopeRepository extends JpaRepository<CategoriaTope, Long> {

}
