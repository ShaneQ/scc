package ie.shanequaid.scc.spring.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("keycloak-client")
class KeycloakClientProperties {
    lateinit var serverUrl: String
    lateinit var username: String
    lateinit var password: String
    lateinit var clientId: String
    lateinit var roleUserId: String
    lateinit var roleActiveMemberId: String

}

@Configuration
@ConfigurationProperties("scc.bucket")
class SCCBucketProperties {
    lateinit var name: String
    lateinit var url: String
    lateinit var region: String
    lateinit var accessKey: String
    lateinit var secretKey: String
}

@Configuration
@ConfigurationProperties("scc.email")
class SCCEmailProperties {
    lateinit var senderEmail: String
    lateinit var adminEmail: String
    lateinit var accessKey: String
    lateinit var secretKey: String

}
