package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.DomainNotification
import br.dev.schirmer.ddd.kernel.domain.valueobjects.EnumValueObject

enum class RouteType(override val value: Int) : EnumValueObject<Int> {
    UNKNOWN(0),
    HTTP(1),
    GRAPHQL(2);

    override val unknownEnumNotification: DomainNotification by lazy { InvalidRouteTypeDomainNotification() }
}