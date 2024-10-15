import org.amoseman.image.ImageLoader;
import org.junit.jupiter.api.Test;

import java.awt.image.DataBuffer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageLoaderTest {
    private static final String testFile000 = "src/test/resources/test-image-000.bmp";

    @Test
    void testImageProcessorLoad() {
        ImageLoader imageLoader = new ImageLoader();
        DataBuffer data = imageLoader.loadImage(testFile000);
        assertEquals(data.getSize(), 6220800);
    }
}