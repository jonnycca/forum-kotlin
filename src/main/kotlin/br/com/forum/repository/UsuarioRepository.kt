package br.com.forum.repository

import br.com.forum.model.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository:JpaRepository<Usuario, Long> {
}