package com.example.agendaapi.service

import com.example.agendaapi.model.Agenda
import org.springframework.stereotype.Service

@Service
interface AgendaService {
    fun getAll(): List<Agenda>
    fun getByName(name: String): Agenda?
    fun save(agenda: Agenda): Agenda
    fun update(agenda: Agenda): String
    fun updateTelefone( id: Long, Telefone: String)
    fun updateEmail( id: Long, Email: String)
    fun delete(name: String): String
}
