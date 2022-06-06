package ie.shanequaid.scc

import ie.shanequaid.scc.spring.properties.KeycloakClientProperties
import ie.shanequaid.scc.spring.properties.SCCBucketProperties
import ie.shanequaid.scc.spring.properties.SCCEmailProperties
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@EnableConfigurationProperties(KeycloakClientProperties::class, SCCBucketProperties::class, SCCEmailProperties::class)
class SccApplicationTests {

	@Test
	fun contextLoads() {
	}

}
