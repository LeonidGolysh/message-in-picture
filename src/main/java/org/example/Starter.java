package org.example;

import org.example.steganography.EmbedText;
import org.example.steganography.ExtractText;

import java.io.IOException;

public class Starter {
    private final EmbedText embedText = new EmbedText();
    private final ExtractText extractText = new ExtractText();

    public void start() {

        String inputImagePath = "src/main/resources/input.png";
        String outputImagePath = "src/main/resources/output.png";
        String message = "Hello world | Привіт світ";

        try {
            embedText.embedTextInImage(inputImagePath, message, outputImagePath);
            String extractedText = extractText.extractTextFromImage(outputImagePath);
            System.out.println("Text in image: " + extractedText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
