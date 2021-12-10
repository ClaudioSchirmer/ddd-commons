package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.utils.kotlin.security.encryptHash
import dev.cschirmer.ddd.kernel.domain.notifications.NotificationContext
import dev.cschirmer.ddd.kernel.domain.notifications.NotificationMessage
import dev.cschirmer.ddd.kernel.domain.valueobjects.ScalarValueObject

data class Password(
    override var value: String
) : ScalarValueObject<String>() {
    private var isEncrypted = false

    companion object {
        fun fromEncryptedLocation(value: String): Password = Password(value).apply { isEncrypted = true }
    }

    val hashValue: String
        get() = when {
            isEncrypted -> value
            isValid() -> value
            else -> ""
        }

    override fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean {
        if (isEncrypted) {
            return true
        }
        "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$".toRegex(RegexOption.IGNORE_CASE).run {
            return if (this.matches(value)) {
                value = value.encryptHash()
                isEncrypted = true
                true
            } else {
                notificationContext?.addNotification(
                    NotificationMessage(
                        fieldName = fieldName,
                        notification = InvalidPasswordDomainNotification()
                    )
                )
                false
            }
        }
    }
}