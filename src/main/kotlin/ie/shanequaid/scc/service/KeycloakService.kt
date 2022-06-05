package ie.shanequaid.scc.service

import ie.shanequaid.scc.persistence.model.dto.KeycloakUserInfo
import ie.shanequaid.scc.persistence.model.dto.Role
import ie.shanequaid.scc.spring.properties.KeycloakClientProperties
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.Keycloak
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.AccessTokenResponse
import org.keycloak.representations.idm.RoleRepresentation
import org.springframework.stereotype.Component
import java.util.List
import java.util.UUID

@Component
class KeycloakService(
    private val keycloakClient: Keycloak,
    private val keycloakClientProperties: KeycloakClientProperties
) {

    companion object {
        private const val SECOND_CLOSET_CLUB = "secondClosetClub"
    }

    fun addRole(userId: UUID, role: Role) {
        val roleName = role.name.lowercase()
        val hasRole = keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
            .clientLevel(keycloakClientProperties.clientId).listAll().stream()
            .anyMatch { roleRep: RoleRepresentation ->
                roleRep.name.equals(roleName, ignoreCase = true)
            }
        if (!hasRole) {
/*
            log.info("Added role {} to user {}", roleName, userId)
*/
                keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
                    .clientLevel(keycloakClientProperties.clientId).add(listOf(getKeycloakRole(role)))

        }
    }

    private fun getKeycloakRole(role: Role): RoleRepresentation {
        val roleName = role.name.lowercase()
        val roleRepresentation = RoleRepresentation()
        when (role) {
            Role.SCC_ACTIVE_MEMBERSHIP -> roleRepresentation.id =
                keycloakClientProperties.roleActiveMemberId
            Role.SCC_USER_ROLE -> roleRepresentation.id =
                keycloakClientProperties.roleUserId
        }
        roleRepresentation.name = roleName
        roleRepresentation.isComposite = false
        roleRepresentation.clientRole = true
        roleRepresentation.containerId = keycloakClientProperties.clientId
        return roleRepresentation
    }

    fun removeRole(userId: UUID, role: Role) {
        val roleName = role.name.lowercase()
        val hasRole = keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
            .clientLevel(keycloakClientProperties.clientId).listAll().stream()
            .anyMatch { roleRep: RoleRepresentation ->
                roleRep.name.equals(roleName, ignoreCase = true)
            }
        if (hasRole) {
/*
            log.info("Removed role {} to user {}", roleName, userId)
*/
            keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()].roles()
                .clientLevel(keycloakClientProperties.clientId).remove(List.of(getKeycloakRole(role)))
        }
    }

    fun keycloakClient(): Keycloak {
        return KeycloakBuilder.builder()
            .serverUrl("http://localhost:8083/auth")
            .grantType(OAuth2Constants.PASSWORD)
            .realm("master")
            .clientId("admin-cli")
            .username("masterapiuser")
            .password("masterapiuser")
            .resteasyClient(
                ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            ).build()
    }

    fun newKeycloakLogin(): AccessTokenResponse {
        val keycloak = KeycloakBuilder.builder()
            .serverUrl("http://localhost:8083/auth")
            .grantType(OAuth2Constants.PASSWORD)
            .realm("master")
            .clientId("admin-cli")
            .username("masterapiuser")
            .password("masterapiuser")
            .resteasyClient(
                ResteasyClientBuilder()
                    .connectionPoolSize(10).build()
            ).build()
        return keycloak.tokenManager().accessToken
    }

    fun getUserInfo(userId: UUID): KeycloakUserInfo {
        val userRepresentation = keycloakClient.realm(SECOND_CLOSET_CLUB).users()[userId.toString()]
            .toRepresentation()
        return KeycloakUserInfo(
            userRepresentation.username,
            userRepresentation.firstName,
            userRepresentation.lastName,
            userRepresentation.email
        )
    }
}
