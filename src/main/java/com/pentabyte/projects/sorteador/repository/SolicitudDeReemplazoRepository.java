package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.SolEstado;
import com.pentabyte.projects.sorteador.model.SolicitudDeReemplazo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Query(value = """
            SELECT szo.*
            FROM aut_solicitud_reemplazo szo
            JOIN aut_integrante i ON i.id = szo.aut_empleado_solicitante
            JOIN aut_asignacion a ON a.id = szo.aut_asignacion_solicitante
             wHERE szo.aut_empleado_solicitante = :id AND szo.sol_estado = 'PENDIENTE'
            """, nativeQuery = true)
    Page<SolicitudDeReemplazo> findAllPendientesPorSolicitante(Pageable pageable, Long id);

    @Query(value = """
            SELECT szo.*
            FROM aut_solicitud_reemplazo szo
            JOIN aut_integrante i ON i.id = szo.aut_empleado_solicitante
            JOIN aut_asignacion a ON a.id = szo.aut_asignacion_solicitante
             wHERE szo.aut_empleado_solicitante = :id AND szo.sol_estado = 'APROBADA'
            """, nativeQuery = true)
    Page<SolicitudDeReemplazo> findAllNoPendientesPorSolicitante(Pageable pageable, Long id);


    @Query("""
            SELECT s
            FROM SolicitudDeReemplazo s
            WHERE s.estadoDeSolicitud = 'PENDIENTE'
            """
    )
    Page<SolicitudDeReemplazo> findAllPendientes(Pageable pageable);

    @Query("""
            SELECT s.id
            FROM SolicitudDeReemplazo s
            ORDER BY s.id DESC LIMIT 1""")
    Optional<Long> obtenerUltimoId();

}





