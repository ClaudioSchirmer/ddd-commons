package dev.cschirmer.ddd.commons.domain.valueobjects

import dev.cschirmer.ddd.kernel.domain.notifications.DomainNotification

/** CNPJ*/
class InvalidCNPJDomainNotification : DomainNotification

/** CPF*/
class InvalidCPFDomainNotification : DomainNotification

/** RouteType*/
class InvalidRouteTypeDomainNotification: DomainNotification

/** Visibility*/
class InvalidVisibilityDomainNotification: DomainNotification

/** Email*/
class InvalidEmailDomainNotification: DomainNotification

/** Password */
class InvalidPasswordDomainNotification: DomainNotification

/** FullName */
class InvalidFullNameLengthDomainNotification: DomainNotification
class InvalidFullNameWordsDomainNotification: DomainNotification

/** Nickname */
class InvalidNicknameLengthDomainNotification: DomainNotification