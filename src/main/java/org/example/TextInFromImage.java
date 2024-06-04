package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextInFromImage {
    private final Converter converter = new Converter();

    public void embedTextInImage(String imagePath, String text, String outputPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        String binaryText = converter.textToBits(text);
        String binaryLength = String.format("%8s", Integer.toBinaryString(binaryText.length())).replace(' ', '0');

        binaryText = binaryLength + binaryText;

        System.out.println("Binary length: " + binaryLength);
        System.out.println("Binary text: " + binaryText);

        int textIndex = 0;
        outerLoop:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                int[] colors = {
                        (pixel >> 16) & 0xff, //red
                        (pixel >> 8) & 0xff, //green
                        pixel & 0xff        //blue
                };

                for (int i = 0; i < 3; i++) {
                    if (textIndex < binaryText.length()) {
//                        System.out.printf("Embedding bit %c at pixel (%d, %d) color %s\n", binaryText.charAt(textIndex),x, y, i == 0 ? "red" : i == 1 ? "green" : "blue");
                        colors[i] = (colors[i] & ~1) | (binaryText.charAt(textIndex) - '0');
                        textIndex++;
                    } else {
                        break outerLoop; //
                    }
                }

                int newPixel = (colors[0] << 16) | (colors[1] << 8) | colors[2];
                image.setRGB(x, y, newPixel);
            }
        }

        ImageIO.write(image, "png", new File(outputPath));
        System.out.println("Text embedded in " + outputPath);
        System.out.println("Binary text: " + binaryText);
    }

    public String extractTextFromImage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        StringBuilder binaryText = new StringBuilder();

        outerLoop:
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                int[] colors = {
                        (pixel >> 16) & 0xff, //red
                        (pixel >> 8) & 0xff, //green
                        pixel & 0xff        //blue
                };

                for (int i = 0; i < 3; i++) {
                    binaryText.append(colors[i] & 1);
                    if (binaryText.length() >= 16 && binaryText.substring(binaryText.length() - 16).equals("1111111111111110")) {

                        // Delete end point
                        binaryText.setLength(binaryText.length() - 16);
                        break outerLoop;
                    }
                }
            }
        }

        System.out.println("Extracted binary text: " + binaryText);
        String binaryTextWithoutLength = binaryText.substring(8);
        return converter.bitsToText(binaryTextWithoutLength);
    }
}
