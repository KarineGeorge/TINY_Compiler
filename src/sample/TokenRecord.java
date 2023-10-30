package sample;

import java.io.File;

public class TokenRecord {
    private TokenType tokenType;
    private String tokenString;

    public TokenRecord(TokenType tokenType, String tokenString) {
        this.tokenType = tokenType;
        this.tokenString = tokenString;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getTokenString() {
        return tokenString;
    }

}
