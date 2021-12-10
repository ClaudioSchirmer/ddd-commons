package dev.cschirmer.ddd.commons.domain.valueobjects


import dev.cschirmer.ddd.commons.domain.valueobjects.DateTime.Companion.DEFAULT_ZONE_ID
import org.junit.Test
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class DateTimeTest {

    // 2021-12-01T15:07:45.999999999Z[UTC]
    private val zonedDateTime = ZonedDateTime.of(2021, 12, 1, 12, 5, 45, 999999999, ZoneId.of(DEFAULT_ZONE_ID))

    @Test
    fun `Validate DateTime and RegistrationDateTime values`() {
        assertTrue(DateTime(ZonedDateTime.now(ZoneId.of(DEFAULT_ZONE_ID))).value.toString().isNotEmpty())
    }

    @Test
    fun `Validate DateTime format function`() {
        val dateTime = DateTime(zonedDateTime)
        assertEquals("01/12/2021", dateTime.toFormat(Date.PT_BR_DATE_PATTERN))
        assertEquals("01/12/2021 12:05:45", dateTime.toFormat(DateTime.PT_BR_DATETIME_PATTERN))
    }

    @Test
    fun `Validate other DateTime functions`() {
        val dateTime = DateTime(zonedDateTime)
        assertEquals("2021-12-01T12:05:45.999Z", dateTime.toDateTimeString())
        assertEquals("2021-12-01", dateTime.toDateString())
        assertEquals("12:05:45.999", dateTime.toTimeString())
        assertEquals("2021-12-01T09:05:45.999-03:00", dateTime.toAmericaSaoPauloString())
        assertEquals("01/12/2021 09:05:45", dateTime.toAmericaSaoPauloString(DateTime.PT_BR_DATETIME_PATTERN))
        assertTrue(dateTime.isValid())
    }

    @Test
    fun `Validate LocalDateTime`() {
        val dateTime = DateTime.fromLocalDateTimeUTC(zonedDateTime.toLocalDateTime())
        assertEquals("2021-12-01T12:05:45.999Z", dateTime.toDateTimeString())
        assertEquals("2021-12-01", dateTime.toDateString())
        assertEquals("12:05:45.999", dateTime.toTimeString())
        assertEquals("2021-12-01T09:05:45.999-03:00", dateTime.toAmericaSaoPauloString())
        assertTrue(dateTime.isValid())
        assertEquals("2021-12-01T12:05:45.999999999", dateTime.localDateTimeUTCValue.toString())
    }
}