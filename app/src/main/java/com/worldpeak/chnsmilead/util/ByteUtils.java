package com.worldpeak.chnsmilead.util;

import java.util.Random;
import java.util.UUID;

public final class ByteUtils {

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private ByteUtils() {
    }

    /**
     * Given an int, returns the int as a 2 byte array of unsigned bytes.
     *
     * @param v the value to convert
     * @return a byte array with two elements
     */
    public static byte[] smallIntToByteArray(int v) {
        if (v > 65535) {
            throw new IllegalArgumentException("value is too big");
        }

        return new byte[]{(byte) ((v >>> 8) & 0xFF), (byte) ((v >>> 0) & 0xFF)};
    }

    /**
     * Used to express smaller sizes (2 bytes), since UDP Packets can be of up
     * to 65535 (including headers). Our package sizes should not be longer than
     * 65,400 bytes.
     *
     * @param arr    array of at least 2 bytes
     * @param offset offset that leaves at least 2 bytes forward
     * @return the small int value
     */
    public static int byteArrayToSmallInt(byte[] arr, int offset) {
        if (arr == null || arr.length - offset < 2) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        return ((arr[offset] & 0xFF) << 8) + (arr[1 + offset] & 0xFF);
    }

    // 3 bytes (16,777,215 - 16 MB)
    public static byte[] smallIntToTripleByteArray(int v) {
        if (v > 16777215) {
            throw new IllegalArgumentException("value is too big");
        }

        return new byte[]{(byte) ((v >>> 16) & 0xFF), (byte) ((v >>> 8) & 0xFF), (byte) ((v >>> 0) & 0xFF)};
    }

    /**
     * Used to express numbers up to 16,777,215.
     *
     * @param arr - array of at least 3 bytes
     *            - offset that leaves at least 3 bytes forward
     * @return
     */
    public static int tripleByteArrayToSmallInt(byte[] arr, int offset) {
        if (arr == null || arr.length - offset < 2) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        return ((arr[offset] & 0xFF) << 16) + ((arr[1 + offset] & 0xFF) << 8) + (arr[2 + offset] & 0xFF);
    }

    /**
     * Returns a 3 byte array checksum number calculated on the given array.
     * <p>
     * The checksum algorithm is very simple. We add up every N bytes in the
     * array, and return this number as a 3 byte array.
     * <p>
     * Currently we're jumping 16 bytes at the time. This takes up to 1
     * millisecond. A 13mb made of 200 byte arrays of 65535 bytes parts can be
     * checksumed in 245ms on a Nexus One.
     *
     * @param arr - An array of no more than 65535 bytes.
     * @return the checksum array of size three
     */
    public static byte[] getByteArrayChecksum(byte[] arr) {
        if (arr.length > 65535) {
            throw new IllegalArgumentException("Byte array is too long");
        }

        // max UDP packets are 65535 bytes long
        // even if we used all of those bytes, the max number added would be
        int result = 0;
        int step = arr.length > 100 ? 16 : 1;

        for (int i = 0; i < arr.length; i += step) {
            result += (arr[i] & 0xFF);
        }

        return smallIntToTripleByteArray(result);
    }

    /**
     * Returns a new byte array: c = a + b.
     * <p>
     * The array a will be towards the 0 index, the array b right after.
     * <p>
     * c.length = a.length + b.length
     *
     * @param a the first array
     * @param b the second array
     * @return the final array
     */
    public static byte[] appendByteArrays(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];

        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        return c;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random(System.currentTimeMillis());
        return min + random.nextInt(max - min);
    }

    public static byte[] decodeHex(String str) {
        str = str.toLowerCase();
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return data;
    }

    public static String encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return new String(out);
    }

    public static byte[] uuidToByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }

        return buffer;
    }
}
