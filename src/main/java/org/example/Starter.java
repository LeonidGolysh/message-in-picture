package org.example;

import java.io.IOException;

public class Starter {
    private final TextInFromImage text = new TextInFromImage();
    public void start() {

        String inputImagePath = "src/main/resources/input.png";
        String outputImagePath = "src/main/resources/output.png";
        String message = "Привіт світ";

        try {
            text.embedTextInImage(inputImagePath, message, outputImagePath);
            String extractedText = text.extractTextFromImage(outputImagePath);
            System.out.println("Text in image: " + extractedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
