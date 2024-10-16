package org.amoseman.stream;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public interface Decoder {
    /**
     * Decode the data using steganography.
     * @param sources the images to decode from.
     * @return this.
     */
    Encoder decode(List<BufferedImage> sources);

    /**
     * Decrypt the data.
     * @param password the password to use.
     * @return this.
     */
    Encoder decrypt(String password);

    /**
     * Decompress the data.
     * @param level the level of compression.
     * @return this.
     */
    Encoder decompress(int level);

    /**
     * Write the data to the file.
     * @param file the file to write to.
     */
    void write(File file);
}
