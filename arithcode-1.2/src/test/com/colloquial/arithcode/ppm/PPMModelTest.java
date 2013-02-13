package com.colloquial.arithcode.ppm;

import org.junit.Test;

import java.io.IOException;

public class PPMModelTest {
    
    @Test
    public void testSuite() throws IOException {
        for (int n = 1; n <= 16; ++n)
            AssertSuite.assertAll(new PpmModelFactory(n));
    }

    static class PpmModelFactory implements ModelFactory {
        final int mN;
        public PpmModelFactory(int n) {
            mN = n;
        }
        public ArithCodeModel create() {
            return new PPMModel(mN);
        }
    }

}