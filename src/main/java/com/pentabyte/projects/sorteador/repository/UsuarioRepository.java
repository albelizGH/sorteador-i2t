package com.pentabyte.projects.sorteador.repository;

import com.pentabyte.projects.sorteador.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
