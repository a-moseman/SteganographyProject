package org.amoseman.steganography.data.compression;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

/**
 * DataCompressor compresses data using the Deflate algorithm.
 */
public class DataCompressor {
    public DataCompressor() {
    }

    /**
     * Compress the byte array using level 9 compression
     *
     * @param inputData Data to be compressed
     * @return The compressed data as a byte array
     */
    public byte[] compress(final byte[] inputData) {
        Deflater deflater = new Deflater();
        deflater.setInput(inputData);
        deflater.setLevel(9);
        deflater.finish();

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (!deflater.finished()) {
            int len = deflater.deflate(buffer);
            baos.write(buffer, 0, len);
        }
        return baos.toByteArray();
    }
}
