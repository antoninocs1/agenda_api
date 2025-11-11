package com.example.agendaapi.repository

import com.example.agendaapi.model.Agenda
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional

@Repository
interface AgendaRepository : JpaRepository<Agenda, Long> {

    // Busca todos os registros (SQL nativo)
    @Query(value = "SELECT * FROM agenda", nativeQuery = true)
    fun findAllAgenda(): List<Agenda>

    // Busca um registro pelo nome
    @Query(value = "SELECT * FROM agenda WHERE name = :name", nativeQuery = true)
    fun findByName(@Param("name") name: String): Agenda?

    // Busca registro pelo cpf
    @Query(value = "SELECT * FROM agenda WHERE cpf = :cpf", nativeQuery = true)
    fun findByCPF(@Param("cpf") cpf: String): List<Agenda>

    // Atualiza telefone
    @Modifying
    @Transactional
    @Query(value = "UPDATE agenda SET telefone = :telefone WHERE id = :id", nativeQuery = true)
    fun updateTelefone(@Param("id") id: Long, @Param("telefone") telefone: String)

    // Atualiza email
    @Modifying
    @Transactional
    @Query(value = "UPDATE agenda SET email = :email WHERE id = :id", nativeQuery = true)
    fun updateEmail(@Param("id") id: Long, @Param("email") email: String)

    // Deleta registro pelo nome
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM agenda WHERE name = :name", nativeQuery = true)
    fun deleteByName(@Param("name") name: String)

}