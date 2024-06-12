package org.example.steganography;

public class ImageUtils {
    public int[] extractColors(int pixel) {
        return new int[] {
                (pixel >> 16) & 0xff, //red
                (pixel >> 8) & 0xff, //green
                pixel & 0xff        // blue
        };
    }
}
