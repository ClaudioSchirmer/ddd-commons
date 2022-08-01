package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationContext
import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationMessage
import br.dev.schirmer.ddd.kernel.domain.valueobjects.ScalarValueObject

data class Nickname(
    override var value: String
) : ScalarValueObject<String>() {
    override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean = when {
        value.length < 4 -> {
            notificationContext?.addNotification(NotificationMessage(fieldName = fieldName, notification = InvalidNicknameLengthDomainNotification()))
            false
        }
        else -> true
    }
}