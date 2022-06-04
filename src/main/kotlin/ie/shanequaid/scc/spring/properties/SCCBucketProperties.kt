package ie.shanequaid.scc.spring.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("scc.bucket")
class SCCBucketProperties {
    lateinit var name: String
    lateinit var url: String
    lateinit var region: String
    lateinit var accessKey: String
    lateinit var secretKey: String
}

