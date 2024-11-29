package org.amoseman.data.encryption.generator;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public final class SecretKeyGenerator {
    private static final String HASHING_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 65536;
    private final SecretKeyFactory factory;

    public SecretKeyGenerator() {
        try {
            this.factory = SecretKeyFactory.getInstance(HASHING_ALGORITHM);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public SecretKeySpec generate(String password, byte[] salt, int keyLength, String encryptionAlgorithm) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, keyLength);
            SecretKey key = factory.generateSecret(spec);
            return new SecretKeySpec(key.getEncoded(), encryptionAlgorithm);
        }
        catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
