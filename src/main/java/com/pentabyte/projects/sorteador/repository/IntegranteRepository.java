package com.pentabyte.projects.sorteador.repository;


import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IntegranteRepository extends JpaRepository<Integrante, Long> {

    @Query("""
            SELECT i 
            FROM Integrante i
            JOIN i.grupo g
            JOIN g.asignacionList a
            JOIN a.sorteo s
            WHERE s.fecha >= :fecha
            AND i.rol = :rol
            AND g.nombre != :grupo
            AND s.confirmado=true
            AND a.estado='PLANIFICADO'
            """)
    List<Integrante> findReemplazantes(Rol rol, String grupo, LocalDateTime fecha);

    List<Integrante> findByGrupoIsNull();

    Optional<Integrante> findByEmail(String email);
}
