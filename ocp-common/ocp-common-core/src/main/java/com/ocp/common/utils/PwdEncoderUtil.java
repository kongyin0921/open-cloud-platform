package com.ocp.common.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * PasswordEncoder实现工具类
 * @author kong
 * @date 2021/08/07 19:15
 * blog: http://blog.kongyin.ltd
 */
public class PwdEncoderUtil {


    public static PasswordEncoder getDelegatingPasswordEncoder(String encodingId) {
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(PwdEncoderType.BCRYPT, new BCryptPasswordEncoder());
        encoders.put(PwdEncoderType.LDAP, new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
        encoders.put(PwdEncoderType.MD4, new org.springframework.security.crypto.password.Md4PasswordEncoder());
        encoders.put(PwdEncoderType.MD5, new org.springframework.security.crypto.password.MessageDigestPasswordEncoder(PwdEncoderType.MD5));
        encoders.put(PwdEncoderType.NOOP, org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
        encoders.put(PwdEncoderType.PBKDF2, new Pbkdf2PasswordEncoder());
        encoders.put(PwdEncoderType.SCRYPT, new SCryptPasswordEncoder());
        encoders.put(PwdEncoderType.SHA_1, new org.springframework.security.crypto.password.MessageDigestPasswordEncoder(PwdEncoderType.SHA_1));
        encoders.put(PwdEncoderType.SHA_256, new org.springframework.security.crypto.password.MessageDigestPasswordEncoder(PwdEncoderType.SHA_256));
        encoders.put(PwdEncoderType.SHA256, new org.springframework.security.crypto.password.StandardPasswordEncoder());
        encoders.put(PwdEncoderType.ARGON2, new Argon2PasswordEncoder());

        Assert.isTrue(encoders.containsKey(encodingId), encodingId + " is not found in idToPasswordEncoder");

        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(encoders.get(encodingId));
        return delegatingPasswordEncoder;
    }


    public interface PwdEncoderType{

        String BCRYPT = "bcrypt";

        @Deprecated
        String LDAP = "ldap";

        @Deprecated
        String MD4 = "MD4";

        @Deprecated
        String MD5 = "MD5";

        @Deprecated
        String NOOP = "noop";

        String PBKDF2 = "pbkdf2";

        String SCRYPT = "scrypt";

        @Deprecated
        String SHA_1 = "SHA-1";

        @Deprecated
        String SHA_256 = "SHA-256";

        @Deprecated
        String SHA256 = "sha256";

        String ARGON2 = "argon2";
    }
}
