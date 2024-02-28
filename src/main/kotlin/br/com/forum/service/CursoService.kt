package br.com.forum.service

import br.com.forum.model.Curso
import org.springframework.stereotype.Service
import java.util.*

@Service
class CursoService(var cursos: List<Curso>) {

    init{
        val curso = Curso(
            id = 1,
            nome = "kotlin",
            categoria = "Programacao"
        )
        cursos = Arrays.asList(curso)
    }

    fun buscaPorId(id: Long): Curso{
        return cursos.stream().filter { curso ->
            curso.id == id
        }.findFirst().get()
    }
}
