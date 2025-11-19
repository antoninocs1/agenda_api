package com.example.agendaapi.controller

import com.example.agendaapi.model.Agenda
import com.example.agendaapi.service.AgendaService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/agenda")
class AgendaController(
    private val agendaService: AgendaService
) {

    @GetMapping
    @Operation(summary = "Listar agendas", description = "Retorna a lista de todas as agendas cadastradas.")
    fun getAll(): List<Agenda> = agendaService.getAll()

    @GetMapping("/{nome}")
    @Operation(summary = "Buscar agenda por nome", description = "Retorna a agenda correspondente ao nome informado.")
    fun getByNome(@PathVariable nome: String): Agenda? = agendaService.getByNome(nome)

    @PostMapping
    @Operation(summary = "Criar agenda", description = "Cria uma nova agenda com os dados fornecidos no corpo da requisição.")
    fun create(@RequestBody agenda: Agenda): Agenda = agendaService.save(agenda)

    @PutMapping("/{id}/telefone/{telefone}")
    @Operation(summary = "Atualizar telefone", description = "Atualiza o telefone da agenda identificada pelo id informado.")
    fun updateTelefone(
        @PathVariable id: Long,
        @PathVariable telefone: String
    ): String {
        agendaService.updateTelefone(id, telefone)
        return "Telefone atualizado!"
    }

    @PutMapping("/{id}/email/{email}")
    @Operation(summary = "Atualizar email", description = "Atualiza o email da agenda identificada pelo id informado.")
    fun updateEmail(
        @PathVariable id: Long,
        @PathVariable email: String
    ): String {
        agendaService.updateEmail(id, email)
        return "Email atualizado!"
    }

    @DeleteMapping("/{nome}")
    @Operation(summary = "Deletar agenda por nome", description = "Exclui a agenda que possui o nome informado.")
    fun deleteByName(@PathVariable nome: String): String {
        agendaService.delete(nome)
        return "Registro removido!"
    }
}