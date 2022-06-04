package ie.shanequaid.scc.spring

import ie.shanequaid.scc.spring.properties.KeycloakClientProperties
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class KeycloakConfig(@Qualifier("keycloakClientProperties") val properties: KeycloakClientProperties) {
    @Bean
    fun keycloakClient(): Keycloak {
        return KeycloakBuilder.builder()
            .serverUrl(properties.serverUrl)
            .grantType(OAuth2Constants.PASSWORD)
            .realm("master")
            .clientId("admin-cli")
            .username(properties.username)
            .password(properties.password)
            .resteasyClient(
                ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            ).build()
    }

    /**
     * Don't use keycloak.json. Instead, use application.yml properties.
     */
    @Bean
    fun keycloakConfigResolver(): KeycloakSpringBootConfigResolver {
        return KeycloakSpringBootConfigResolver()
    }
}