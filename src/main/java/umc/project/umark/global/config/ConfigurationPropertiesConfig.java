package umc.project.umark.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import umc.project.umark.global.jwt.JwtProperties;

@EnableConfigurationProperties({
        JwtProperties.class,
})
@Configuration
public class ConfigurationPropertiesConfig {}
