package br.dev.schirmer.ddd.commons.domain.valueobjects

import br.dev.schirmer.ddd.kernel.domain.notifications.DomainNotification

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