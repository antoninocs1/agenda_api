package com.example.agendaapi.service

import com.example.agendaapi.dto.CompromissoRequest
import com.example.agendaapi.model.Compromisso
import com.example.agendaapi.model.Status
import com.example.agendaapi.repository.CompromissoRepository
import com.example.agendaapi.repository.AgendaRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime

@Service
class CompromissoServiceImpl(
    val compromissoRepository: CompromissoRepository,
    val agendaRepository: AgendaRepository
) : CompromissoService {

    override fun getAll(): List<Compromisso> = compromissoRepository.findAll()

    override fun getById(id: Long): Compromisso? = compromissoRepository.findById(id).orElse(null)

    override fun getByAgendaId(agendaId: Long): List<Compromisso> = compromissoRepository.findByAgenda_Id(agendaId)

    override fun save(compromisso: Compromisso): Compromisso {
        // valida se agenda existe
        val agendaId = compromisso.agenda.id
        if (!agendaRepository.existsById(agendaId))
            throw IllegalArgumentException("Agenda com id $agendaId não existe")

        return compromissoRepository.save(compromisso)
    }

    override fun update(compromisso: Compromisso): String {
        if (!compromissoRepository.existsById(compromisso.id))
            return "Registro não existe na base de dados"

        compromissoRepository.save(compromisso)
        return "Compromisso id ${compromisso.id} alterado com sucesso"
    }

    override fun update(compromissoReq: CompromissoRequest): String {
        val id = compromissoReq.id ?: return "Campo 'id' é obrigatório para atualização"
        val opt = compromissoRepository.findById(id)
        if (!opt.isPresent) return "Registro não existe na base de dados"

        val existing = opt.get()

        val novoAgenda = compromissoReq.agendaId?.let {
            if (!agendaRepository.existsById(it)) throw IllegalArgumentException("Agenda com id $it não existe")
            agendaRepository.findById(it).get()
        } ?: existing.agenda

        val novoTitulo = compromissoReq.titulo ?: existing.titulo
        val novaDescricao = compromissoReq.descricao ?: existing.descricao
        val novaData = compromissoReq.data?.let { LocalDate.parse(it) } ?: existing.data
        val novaHora = compromissoReq.hora?.let { LocalTime.parse(it) } ?: existing.hora
        val novoStatus = compromissoReq.status?.let { Status.values().firstOrNull { s -> s.value.equals(it, true) || s.name.equals(it, true) } } ?: existing.status

        val atualizado = existing.copy(
            agenda = novoAgenda,
            titulo = novoTitulo,
            descricao = novaDescricao,
            data = novaData,
            hora = novaHora,
            status = novoStatus
        )

        compromissoRepository.save(atualizado)
        return "Compromisso id ${atualizado.id} alterado com sucesso"
    }

    override fun delete(id: Long): String {
        if (!compromissoRepository.existsById(id))
            return "Registro não existe na base de dados"

        compromissoRepository.deleteById(id)
        return "Registro removido com sucesso!"
    }
}
