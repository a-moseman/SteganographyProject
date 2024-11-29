package org.amoseman.data.encryption.cipher.impl;

import org.amoseman.data.encryption.cipher.CipherBase;
import org.amoseman.data.encryption.cipher.Decryptor;
import org.amoseman.data.encryption.generator.SecretKeyGenerator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * A class for decrypting data using AES 256.
 */
public final class DecryptorImpl extends CipherBase implements Decryptor {
    /**
     * Instantiate a data decryptor.
     * @param secretKeyGenerator the secret key generator to use.
     */
    public DecryptorImpl(SecretKeyGenerator secretKeyGenerator) {
        super(secretKeyGenerator);
    }

    /**
     * Extract a sub array from the provided byte array.
     * @param data the byte array.
     * @param size the size of the sub array.
     * @param offset the offset of the sub array in the byte array.
     * @return the sub array.
     */
    private byte[] extract(byte[] data, int size, int offset) {
        byte[] extracted = new byte[size];
        System.arraycopy(data, offset, extracted, 0, extracted.length);
        return extracted;
    }

    /**
     * Decrypt the provided encrypted data.
     * @param data the encrypted data to decrypt.
     * @param salt the salt to use for the password.
     * @param password the password to use.
     * @return the decrypted data.
     */
    public byte[] decrypt(byte[] data, byte[] salt, String password) {
        byte[] iv = extract(data, IV_LENGTH, 0);
        byte[] encrypted = extract(data, data.length - IV_LENGTH, IV_LENGTH);
        initialize(Cipher.DECRYPT_MODE, iv, salt, password);
        try {
            return cipher.doFinal(encrypted);
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
