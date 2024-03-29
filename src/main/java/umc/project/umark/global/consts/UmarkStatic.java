package umc.project.umark.global.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UmarkStatic {
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String NULL_STRING = "null";
    public static final String TOKEN_ROLE = "role";
    public static final String TOKEN_TYPE = "auth";
    public static final String TOKEN_ISSUER = "Umark";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";

    public static final int MILLI_TO_SECOND = 1000;
}