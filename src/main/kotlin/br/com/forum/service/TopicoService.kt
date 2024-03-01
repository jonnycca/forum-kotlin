package br.com.forum.service

import br.com.forum.dto.AtualizarTopicoForm
import br.com.forum.dto.NovoTopicoForm
import br.com.forum.dto.TopicoView
import br.com.forum.exception.NotFoundException
import br.com.forum.mapper.TopicoFormMapper
import br.com.forum.mapper.TopicoViewMapper
import br.com.forum.model.Curso
import br.com.forum.model.Topico
import br.com.forum.model.Usuario
import br.com.forum.repository.TopicoRepository
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class TopicoService(
    private var topicoRepository: TopicoRepository,
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper
) {

    companion object {
        private const val notFoundMessage = "Topico não encontrado"
    }

    fun listar(): List<TopicoView> {
        return topicoRepository.findAll().stream().map { topico -> topicoViewMapper.map(topico) }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicoRepository.findById(id).stream().filter { topico -> topico.id == id }
            .findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(novoTopicoForm: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(novoTopicoForm)
        topicoRepository.save(topico)
        return topicoViewMapper.map(topico)

    }

    fun atualizar(atualizarTopico: AtualizarTopicoForm) :TopicoView{
        val topico = topicoRepository.findById(atualizarTopico.id).stream().filter { topico -> topico.id == atualizarTopico.id }
            .findFirst().orElseThrow{NotFoundException(notFoundMessage)}

        topico.titulo = atualizarTopico.titulo
        topico.mensagem = atualizarTopico.mensagem

        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
        topicoRepository.deleteById(id)
    }

}