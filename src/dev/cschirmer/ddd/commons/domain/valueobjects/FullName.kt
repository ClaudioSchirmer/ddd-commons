package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.NotificationContext
import dev.cschirmer.ddd.kernel.domain.notifications.NotificationMessage
import dev.cschirmer.ddd.kernel.domain.valueobjects.ScalarValueObject

data class FullName(
	override var value: String
) : ScalarValueObject<String>() {
	override fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean = when {
		value.length <= 6 -> {
			notificationContext?.addNotification(NotificationMessage(fieldName = fieldName, notification = InvalidFullNameLengthDomainNotification()))
			false
		}
		value.split(" ").count() < 2 -> {
			notificationContext?.addNotification(NotificationMessage(fieldName = fieldName, notification = InvalidFullNameWordsDomainNotification()))
			false
		}
		else -> true
	}
}