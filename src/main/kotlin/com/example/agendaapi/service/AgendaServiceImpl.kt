package com.example.agendaapi.service

import com.example.agendaapi.model.Agenda
import com.example.agendaapi.repository.AgendaRepository
import org.springframework.stereotype.Service

@Service
class AgendaServiceImpl(val agendaRepository: AgendaRepository) : AgendaService {

    override fun getAll(): List<Agenda> = agendaRepository.findAll()

    override fun getByNome(nome: String): Agenda? = agendaRepository.findByNome(nome)

    override fun save(agenda: Agenda): Agenda = agendaRepository.save(agenda)

    override fun update(agenda: Agenda): String {
        if (!agendaRepository.existsById(agenda.id))
            return "Registro não existe na base de dados"

        agendaRepository.save(agenda)

        return "Agenda id ${agenda.id} alterado com sucesso"
    }

    override fun updateTelefone(id: Long, telefone: String) = agendaRepository.updateTelefone(id, telefone)

    override fun updateEmail(id: Long, email: String) = agendaRepository.updateEmail(id, email)

    override fun delete(nome: String): String {
        /*if (!agendaRepository.existsById(id))
            return "Registro não existe na base de dados"
*/
                agendaRepository.deleteByNome(nome)

        return "Registro removido com sucesso!"
    }
    
            override fun deleteById(id: Long): String {
                if (!agendaRepository.existsById(id))
                    return "Registro não existe na base de dados"
        
                agendaRepository.deleteById(id)
                return "Registro removido com sucesso!"
            }
}