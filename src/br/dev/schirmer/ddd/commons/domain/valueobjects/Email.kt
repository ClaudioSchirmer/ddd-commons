package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationContext
import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationMessage
import br.dev.schirmer.ddd.kernel.domain.valueobjects.ScalarValueObject

data class Email (
	override var value: String
) : ScalarValueObject<String>() {
	override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean  {
		val pattern = "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?\$".toRegex(RegexOption.IGNORE_CASE)
		return if (pattern.matches(value)) {
			true
		} else {
			notificationContext?.addNotification(NotificationMessage(fieldName = fieldName, notification = InvalidEmailDomainNotification()))
			false
		}
	}
}