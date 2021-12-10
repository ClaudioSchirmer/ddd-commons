package dev.cschirmer.ddd.commons.domain.valueobjects

import kotlin.test.*
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

internal class MoneyTest {

    @Test
    fun `Validate reais`() {
        listOf("1.99", "5.00", "100.00", "1400.25", "10765.34", "100654.20", "2100342.99", "765433454321987.85")
            .forEach {
                val defaultMoney = Money(it.toBigDecimal())
                assertEquals(it, defaultMoney.toString(), "Invalid string.")
                assertEquals("R$ $it", defaultMoney.toSymbolString(), "Invalid currency symbol.")
                assertEquals("BRL $it", defaultMoney.toCodeString(), "Invalid currency code.")

                val moneyInReais = Money.reais(it.toBigDecimal())
                assertEquals(it, moneyInReais.toString(), "Invalid string in reais.")
                assertEquals("R$ $it", moneyInReais.toSymbolString(), "Invalid currency symbol.")
                assertEquals("BRL $it", moneyInReais.toCodeString(), "Invalid currency code.")
            }


        assertTrue(Money(BigDecimal(666.66)).isValid())
        assertTrue(Money(27.50.toBigDecimal()).isValid())
        assertTrue(Money.reais(300.toBigDecimal()).isValid())

    }

    @Test
    fun `Validate dollars`() {
        listOf("1.00", "5.50", "500.00", "1999.56", "10632.15", "876655.12", "6732342.80", "398760000044442.12")
            .forEach {
                val moneyInDollars = Money.dollars(it.toBigDecimal())
                assertEquals(it, moneyInDollars.toString())
                assertEquals(it, moneyInDollars.toString(), "Invalid string.")
                assertEquals("$ $it", moneyInDollars.toLocaleString(Locale.US), "Invalid currency locale symbol.")
                assertEquals("USD $it", moneyInDollars.toCodeString(), "Invalid currency code.")
            }
    }

    @Test
    fun `Validate operator functions`() {
        // only integers
        assertEquals("20.00", (Money(10.toBigDecimal()) + Money(10.toBigDecimal())).toString())
        assertEquals("0.00", (Money(10.toBigDecimal()) - Money(10.toBigDecimal())).toString())
        assertEquals("100.00", (Money(10.toBigDecimal()) * Money(10.toBigDecimal())).toString())
        assertEquals("5.00", (Money(50.toBigDecimal()) / Money(10.toBigDecimal())).toString())

        // integers with decimals
        assertEquals("20.50", (Money(10.30.toBigDecimal()) + Money(10.20.toBigDecimal())).toString())
        assertEquals("0.10", (Money(10.20.toBigDecimal()) - Money(10.10.toBigDecimal())).toString())
        assertEquals("111.09", (Money(10.55.toBigDecimal()) * Money(10.53.toBigDecimal())).toString())
        assertEquals("4.92", (Money(50.25.toBigDecimal()) / Money(10.21.toBigDecimal())).toString())

        // negative numbers
        assertEquals("-20.50", (Money((-10.30).toBigDecimal()) + Money((-10.20).toBigDecimal())).toString())
        assertEquals("-0.10", (Money((-10.20).toBigDecimal()) - Money((-10.10).toBigDecimal())).toString())
        assertEquals("111.09", (Money((-10.55).toBigDecimal()) * Money((-10.53).toBigDecimal())).toString())
        assertEquals("4.92", (Money((-50.25).toBigDecimal()) / Money((-10.21).toBigDecimal())).toString())
    }

    @Test
    fun `Validate rounding modes`() {
        assertEquals("0.86", Money("0.865129276".toBigDecimal(), roundingMode = RoundingMode.DOWN).toString())
        assertEquals("0.87", Money("0.865129276".toBigDecimal(), roundingMode = RoundingMode.HALF_EVEN).toString())

        assertEquals("50.47", Money("50.475".toBigDecimal(), roundingMode = RoundingMode.DOWN).toString())
        assertEquals("50.48", Money("50.475".toBigDecimal(), roundingMode = RoundingMode.HALF_EVEN).toString())

        assertEquals("5.56", Money("5.56675".toBigDecimal(), roundingMode = RoundingMode.DOWN).toString())
        assertEquals("5.57", Money("5.56675".toBigDecimal(), roundingMode = RoundingMode.HALF_EVEN).toString())

        assertEquals("12.09", Money("12.0976123".toBigDecimal(), roundingMode = RoundingMode.DOWN).toString())
        assertEquals("12.10", Money("12.0976123".toBigDecimal(), roundingMode = RoundingMode.HALF_EVEN).toString())

        assertEquals("14.46", Money("14.467".toBigDecimal(), roundingMode = RoundingMode.DOWN).toString())
        assertEquals("14.47", Money("14.467".toBigDecimal(), roundingMode = RoundingMode.HALF_EVEN).toString())
    }
}
