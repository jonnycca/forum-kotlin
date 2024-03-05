package br.com.forum.repository

import br.com.forum.dto.TopicoPorCategoriaDto
import br.com.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicoRepository : JpaRepository<Topico, Long> {

    fun findByCursoNome(nomeCurso: String, paginacao: Pageable): Page<Topico>

    @Query(
        "SELECT new br.com.forum.dto.TopicoPorCategoria( curso.categoria, count(t)) " +
                "FROM topico t JOIN t.curso curso GROUP BY custo.categoria"
    )
    fun relatorio(): List<TopicoPorCategoriaDto>
}