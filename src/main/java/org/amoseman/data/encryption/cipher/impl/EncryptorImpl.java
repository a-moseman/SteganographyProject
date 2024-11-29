package org.amoseman.data.encryption.cipher.impl;

import org.amoseman.data.encryption.cipher.CipherBase;
import org.amoseman.data.encryption.cipher.Encryptor;
import org.amoseman.data.encryption.generator.SaltGenerator;
import org.amoseman.data.encryption.generator.SecretKeyGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * A class for encrypting data using AES 256.
 */
public final class EncryptorImpl extends CipherBase implements Encryptor {
    private final SaltGenerator saltGenerator;

    /**
     * Instantiate a data encryptor.
     * @param saltGenerator the salt generator to use.
     * @param secretKeyGenerator the secret key generator to use.
     */
    public EncryptorImpl(SaltGenerator saltGenerator, SecretKeyGenerator secretKeyGenerator) {
        super(secretKeyGenerator);
        this.saltGenerator = saltGenerator;
    }

    /**
     * Concatenate two byte arrays together.
     * @param a the first byte array.
     * @param b the second byte array.
     * @return a concatenated with b.
     */
    private byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * Encrypt the data.
     * @param data the data to encrypt.
     * @param salt the password salt to use.
     * @param password the password to use.
     * @return the encrypted data prepended with the initialization vector of length 16.
     */
    @Override
    public byte[] encrypt(byte[] data, byte[] salt, String password) {
        byte[] iv = saltGenerator.generate(IV_LENGTH);
        initialize(Cipher.ENCRYPT_MODE, iv, salt, password);
        byte[] encrypted;
        try {
            encrypted = cipher.doFinal(data);
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return concat(iv, encrypted);
    }
}
