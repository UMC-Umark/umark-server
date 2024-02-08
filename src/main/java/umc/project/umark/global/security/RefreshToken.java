package umc.project.umark.global.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String key;
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }

}
