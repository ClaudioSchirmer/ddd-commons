package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationContext
import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationMessage
import br.dev.schirmer.ddd.kernel.domain.valueobjects.ScalarValueObject
import br.dev.schirmer.utils.kotlin.string.onlyDigits

data class CNPJ(
	override var value: String
) : ScalarValueObject<String>() {

	val maskedValue get() = with(value) {
		"${take(2)}.${substring(2,5)}.${substring(5,8)}/${substring(8,12)}-${drop(12)}"
	}

	init {
		value = value.onlyDigits()
	}

	override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean  = if (!isValid()) {
		notificationContext?.addNotification(NotificationMessage(fieldName = fieldName, notification = InvalidCNPJDomainNotification()))
		true
	} else {
		false
	}

	private fun isValid(): Boolean {
		with(value) {

			if (length != 14) {
				return false
			}

			var fDig = 0
			(5 downTo 2).forEachIndexed { index, indexRev ->
				fDig += this[index].digitToInt() * indexRev
			}
			(9 downTo 2).forEachIndexed { index, indexRev ->
				fDig += this[index+4].digitToInt() * indexRev
			}

			(fDig % 11).run {
				fDig = if (this < 2) {
					0
				} else {
					11 - this
				}
			}

			var sDig = 0
			(6 downTo 2).forEachIndexed { index, indexRev ->
				sDig += this[index].digitToInt() * indexRev
			}
			(9 downTo 2).forEachIndexed { index, indexRev ->
				sDig += this[index+5].digitToInt() * indexRev
			}

			(sDig % 11).run {
				sDig = if (this < 2) {
					0
				} else {
					11 - this
				}
			}

			return this[12].digitToInt() == fDig && this[13].digitToInt() == sDig
		}
	}

}
