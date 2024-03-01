package br.com.forum.service

import br.com.forum.exception.NotFoundException
import br.com.forum.model.Curso
import br.com.forum.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(private val repository: CursoRepository) {

    companion object{
        private const val notFoundMessage = "Usuario não encontrado"
    }
    fun buscaPorId(id: Long): Curso{
        return repository.findById(id).orElseThrow{ NotFoundException(notFoundMessage) }
    }
}
