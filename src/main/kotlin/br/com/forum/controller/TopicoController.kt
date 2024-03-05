package br.com.forum.controller

import br.com.forum.dto.AtualizarTopicoForm
import br.com.forum.dto.NovoTopicoForm
import br.com.forum.dto.TopicoView
import br.com.forum.service.TopicoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/topicos")
class TopicoController(
    @Autowired private val topicoService: TopicoService
) {

    @GetMapping
    @Cacheable("topicos")
    fun listar(
        @RequestParam(required = false) nomeCurso: String?,
        @PageableDefault(size = 5, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable
    ): Page<TopicoView> {
        return topicoService.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return topicoService.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun cadastrar(
        @RequestBody @Valid novoTopicoForm: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView> {
        val topicoCriado = topicoService.cadastrar(novoTopicoForm)
        val uri = uriBuilder.path("/topicos/{$topicoCriado.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoCriado)
    }

    @PutMapping()
    @Transactional
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun atualizar(@RequestBody @Valid atualizarTopico: AtualizarTopicoForm): ResponseEntity<TopicoView> {
        val topicoAtualizado = topicoService.atualizar(atualizarTopico)
        return ResponseEntity.ok(topicoAtualizado)
    }

    @DeleteMapping("/id")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = ["topicos"], allEntries = true)
    fun deletar(@PathVariable id: Long) {
        topicoService.deletar(id)
    }
}
