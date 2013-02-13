package com.colloquial.arithcode.ppm;

import org.junit.Test;

import java.io.IOException;

public class UniformModelTest {
    
    @Test
    public void testSuite() throws IOException {
        AssertSuite.assertAll(new ModelFactory() {
                public ArithCodeModel create() {
                    return UniformModel.MODEL;
                }
            });
    }

}