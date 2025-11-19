package com.example.agendaapi.service

import com.example.agendaapi.model.Compromisso
import com.example.agendaapi.dto.CompromissoRequest
import org.springframework.stereotype.Service

@Service
interface CompromissoService {
    fun getAll(): List<Compromisso>
    fun getById(id: Long): Compromisso?
    fun getByAgendaId(agendaId: Long): List<Compromisso>
    fun save(compromisso: Compromisso): Compromisso
    fun update(compromisso: Compromisso): String
    fun update(compromissoReq: CompromissoRequest): String
    fun delete(id: Long): String
}
