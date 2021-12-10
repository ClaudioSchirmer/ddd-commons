package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.NotificationContext
import dev.cschirmer.ddd.kernel.domain.notifications.NotificationMessage
import dev.cschirmer.ddd.kernel.domain.valueobjects.ScalarValueObject
import dev.cschirmer.utils.kotlin.extensions.onlyDigits

data class CPF(
    override var value: String
) : ScalarValueObject<String>() {

    val maskedValue
        get() = with(value) {
            "${take(3)}.${substring(3, 6)}.${substring(6, 9)}-${drop(9)}"
        }

    init {
        value = value.onlyDigits()
    }

    override fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean = if (!isValid()) {
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
        with(value) {

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
