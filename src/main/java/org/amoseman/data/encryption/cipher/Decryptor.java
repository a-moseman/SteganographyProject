package org.amoseman.data.encryption.cipher;

public interface Decryptor {
    /**
     * Decrypt the provided data.
     * @param data the data to decrypt.
     * @param salt the password salt to use.
     * @param password the password to use.
     * @return the decrypted data.
     */
    byte[] decrypt(byte[] data, byte[] salt, String password);
}
