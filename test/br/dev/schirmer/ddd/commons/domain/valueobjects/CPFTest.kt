package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.utils.kotlin.extensions.onlyDigits
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CPFTest {

    @Test
    fun validCPF() = runBlocking {
        listOf("888.772.910-79", "718.006.690-70", "801.460.340-07", "93540160060", "14472903059").forEach {
            assertTrue(CPF(it).isValid(), "CPF deveria ser válido.")
            assertEquals(it.onlyDigits(), CPF(it).value, "CPF deveria manter o valor sem máscaras")
        }
    }

    @Test
    fun invalidCPF() = runBlocking {
        listOf("888.772.910-29", "728.006.690-70", "802.460.340-07", "13540160060", "14272903059", "", "--", "01234567891").forEach {
            assertFalse(CPF(it).isValid(), "CPF deveria ser inválido.")
        }
    }


}