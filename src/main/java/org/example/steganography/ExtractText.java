package org.example.steganography;

import org.example.convert.Converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExtractText {
    private final Converter converter = new Converter();
    private final ImageUtils imageUtils = new ImageUtils();

    public String extractTextFromImage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        StringBuilder binaryText = new StringBuilder();

        extractBinaryTextFromImage(image, binaryText);

        System.out.println("Extracted binary text: " + binaryText);
        String binaryTextWithoutLength = binaryText.substring(8);
        return converter.bitsToText(binaryTextWithoutLength);
    }

    private void extractBinaryTextFromImage(BufferedImage image, StringBuilder binaryText) {
        boolean endPointFound = false;

        for (int y = 0; y < image.getHeight() && !endPointFound; y++) {
            for (int x = 0; x < image.getWidth() && !endPointFound; x++) {
                int pixel = image.getRGB(x, y);

                int[] colors = imageUtils.extractColors(pixel);

                for (int i = 0; i < 3; i++) {
                    binaryText.append(colors[i] & 1);

                    if (binaryText.length() >= 16 && binaryText.substring(binaryText.length() - 16).equals("1111111111111110")) {
                        // Delete end point
                        binaryText.setLength(binaryText.length() - 16);
                        endPointFound = true;
                        break;
                    }
                }
            }
        }
    }
}
