package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
