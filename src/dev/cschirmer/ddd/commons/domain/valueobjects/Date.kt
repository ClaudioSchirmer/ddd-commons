package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.NotificationContext
import dev.cschirmer.ddd.kernel.domain.valueobjects.ScalarValueObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Date(override var value: LocalDate) : ScalarValueObject<LocalDate>() {

    constructor(localDateString: String, pattern: String = DATE_PATTERN) : this(
        LocalDate.parse(
            localDateString,
            DateTimeFormatter.ofPattern(pattern)
        )
    )

    fun toFormat(pattern: String): String = runCatching {
        value.format(DateTimeFormatter.ofPattern(pattern))
    }.getOrElse {
        throw DateTimeFormatException()
    }

    fun toPTBRString() = value.format(DateTimeFormatter.ofPattern(PT_BR_DATE_PATTERN))

    override fun toString(): String = value.format(DateTimeFormatter.ofPattern(DATE_PATTERN))

    override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean = true

    companion object {
        const val DATE_PATTERN = "yyyy-MM-dd"
        const val PT_BR_DATE_PATTERN = "dd/MM/yyyy"
        fun now() = Date(LocalDate.now())
        fun localDateNow(): LocalDate = LocalDate.now()
    }
}