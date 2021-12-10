package dev.cschirmer.ddd.commons.domain.valueobjects

import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFails


class DateTest {
    @Test
    fun `valida date`() {
        Date("2021-11-29").run {
            assertEquals(LocalDate.of(2021, 11, 29), value, "Data deveria estar igual")
        }

        Date(LocalDate.of(2021, 11, 29)).run {
            assertEquals("2021-11-29", toString(), "Data deveria estar igual")
            assertEquals("29/11/2021", toPTBRString(), "Data deveria estar igual")
            assertEquals("29/11/2021", toFormat(Date.PT_BR_DATE_PATTERN), "Data deveria estar igual")
        }

        assertFails {
            Date("abacaxi")
        }

        assertEquals(LocalDate.now(), Date.localDateNow(), "Data deveria ser igual")

        assertEquals(Date.now().value, LocalDate.now(), "Data deveria ser igual")
    }
}