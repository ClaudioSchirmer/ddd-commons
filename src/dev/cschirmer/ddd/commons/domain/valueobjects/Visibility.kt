package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.DomainNotification
import dev.cschirmer.ddd.kernel.domain.valueobjects.EnumValueObject

enum class Visibility(override val value: Int) : EnumValueObject<Int> {
	UNKNOWN(0),
	PRIVATE(1),
	PUBLIC(2);

	override val unknownEnumNotification: DomainNotification by lazy { InvalidVisibilityDomainNotification() }
}