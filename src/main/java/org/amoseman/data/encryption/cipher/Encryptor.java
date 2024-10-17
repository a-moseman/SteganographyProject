package org.amoseman.data.encryption.cipher;

public interface Encryptor {
    /**
     * Encrypt the provided data.
     * @param data the data to encrypt.
     * @param salt the password salt to use.
     * @param password the password to use.
     * @return the encrypted data.
     */
    byte[] encrypt(byte[] data, byte[] salt, String password);
}
