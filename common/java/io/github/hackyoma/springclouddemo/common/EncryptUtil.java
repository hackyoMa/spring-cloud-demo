package io.github.hackyoma.springclouddemo.common;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * EncryptUtil
 *
 * @author hackyo
 * @version 2022/1/20
 */
public final class EncryptUtil {

    public final static String KEY = "5a/GbMgxThP2agiTerFZki8OPS5QC9UJojWRLDE49Kk=";
    public final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    public final static SecretKey SECRET_KEY = new SecretKeySpec(Base64.getDecoder().decode(KEY), EncryptUtil.SIGNATURE_ALGORITHM.getJcaName());

    public static String hmacSha256(String original) {
        try {
            Mac mac = Mac.getInstance(EncryptUtil.SIGNATURE_ALGORITHM.getJcaName());
            mac.init(SECRET_KEY);
            return Base64.getEncoder().encodeToString(mac.doFinal(original.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

}
