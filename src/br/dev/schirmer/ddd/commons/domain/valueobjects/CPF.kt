package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationContext
import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationMessage
import br.dev.schirmer.ddd.kernel.domain.valueobjects.ValueObject
import br.dev.schirmer.utils.kotlin.string.onlyDigits

@JvmInline
value class CPF(
    override val value: String
) : ValueObject<String> {

    val maskedValue
        get() = with(value.onlyDigits<String>()) {
            "${take(3)}.${substring(3, 6)}.${substring(6, 9)}-${drop(9)}"
        }

    val unmaskedValue get() = value.onlyDigits<String>()

    override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean = if (!isValid()) {
        notificationContext?.addNotification(
            NotificationMessage(
                fieldName = fieldName,
                notification = InvalidCPFDomainNotification()
            )
        )
        false
    } else {
        true
    }

    private fun isValid(): Boolean {
        with(unmaskedValue) {

            if (length != 11) {
                return false
            }

            var fDig = 0
            (10 downTo 2).forEachIndexed { index, indexRev ->
                fDig += this[index].digitToInt() * indexRev
            }

            ((fDig * 10) % 11).run {
                fDig = if (this == 10) {
                    0
                } else {
                    this
                }
            }

            var sDig = 0
            (11 downTo 2).forEachIndexed { index, indexRev ->
                sDig += this[index].digitToInt() * indexRev
            }

            ((sDig * 10) % 11).run {
                sDig = if (this == 10) {
                    0
                } else {
                    this
                }
            }

            return this[9].digitToInt() == fDig && this[10].digitToInt() == sDig
        }
    }
}
