package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationContext
import br.dev.schirmer.ddd.kernel.domain.valueobjects.ScalarValueObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

data class Money(
    override var value: BigDecimal,
    val currency: Currency = BRL,
    val roundingMode: RoundingMode = RoundingMode.HALF_EVEN
) : ScalarValueObject<BigDecimal>() {

    companion object {
        private val BRL = Currency.getInstance("BRL")
        private val USD = Currency.getInstance("USD")

        fun reais(amount: BigDecimal) = Money(amount, BRL)
        fun dollars(amount: BigDecimal) = Money(amount, USD)
    }

    init {
        value = value.setScale(currency.defaultFractionDigits, roundingMode)
    }

    override fun toString() = value.toString()
    override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?) = true

    fun toSymbolString() = "${currency.symbol} $value" // R$ - US$
    fun toCodeString() = "${currency.currencyCode} $value" // BRL - USD
    fun toLocaleString(locale: Locale?) = "${currency.getSymbol(locale)} $value" // Locale.US = $

    operator fun plus(other: Money) = Money(value + other.value)
    operator fun minus(other: Money) = Money(value - other.value)
    operator fun times(other: Money) = Money(value * other.value)
    operator fun div(other: Money) = Money(value / other.value)
}