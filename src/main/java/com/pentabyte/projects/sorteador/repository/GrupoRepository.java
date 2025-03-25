package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.dto.consultas.planificacion.GrupoPlanificacionDTO;
import com.pentabyte.projects.sorteador.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    
    @Query("""
            SELECT g.id, g.ordenDeGrupo
            FROM Grupo g
            WHERE g.categoria.id IN :idsCategorias
            """)
    List<Object[]> findGruposOrdenPorCategorias(List<Long> idsCategorias);

    boolean existsByNombre(String nombre);
    
    @Query("""
            SELECT g.categoria.id, COUNT(g)
            FROM Grupo g
            WHERE g.categoria.id IN :idsCategorias
            GROUP BY g.categoria.id
            """)
    List<Object[]> findCategoriaCantidadGruposPorCategorias(List<Long> idsCategorias);

    @Query("""
            SELECT new com.pentabyte.projects.sorteador.dto.consultas.planificacion.GrupoPlanificacionDTO(g.id,g.ordenDeGrupo,c.id)
            FROM Grupo g
            LEFT JOIN g.categoria c
            WHERE c.id IN :idsCategorias
            """)
    List<GrupoPlanificacionDTO> findGruposPoridsCategorias(List<Long> idsCategorias);

    @Query("""
        SELECT g
        FROM Grupo g
        LEFT JOIN g.integranteList i
        WHERE i.id = :idIntegrante
        """)
    Grupo obtenerGrupoPorIntegrante(@Param("idIntegrante") Long idIntegrante);


}
