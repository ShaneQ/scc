package ie.shanequaid.scc.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode
import java.nio.charset.StandardCharsets

@Configuration
class ThymeleafTemplateConfig {
    @Bean
    fun springTemplateEngine(): SpringTemplateEngine? {
        val templateEngine = SpringTemplateEngine()
        templateEngine.addTemplateResolver(htmlTemplateResolver())
        return templateEngine
    }

    @Bean
    fun htmlTemplateResolver(): SpringResourceTemplateResolver? {
        val emailTemplateResolver = SpringResourceTemplateResolver()
        emailTemplateResolver.setPrefix("classpath:/templates/")
        emailTemplateResolver.setSuffix(".html")
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML)
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name())
        return emailTemplateResolver
    }
}
