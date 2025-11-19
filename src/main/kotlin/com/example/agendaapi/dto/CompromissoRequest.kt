package com.example.agendaapi.dto

/**
 * DTO único para criar/atualizar Compromisso.
 * - `id` nulo em POST (criação)
 * - em PUT, envie `id` e apenas os campos que deseja alterar (campos nulos serão ignorados)
 */
data class CompromissoRequest(
	val id: Long? = null,
	val agendaId: Long? = null,
	val titulo: String? = null,
	val descricao: String? = null,
	// ISO date string e.g. "2025-11-18"
	val data: String? = null,
	// time string e.g. "18:40:00" or "18:40"
	val hora: String? = null,
	// Optional status: "pendente", "concluido", "cancelado"
	val status: String? = null
)

