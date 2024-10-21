package org.amoseman.steganography.data.compression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * DataDecompressor decompresses data using the Deflate algorithm.
 */
public class DataDecompressor {
    public DataDecompressor() {
    }

    /**
     * Decompress the ZLib compressed data
     *
     * @param data The compressed data as a byte array
     * @return Decompressed data as a byte array
     */
    public byte[] decompress(final byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (!inflater.finished()) {
            int len = 0;
            try {
                len = inflater.inflate(buffer);
            }
            catch (DataFormatException e) {
                throw new RuntimeException(e);
            }
            baos.write(buffer, 0, len);
        }

        return baos.toByteArray();
    }
}
