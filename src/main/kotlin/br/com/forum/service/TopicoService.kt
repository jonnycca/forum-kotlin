package br.com.forum.service

import br.com.forum.dto.AtualizarTopicoForm
import br.com.forum.dto.NovoTopicoForm
import br.com.forum.dto.TopicoPorCategoriaDto
import br.com.forum.dto.TopicoView
import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.TopicoFormMapper
import br.com.forum.mapper.TopicoViewMapper
import br.com.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class TopicoService(
    private var topicoRepository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper
) {

    companion object {
        private const val notFoundMessage = "Topico não encontrado"
    }

    fun listar(
        nomeCurso: String?,
        pageable: Pageable
    ): Page<TopicoView> {
        val topicos = if (nomeCurso == null) {
            topicoRepository.findAll(pageable)
        } else {
            topicoRepository.findByCursoNome(nomeCurso, pageable)
        }
        return topicos.map { topico -> topicoViewMapper.map(topico) }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicoRepository.findById(id).stream().filter { topico -> topico.id == id }
            .findFirst().orElseThrow { NotFoundException(notFoundMessage) }
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(novoTopicoForm: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(novoTopicoForm)
        topicoRepository.save(topico)
        return topicoViewMapper.map(topico)

    }

    fun atualizar(atualizarTopico: AtualizarTopicoForm): TopicoView {
        val topico =
            topicoRepository.findById(atualizarTopico.id).stream().filter { topico -> topico.id == atualizarTopico.id }
                .findFirst().orElseThrow { NotFoundException(notFoundMessage) }

        topico.titulo = atualizarTopico.titulo
        topico.mensagem = atualizarTopico.mensagem

        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
        topicoRepository.deleteById(id)
    }

    fun relatorio(): List<TopicoPorCategoriaDto> {
        return topicoRepository.relatorio()
    }

}