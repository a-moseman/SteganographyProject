package org.amoseman.data;

import org.amoseman.steganography.data.DataCompressor;
import org.amoseman.steganography.data.DataDecompressor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class DataCompressorDecompressorTest {
    private static String testdata;

    @BeforeAll
    public static void beforeAll() {
        try {
            testdata = Files.readString(Paths.get("src/test/resources", "testdata.txt"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() {
        // Compress the test data
        DataCompressor dataCompressor = new DataCompressor();
        byte[] compressionResult;
        compressionResult = dataCompressor.compress(testdata.getBytes(StandardCharsets.UTF_8));
        assertNotEquals(0, compressionResult.length);
        assertTrue(compressionResult.length < testdata.getBytes(StandardCharsets.UTF_8).length);


        // Decompress the compressed data to make sure it matches the original testdata
        DataDecompressor dataDecompressor = new DataDecompressor();
        byte[] decompressionResult;
        decompressionResult = dataDecompressor.decompress(compressionResult);

        assertNotEquals(0, decompressionResult.length);
        assertEquals(testdata, new String(decompressionResult));
    }
}