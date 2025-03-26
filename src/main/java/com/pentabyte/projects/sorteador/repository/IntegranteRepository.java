package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.model.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Long> {

    @Query("SELECT i FROM Integrante i WHERE i.rol = :rol AND i.grupo.nombre != :grupo")
    Page<Integrante> findReemplazantes(Rol rol, String grupo, Pageable paginacion);

    List<Integrante> findByGrupoIsNull();
}
