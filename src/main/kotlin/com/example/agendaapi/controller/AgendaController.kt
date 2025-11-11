package com.example.agendaapi.controller

import com.example.agendaapi.model.Agenda
import com.example.agendaapi.service.AgendaService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/agenda")
class AgendaController(
    private val agendaService: AgendaService
) {

    @GetMapping
    fun getAll(): List<Agenda> = agendaService.getAll()

    @GetMapping("/{name}")
    fun getByName(@PathVariable name: String): Agenda? = agendaService.getByName(name)

    @PostMapping
    fun create(@RequestBody agenda: Agenda): Agenda = agendaService.save(agenda)

    @PutMapping("/{id}/telefone/{telefone}")
    fun updateTelefone(
        @PathVariable id: Long,
        @PathVariable telefone: String
    ): String {
        agendaService.updateTelefone(id, telefone)
        return "Telefone atualizado!"
    }

    @PutMapping("/{id}/email/{email}")
    fun updateEmail(
        @PathVariable id: Long,
        @PathVariable email: String
    ): String {
        agendaService.updateEmail(id, email)
        return "Email atualizado!"
    }

    @DeleteMapping("/{name}")
    fun deleteByName(@PathVariable name: String): String {
        agendaService.delete(name)
        return "Registro removido!"
    }
}