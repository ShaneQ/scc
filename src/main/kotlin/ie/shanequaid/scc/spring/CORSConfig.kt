package ie.shanequaid.scc.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.Arrays

@Configuration
class CORSConfig {
    /**
     * Setup CORS
     * @return
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = Arrays.asList(
            "http://localhost:8089",
            "http://localhost:8089/",
            "http://localhost:8090",
            "https://www.2ndclosetclub.com",
            "https://2ndclosetclub.com"
        )
        config.allowedMethods = Arrays.asList(CorsConfiguration.ALL)
        config.allowedHeaders = Arrays.asList(CorsConfiguration.ALL)
        config.allowCredentials = true
        source.registerCorsConfiguration("/**", config)
        return source
    }
}