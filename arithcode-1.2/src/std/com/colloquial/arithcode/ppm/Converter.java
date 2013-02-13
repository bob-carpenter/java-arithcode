package com.colloquial.arithcode.ppm;

/** Package utility class for converting integers to bytes and back
 * again in a uniform manner. 
 *
 * <p>Thanks to Garrick Toubassi for pointing out the inconsistency
 * with twos-complement arithmetic representations of the way this
 * was done in version 1.1.  
 * 
 * @author <a href="http://www.colloquial.com/carp/">Bob Carpenter</a>
 * @version 1.2
 * @since 1.1
 */
public final class Converter {

    private Converter() {
        /* no instances */
    }

    /** 
     * Returns byte coded by the specified integer.
     *
     * <p>Calling {@code integerToByte(i)} is the same as casting,
     * returning {@code (byte) i}.
     *
     * @param i Integer to conver to a byte.
     * @return Byte coded by the specified integer.
     */
    static public byte integerToByte(int i) {
        return (byte) i;
    }

    /** 
     * Returns integer code for the specified byte.
     *
     * <p>This works by converting the byte to an intger
     * and then masking down to the low order bits,
     * so that {@code byteToInteger(b)} returns
     * the same value as {@code 0xFF & (int) b}.  
     *
     * @param b Byte to code as an integer.
     * @return Integer code for the specified byte.
     */
    static public int byteToInteger(byte b) {
        return 0xFF & (int) b;
    }

}
