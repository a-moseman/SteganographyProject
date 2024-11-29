package org.amoseman.data.encryption.cipher.impl;

import org.amoseman.data.encryption.cipher.CipherBase;
import org.amoseman.data.encryption.cipher.Decryptor;
import org.amoseman.data.encryption.cipher.Encryptor;
import org.amoseman.data.encryption.generator.SaltGenerator;
import org.amoseman.data.encryption.generator.SecretKeyGenerator;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
class CipherTest {

    @Test
    void encryptDecrypt() {
        SecureRandom random = new SecureRandom();
        SaltGenerator saltGenerator = new SaltGenerator(random);
        SecretKeyGenerator secretKeyGenerator = new SecretKeyGenerator();
        Encryptor encryptor = new EncryptorImpl(saltGenerator, secretKeyGenerator);
        Decryptor decryptor = new DecryptorImpl(secretKeyGenerator);

        String password = "password";
        byte[] salt = saltGenerator.generate(CipherBase.SALT_LENGTH);
        byte[] data = "hello world".getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = encryptor.encrypt(data, salt, password);
        byte[] decrypted = decryptor.decrypt(encrypted, salt, password);
        assertArrayEquals(data, decrypted);
    }
}