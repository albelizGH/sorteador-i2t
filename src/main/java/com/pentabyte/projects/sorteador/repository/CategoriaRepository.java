package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query(value = "SELECT COUNT(*) > 0 " +
            "FROM aut_categoria c " +
            "WHERE c.nombre = :nombre", nativeQuery = true)
    Integer buscarNombre(@Param("nombre") String nombre);

    @Query("""
            SELECT COUNT(c)
            FROM Categoria c
            """)
    long countCategorias();

}
