package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationContext
import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationMessage
import br.dev.schirmer.ddd.kernel.domain.valueobjects.ValueObject

@JvmInline
value class FullName(
	override val value: String
) : ValueObject<String> {
	override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean = when {
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