package com.example.agendaapi.controller

import com.example.agendaapi.model.Agenda
import com.example.agendaapi.service.AgendaService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import java.net.URI
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@RestController
@RequestMapping("/agenda")
class AgendaController(
    private val agendaService: AgendaService
) {

    @GetMapping
    @Operation(summary = "Listar agendas", description = "Retorna a lista de todas as agendas cadastradas.")
        fun getAll(): ResponseEntity<List<Agenda>> = ResponseEntity.ok(agendaService.getAll())

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar agenda por id", description = "Retorna a agenda identificada pelo id informado.")
    fun getById(@PathVariable id: Long): ResponseEntity<Agenda> {
        val agenda = agendaService.getById(id)
        return if (agenda != null) ResponseEntity.ok(agenda) else ResponseEntity.notFound().build()
    }

    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar agenda por nome", description = "Retorna a agenda correspondente ao nome informado.")
    fun getByNome(@PathVariable nome: String): ResponseEntity<Agenda> {
        val agenda = agendaService.getByNome(nome)
        return if (agenda != null) ResponseEntity.ok(agenda) else ResponseEntity.notFound().build()
    }

    @PostMapping
    @Operation(summary = "Criar agenda", description = "Cria uma nova agenda com os dados fornecidos no corpo da requisição.")
    fun create(@RequestBody agenda: Agenda): ResponseEntity<Agenda> {
        val saved = agendaService.save(agenda)
        val location: URI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/id/{id}")
            .buildAndExpand(saved.id)
            .toUri()
        return ResponseEntity.created(location).body(saved)
    }

    @PutMapping("/{id}/telefone/{telefone}")
    @Operation(summary = "Atualizar telefone", description = "Atualiza o telefone da agenda identificada pelo id informado.")
        fun updateTelefone(
            @PathVariable id: Long,
            @PathVariable telefone: String
        ): ResponseEntity<Void> {
            val existing = agendaService.getById(id)
            if (existing == null) return ResponseEntity.notFound().build()
            agendaService.updateTelefone(id, telefone)
            return ResponseEntity.ok().build()
        }

    @PutMapping("/{id}/email/{email}")
    @Operation(summary = "Atualizar email", description = "Atualiza o email da agenda identificada pelo id informado.")
        fun updateEmail(
            @PathVariable id: Long,
            @PathVariable email: String
        ): ResponseEntity<Void> {
            val existing = agendaService.getById(id)
            if (existing == null) return ResponseEntity.notFound().build()
            agendaService.updateEmail(id, email)
            return ResponseEntity.ok().build()
        }

    @DeleteMapping("/nome/{nome}")
    @Operation(summary = "Deletar agenda por nome", description = "Exclui a agenda que possui o nome informado.")
        fun deleteByName(@PathVariable nome: String): ResponseEntity<Void> {
            val existing = agendaService.getByNome(nome)
            if (existing == null) return ResponseEntity.notFound().build()
            agendaService.delete(nome)
            return ResponseEntity.noContent().build()
        }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Deletar agenda por id", description = "Exclui a agenda identificada pelo id informado.")
        fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
            val existing = agendaService.getById(id)
            if (existing == null) return ResponseEntity.notFound().build()
            agendaService.deleteById(id)
            return ResponseEntity.noContent().build()
        }
}