package org.amoseman.steganography.data.steganography;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Interface for encoding data into images using steganography.
 */
public interface SteganographyDecoder {
    /**
     * Decode the data from the images using steganography.
     * @param source the source images.
     * @return the decoded data.
     */
    byte[] decode(List<BufferedImage> source);
}
