package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.utils.kotlin.security.string.encryptHash
import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationContext
import br.dev.schirmer.ddd.kernel.domain.notifications.NotificationMessage
import br.dev.schirmer.ddd.kernel.domain.valueobjects.ScalarValueObject

data class Password(
    override var value: String
) : ScalarValueObject<String>() {
    private var isEncrypted = false

    companion object {
        fun fromEncryptedLocation(value: String): Password = Password(value).apply { isEncrypted = true }
    }

    suspend fun getHashValue() = when {
        isEncrypted -> value
        isValid() -> value
        else -> ""
    }

    override suspend fun isValid(fieldName: String?, notificationContext: NotificationContext?): Boolean {
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