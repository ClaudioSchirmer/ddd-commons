package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.DomainNotification
import dev.cschirmer.ddd.kernel.domain.valueobjects.EnumValueObject

enum class RouteType(override val value: Int) : EnumValueObject<Int> {
    UNKNOWN(0),
    HTTP(1),
    GRAPHQL(2);

    override val unknownEnumNotification: DomainNotification by lazy { InvalidRouteTypeDomainNotification() }
}