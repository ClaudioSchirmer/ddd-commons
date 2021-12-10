package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.NotificationContext
import dev.cschirmer.ddd.kernel.domain.notifications.NotificationMessage
import dev.cschirmer.ddd.kernel.domain.valueobjects.ScalarValueObject

data class Email (
	override var value: String
) : ScalarValueObject<String>() {
	override fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean  {
		val pattern = "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?\$".toRegex(RegexOption.IGNORE_CASE)
		return if (pattern.matches(value)) {
			true
		} else {
			notificationContext?.addNotification(NotificationMessage(fieldName = fieldName, notification = InvalidEmailDomainNotification()))
			false
		}
	}
}