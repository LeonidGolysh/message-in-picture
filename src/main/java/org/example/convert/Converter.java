package org.example.convert;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

public class Converter {

    public String textToBits(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        BitSet bitSet = BitSet.valueOf(bytes);
        StringBuilder bits = new StringBuilder();

        for (int i = 0; i < bytes.length * 8; i++) {
            bits.append(bitSet.get(i) ? '1' : '0');
        }

        bits.append("1111111111111110"); // End point
        return bits.toString();
    }

    public String bitsToText(String bits) {
        BitSet bitSet = new BitSet(bits.length());

        //Fill thi BitSet by setting bits for each character '1' in the bits string
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(i) == '1') {
                bitSet.set(i);
            }
        }

        byte[] bytes = bitSet.toByteArray();
        debugByteValues(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private void debugByteValues(byte[] bytes) {
        StringBuilder text = new StringBuilder();

        for (byte b : bytes) {

            String byteStr = String.format("%8s", Integer.toBinaryString(b & 0xff).replace(' ', '0'));
            System.out.println("Byte string: " + byteStr);

            text.append((char) b);
        }
    }
}
