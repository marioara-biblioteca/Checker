package checker.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

public class Utils {
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
    public static String get_SHA_256_SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
         //   String salt=getSalt();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
          //  md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    public static int HALF_DAY = 12*60*60*1000; // 12h
    public static int ONE_DAY = 24*60*60*1000; // 24h

    public static String get_JWT(String subject, int offset, String reqURL, List<String> roles){
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + offset))
                .withIssuer(reqURL)
                .withClaim("role", roles)
                .sign(algorithm);
    }
}
