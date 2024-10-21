package org.amoseman.steganography.data.steganography;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Interface for encoding data into images using steganography.
 */
public interface SteganographyEncoder {
    /**
     * Encode the data into the images using steganography.
     * @param source the source images.
     * @param data the data.
     * @return the modified images.
     */
    List<BufferedImage> encode(List<BufferedImage> source, byte[] data);
}
