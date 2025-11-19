package com.example.agendaapi.controller

import com.example.agendaapi.dto.CompromissoRequest
import com.example.agendaapi.dto.CompromissoResponse
import com.example.agendaapi.model.Compromisso
import com.example.agendaapi.model.Status
import com.example.agendaapi.repository.AgendaRepository
import com.example.agendaapi.service.CompromissoService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RestController
@RequestMapping("/compromisso")
class CompromissoController(
    private val compromissoService: CompromissoService,
    private val agendaRepository: AgendaRepository
) {

    @GetMapping
    @Operation(summary = "Listar compromissos", description = "Retorna todos os compromissos cadastrados.")
    fun getAll(): List<Compromisso> = compromissoService.getAll()

    @GetMapping("/{id}")
    @Operation(summary = "Buscar compromisso por id", description = "Retorna o compromisso identificado pelo id informado.")
    fun getById(@PathVariable id: Long): Compromisso? = compromissoService.getById(id)

    @GetMapping("/agenda/{agendaId}")
    @Operation(summary = "Listar compromissos por agenda", description = "Retorna os compromissos associados à agenda informada pelo id.")
    fun getByAgenda(@PathVariable agendaId: Long): List<Compromisso> = compromissoService.getByAgendaId(agendaId)

    @PostMapping
    @Operation(summary = "Criar compromisso", description = "Cria novo compromisso. Envie agendaId, titulo, descricao, data (YYYY-MM-DD), hora (HH:mm[:ss]) e opcional status.")
    fun create(@RequestBody req: CompromissoRequest): CompromissoResponse {
        // valida campos obrigatórios para criação
        val agendaId = req.agendaId ?: throw IllegalArgumentException("agendaId é obrigatório")
        val titulo = req.titulo ?: throw IllegalArgumentException("titulo é obrigatório")
        val dataStr = req.data ?: throw IllegalArgumentException("data é obrigatória")
        val horaStr = req.hora ?: throw IllegalArgumentException("hora é obrigatória")

        val agenda = agendaRepository.findById(agendaId).orElseThrow { IllegalArgumentException("Agenda com id $agendaId não encontrada") }

        val data = LocalDate.parse(dataStr)
        val hora = LocalTime.parse(horaStr)

        val status = req.status?.let { s ->
            Status.values().firstOrNull { it.value.equals(s, true) || it.name.equals(s, true) }
        } ?: Status.PENDENTE

        val compromisso = Compromisso(
            agenda = agenda,
            titulo = titulo,
            descricao = req.descricao,
            data = data,
            hora = hora,
            status = status
        )

        val saved = compromissoService.save(compromisso)

        return CompromissoResponse(
            id = saved.id,
            agendaId = saved.agenda.id,
            agendaNome = saved.agenda.nome,
            titulo = saved.titulo,
            descricao = saved.descricao,
            data = saved.data.format(DateTimeFormatter.ISO_LOCAL_DATE),
            hora = saved.hora.format(DateTimeFormatter.ISO_TIME),
            status = saved.status.toString(),
            createdAt = saved.createdAt.toString()
        )
    }

    @PutMapping
    @Operation(summary = "Atualizar compromisso", description = "Atualiza um compromisso; envie um objeto com `id` e os campos a alterar (titulo, descricao, data, hora, status, agendaId). Campos omitidos permanecem inalterados.")
    fun update(@RequestBody req: CompromissoRequest): String = compromissoService.update(req)

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar compromisso", description = "Remove o compromisso identificado pelo id informado.")
    fun delete(@PathVariable id: Long): String {
        return compromissoService.delete(id)
    }
}
