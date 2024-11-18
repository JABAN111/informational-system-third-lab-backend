package is.fistlab.exceptions.auth;

public class JwtTokenExpired extends RuntimeException {
    public JwtTokenExpired(String message) {
        super(message);
    }
}
