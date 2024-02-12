package umc.project.umark.global.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {

    private final String secretKey;
    private final long accessExp;
    private final long refreshExp;

    public JwtProperties(String secretKey, long accessExp, long refreshExp) {
        this.secretKey = secretKey;
        this.accessExp = accessExp;
        this.refreshExp = refreshExp;
    }
}
