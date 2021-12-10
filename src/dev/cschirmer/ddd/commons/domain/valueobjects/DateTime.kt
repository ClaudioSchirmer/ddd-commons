package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.NotificationContext
import dev.cschirmer.ddd.kernel.domain.valueobjects.ScalarValueObject
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

open class DateTime(final override var value: ZonedDateTime) : ScalarValueObject<ZonedDateTime>() {

    constructor(zonedDateTimeString: String, pattern: String = RFC_3339_PATTERN) : this(
        ZonedDateTime.parse(zonedDateTimeString, DateTimeFormatter.ofPattern(pattern))
    )

    init {
        value = value.withZoneSameInstant(ZoneId.of(DEFAULT_ZONE_ID))
    }

    val localDateTimeUTCValue: LocalDateTime get() = value.toLocalDateTime()

    fun toFormat(pattern: String): String = runCatching {
        value.format(DateTimeFormatter.ofPattern(pattern))
    }.getOrElse {
        throw DateTimeFormatException()
    }

    fun toDateString(): String = toFormat(Date.DATE_PATTERN)

    fun toDateTimeString(): String = toFormat(RFC_3339_PATTERN)

    fun toTimeString(): String = toFormat(TIME_PATTERN)

    fun toAmericaSaoPauloString(pattern: String = RFC_3339_PATTERN): String =
        value.withZoneSameInstant(ZoneId.of(BET_ZONE_ID))
            .format(DateTimeFormatter.ofPattern(pattern))

    override fun isValid(fieldName: String?, notificationContext: NotificationContext?) = true

    override fun equals(other: Any?) = other is DateTime && other.value == this.value

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    companion object {
        const val RFC_3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        const val PT_BR_DATETIME_PATTERN = "dd/MM/yyyy HH:mm:ss"
        const val TIME_PATTERN = "HH:mm:ss.SSS"
        const val BET_ZONE_ID = "America/Sao_Paulo"
        const val DEFAULT_ZONE_ID = "UTC"

        fun fromLocalDateTimeUTC(localDateTimeUTC: LocalDateTime) =
            DateTime(ZonedDateTime.of(localDateTimeUTC, ZoneId.of(DEFAULT_ZONE_ID)))

        fun now() = DateTime(ZonedDateTime.now(ZoneId.of(DEFAULT_ZONE_ID)))
        fun zonedDateTimeNow(): ZonedDateTime = ZonedDateTime.now(ZoneId.of(DEFAULT_ZONE_ID))
    }
}