package org.amoseman.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public ImageLoader() {
    }

    /**
     *
     */
    public DataBuffer loadImage(String filename) {
        try {
            File file = new File(filename);
            BufferedImage bufferedImage = ImageIO.read(file);
            return bufferedImage.getData().getDataBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
