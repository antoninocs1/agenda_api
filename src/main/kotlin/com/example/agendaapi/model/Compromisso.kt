package com.example.agendaapi.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "compromisso")
data class Compromisso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_id", nullable = false)
    val agenda: Agenda,

    @Column(nullable = false, length = 100)
    val titulo: String,

    @Column(length = 600)
    val descricao: String? = null,

    @Column(nullable = false)
    val data: LocalDate,

    @Column(nullable = false)
    val hora: LocalTime,

    @Column(nullable = false)
    @Convert(converter = StatusConverter::class)
    val status: Status = Status.PENDENTE,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)

enum class Status(val value: String) {
    PENDENTE("pendente"),
    CONCLUIDO("concluido"),
    CANCELADO("cancelado");

    override fun toString(): String = value
}

@Converter(autoApply = false)
class StatusConverter : AttributeConverter<Status, String> {
    override fun convertToDatabaseColumn(attribute: Status?): String? {
        return attribute?.value
    }

    override fun convertToEntityAttribute(dbData: String?): Status? {
        if (dbData == null) return null
        return Status.values().firstOrNull { it.value == dbData }
            ?: throw IllegalArgumentException("Unknown status value: $dbData")
    }
}
