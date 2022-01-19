package dev.cschirmer.ddd.commons.application.translation

import dev.cschirmer.ddd.kernel.application.configuration.Language
import dev.cschirmer.ddd.kernel.application.translation.TranslateModule

object CommonPTBRTranslationModule : TranslateModule {
    override val language: Language = Language.PT_BR
    override val translations: Map<String, String> = mapOf(
        /** Common.domain */
        "InvalidCNPJDomainNotification" to "CNPJ não é válido.",
        "InvalidCPFDomainNotification" to "CPF não é válido.",
        "InvalidRouteTypeDomainNotification" to "Tipo de rota não é válida.",
        "InvalidVisibilityDomainNotification" to "Visibilidade não é válida.",
        "InvalidEmailDomainNotification" to "Email não é válido.",
        "InvalidPasswordDomainNotification" to "Senha não atende aos critérios de segurança.",
        "InvalidFullNameLengthDomainNotification" to "Nome deve conter pelo menos 6 letras.",
        "InvalidFullNameWordsDomainNotification" to "Nome deve conter pelo menos duas palavras.",
        "InvalidNicknameLengthDomainNotification" to "Apelido deve conter pelo menos 4 letras.",
        /** Common.domain.enum */
        "RouteType.UNKNOWN" to "Desconhecido",
        "RouteType.HTTP" to "Http",
        "RouteType.GRAPHQL" to "GRAPHQL",
        "Visibility.UNKNOWN" to "Desconhecido",
        "Visibility.PRIVATE" to "Privado",
        "Visibility.PUBLIC" to "Público"
    )
}