package org.amoseman.data.encryption.generator;

import java.security.SecureRandom;

/**
 * A class for generating salt for cryptography operations.
 */
public final class SaltGenerator {
    private final SecureRandom random;

    /**
     * Instantiate the salt generator.
     * @param random the source of random to use.
     */
    public SaltGenerator(SecureRandom random) {
        this.random = random;
    }

    /**
     * Generate salt.
     * @param size the size of the salt.
     * @return the salt.
     */
    public byte[] generate(int size) {
        byte[] salt = new byte[size];
        random.nextBytes(salt);
        return salt;
    }
}
