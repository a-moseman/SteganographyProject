package org.amoseman.stream;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface Encoder {
    /**
     * Read the data from file.
     * @param file the file.
     * @return this.
     */
    Encoder read(File file);

    /**
     * Compress the data.
     * @param level the level of compression.
     * @return this.
     */
    Encoder compress(int level);

    /**
     * Encrypt the data.
     * @param password the password to use.
     * @return this.
     */
    Encoder encrypt(String password);

    /**
     * Encode the data using steganography.
     * @param targets the images to encode into.
     * @return the modified images with the encoded data.
     */
    List<BufferedImage> encode(List<BufferedImage> targets);
}
