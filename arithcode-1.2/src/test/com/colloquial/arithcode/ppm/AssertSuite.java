package com.colloquial.arithcode.ppm;

import static com.colloquial.io.Util.copy;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.util.Arrays;
import java.util.Random;


public class AssertSuite {

    static final byte[][] FIXED_TESTS = {
        { },
        { 0 },
        { 0, 0 },
        { -128 },
        { -128, -128 },
        { 0, -128 },
        { -128, 0, 0 },
        { 1 },
        { 3, 7 },
        { 1, 2, 3 },
    };

    public static void assertAll(ModelFactory factory) throws IOException {
        for (byte[] bytes : FIXED_TESTS)
            assertModel(factory,bytes);
    
        Random random = new Random();
        for (int i = 0; i < 257; ++i) {
            byte[] bytes = new byte[i];
            random.nextBytes(bytes);
            assertModel(factory,bytes);
        }
        for (int i = 1; i < 100000; i *= 3) {
            byte[] bytes = new byte[i];
            random.nextBytes(bytes);
            assertModel(factory,bytes);
        }
    }

    static void assertModel(ModelFactory factory,
                            byte[] bytes) 
        throws IOException {

        ArithCodeModel modelEncode = factory.create();
        byte[] codeBytes = encode(modelEncode,bytes);

        ArithCodeModel modelDecode = factory.create();
        byte[] decodedBytes = decode(modelDecode,codeBytes);

        assertTrue(Arrays.equals(bytes,decodedBytes));
    }

    static byte[] encode(ArithCodeModel model,
                         byte[] bytes) throws IOException {
        ByteArrayOutputStream out
            = new ByteArrayOutputStream();
        OutputStream encoder
            = new ArithCodeOutputStream(out,model);
        encoder.write(bytes,0,bytes.length);
        encoder.close();
        return out.toByteArray();
    }

    static byte[] decode(ArithCodeModel model,
                         byte[] bytes) throws IOException {
        InputStream in
            = new ByteArrayInputStream(bytes);
        InputStream decoder
            = new ArithCodeInputStream(in,model);
        ByteArrayOutputStream out
            = new ByteArrayOutputStream();
        copy(decoder,out);
        return out.toByteArray();
    }






}