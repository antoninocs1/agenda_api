package com.example.agendaapi.dto

data class CompromissoResponse(
	val id: Long,
	val agendaId: Long,
	val agendaNome: String?,
	val titulo: String,
	val descricao: String?,
	val data: String,
	val hora: String,
	val status: String,
	val createdAt: String
)

