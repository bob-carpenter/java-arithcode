package com.colloquial.arithcode.ppm;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

public class ConverterTest {
    
    @Test
    public void testAll() {
        for (int i = 0; i < 256; ++i) {
            byte b = (byte) ((i < 0) ? (256 - i) : i);
            assertEquals(b,Converter.integerToByte(i));
            assertEquals(i,Converter.byteToInteger(b));
        }
    }
}