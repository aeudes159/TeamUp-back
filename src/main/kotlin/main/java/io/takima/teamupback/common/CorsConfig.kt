package main.java.io.takima.teamupback.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    @Profile("!prod")  // Active pour tous les profils sauf "prod"
    fun corsFilter(): CorsFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOriginPattern("*")  // Permet tous les origins en local
        config.allowedOrigins = listOf("https://teamup.jo-pouradier.fr")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        
        return CorsFilter(source)
    }
}
