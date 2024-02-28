package br.com.forum.mapper

import br.com.forum.dto.NovoTopicoForm
import br.com.forum.model.Topico
import br.com.forum.service.CursoService
import br.com.forum.service.UsuarioService

class TopicoFormMapper(
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
) : Mapper<NovoTopicoForm, Topico> {

    override fun map(t: NovoTopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscaPorId(t.idCurso),
            autor = usuarioService.buscarPorId(t.idAutor)
        )
    }
}