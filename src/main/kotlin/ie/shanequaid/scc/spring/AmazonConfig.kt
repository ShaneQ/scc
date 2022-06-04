package ie.shanequaid.scc.spring

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import ie.shanequaid.scc.spring.properties.SCCBucketProperties
import ie.shanequaid.scc.spring.properties.SCCEmailProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonConfig @Autowired constructor(
    @Qualifier("SCCBucketProperties") val s3Properties: SCCBucketProperties,
    @Qualifier("SCCEmailProperties") val emailProperties: SCCEmailProperties
) {
    @Bean
    fun s3(): AmazonS3 {
        val awsCredentials: AWSCredentials =
            BasicAWSCredentials(s3Properties.accessKey, s3Properties.secretKey)
        return AmazonS3ClientBuilder
            .standard()
            .withRegion(s3Properties.region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build()
    }

    @Bean
    fun email(): AmazonSimpleEmailService {
        val awsCredentials: AWSCredentials =
            BasicAWSCredentials(emailProperties.accessKey, emailProperties.secretKey)
        return AmazonSimpleEmailServiceClientBuilder.standard()
            .withRegion(Regions.EU_WEST_1).withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build()
    }
}
