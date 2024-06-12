package org.example.steganography;

import org.example.convert.Converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EmbedText {
    private final Converter converter = new Converter();
    private final ImageUtils imageUtils = new ImageUtils();

    public void embedTextInImage(String imagePath, String text, String outputPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        String binaryText = prepareBinaryText(text);

        System.out.println("Binary text: " + binaryText);

        embedBinaryTextInImage(image, binaryText);

        ImageIO.write(image, "png", new File(outputPath));
        System.out.println("Text embedded in " + outputPath);
        System.out.println("Binary text: " + binaryText);
    }

    private String prepareBinaryText(String text) {
        String binaryText = converter.textToBits(text);
        String binaryLength = String.format("%8s", Integer.toBinaryString(text.length())).replace(' ', '0');

        binaryText = binaryLength + binaryText;

        System.out.println("Binary length: " + binaryLength);
        return binaryText;
    }

    private void embedBinaryTextInImage(BufferedImage image, String binaryText) {
        int textIndex = 0;
        boolean isTextEmbedded = false;

        for (int y = 0; y < image.getHeight() && !isTextEmbedded; y++) {
            for (int x = 0; x < image.getWidth() && !isTextEmbedded; x++) {
                int pixel = image.getRGB(x, y);
                int[] colors = imageUtils.extractColors(pixel);

                // Iterate through each color channel (red, green, blue)
                for (int i = 0; i < 3; i++) {
                    if (textIndex < binaryText.length()) {
                        int originalColor = colors[i];  //Used for checking
                        int bitToEmbed = binaryText.charAt(textIndex) - '0';

                        //Embed binary data into the least significant bit (LSB) of the current color chanel
                        colors[i] = (colors[i] & ~1) | bitToEmbed;
                        textIndex++;
//                        System.out.printf("Embedding bit %d at pixel (%d, %d) color %s: %02X -> %02X\n",
//                                bitToEmbed, x, y, (i == 0 ? "red" : i == 1 ? "green" : "blue"),
//                                originalColor, colors[i]);
                    } else {
                        isTextEmbedded = true;
                        break;
                    }
                }
                // Combine modified color channels into a new pixel value
                int newPixel = (colors[0] << 16) | (colors[1] << 8) | colors[2];
                image.setRGB(x, y, newPixel);
            }
        }
    }
}