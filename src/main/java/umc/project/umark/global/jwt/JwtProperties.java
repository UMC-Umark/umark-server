package umc.project.umark.global.jwt;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "auth.jwt")
@Slf4j
public class JwtProperties {

    private String secretKey;
    private long accessExp;
    private long refreshExp;

    @PostConstruct
    public void init() {
        log.info("JwtProperties loaded: secretKey={}, accessExp={}, refreshExp={}", secretKey, accessExp, refreshExp);
    }
}
