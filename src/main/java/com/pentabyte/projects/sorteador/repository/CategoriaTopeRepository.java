package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.model.CategoriaTope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaTopeRepository extends JpaRepository<CategoriaTope, Long> {



  @Query(value = "SELECT cpe.cantidad_max " +
          "FROM aut_categoria_tope cpe " +
          "JOIN aut_categoria c ON cpe.aut_categoria_id = c.id " +
          "WHERE c.id = :categoriaId " +
          "AND cpe.es_autoridad = :esAutoridad" , nativeQuery = true)

  Integer obtenerCantidadMaximaPorCategoria(@Param("categoriaId") Long categoriaId,@Param("esAutoridad") Integer esAutoridad);

  @Query(value = "SELECT COUNT(DISTINCT i.id) " +
          " FROM aut_categoria_tope cpe " +
          "JOIN aut_categoria c ON cpe.aut_categoria_id = c.id " +
          "JOIN aut_grupo g ON g.aut_categoria_id = c.id " +
          "JOIN aut_integrante i ON i.aut_grupo_id = g.id " +
          "WHERE cpe.aut_categoria_id = :categoriaId " +
          "AND i.rol = :rol ", nativeQuery = true)
  Integer obtenerCantidadIntegrantes(@Param("categoriaId") Long categoriaId,@Param("rol") String rol);

  @Query(value = "SELECT cpe.cantidad_max " +
          "FROM aut_categoria_tope cpe " +
          "JOIN aut_categoria c ON cpe.aut_categoria_id = c.id " +
          "WHERE cpe.id = :categoriaTopeId ", nativeQuery = true)
  Integer obtenerCantidadMaximaPorCategoriaTope(@Param("categoriaTopeId") Long categoriaTopeId);









}
