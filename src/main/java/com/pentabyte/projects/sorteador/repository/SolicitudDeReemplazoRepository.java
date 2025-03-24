package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.SolEstado;
import com.pentabyte.projects.sorteador.model.SolicitudDeReemplazo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudDeReemplazoRepository extends JpaRepository<SolicitudDeReemplazo, Long> {


    @Query("""
            SELECT s
            FROM SolicitudDeReemplazo s
            WHERE s.estadoDeSolicitud = :estado
            """)
    Page<SolicitudDeReemplazo> findAllConEstado(Pageable pageable, SolEstado estado);

    @Query("""
            SELECT s
            FROM SolicitudDeReemplazo s
            WHERE s.estadoDeSolicitud != 'PENDIENTE'
            """)
    Page<SolicitudDeReemplazo> findAllNoPendientes(Pageable pageable);

}
