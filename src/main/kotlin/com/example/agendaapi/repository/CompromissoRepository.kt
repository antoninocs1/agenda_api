package com.example.agendaapi.repository

import com.example.agendaapi.model.Compromisso
import com.example.agendaapi.model.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompromissoRepository : JpaRepository<Compromisso, Long> {

    fun findByAgenda_Id(agendaId: Long): List<Compromisso>

    fun findByStatus(status: Status): List<Compromisso>
}
