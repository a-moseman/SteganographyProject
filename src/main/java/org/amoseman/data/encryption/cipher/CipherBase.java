package org.amoseman.data.encryption.cipher;

import org.amoseman.data.encryption.generator.SecretKeyGenerator;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * A base AES 256 cipher class.
 */
public class CipherBase {
    public static final int IV_LENGTH = 16;
    public static final int SALT_LENGTH = 16;
    public static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    protected final Cipher cipher;
    protected final SecretKeyGenerator secretKeyGenerator;

    /**
     * Instantiate the base cipher.
     * @param secretKeyGenerator the secret key generator to use.
     */
    public CipherBase(SecretKeyGenerator secretKeyGenerator) {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        }
        catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        this.secretKeyGenerator = secretKeyGenerator;
    }

    /**
     * Initialize the cipher.
     * @param mode the mode to use.
     * @param iv the initialization vector to use.
     * @param salt the password salt to use.
     * @param password the password to use.
     */
    protected void initialize(int mode, byte[] iv, byte[] salt, String password) {
        SecretKeySpec keySpec = secretKeyGenerator.generate(password, salt, KEY_LENGTH, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        try {
            cipher.init(mode, keySpec, ivSpec);
        }
        catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
}
