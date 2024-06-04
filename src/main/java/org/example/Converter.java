package org.example;

public class Converter {

    public String textToBits(String text) {
        StringBuilder bits = new StringBuilder();
        for (char c : text.toCharArray()) {
            bits.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        bits.append("1111111111111110"); // End point
        return bits.toString();
    }

    public String bitsToText(String bits) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i + 8 <= bits.length(); i += 8) {

            String byteStr = bits.substring(i, i + 8);
            System.out.println("Byte string: " + byteStr);

            if(byteStr.equals("1111111111111110")) {
                System.out.println("End marker found");
                break;
            }
//            text.append((char) Integer.parseInt(byteStr, 2));
            char c = (char) Integer.parseInt(byteStr, 2);
            text.append(c);
        }

        return text.toString();
    }
}
