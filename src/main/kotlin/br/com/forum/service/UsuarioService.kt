package br.com.forum.service

import br.com.forum.exception.NotFoundException
import br.com.forum.model.Usuario
import br.com.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(private val repository: UsuarioRepository) {

    companion object{
        private const val notFoundMessage = "Usuario não encontrado"
    }

    fun buscarPorId(id: Long): Usuario {
        return repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
    }

}
