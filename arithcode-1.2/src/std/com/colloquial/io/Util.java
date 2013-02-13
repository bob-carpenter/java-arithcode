package com.colloquial.io;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Static utilities for input/output.
 *
 * @author Bob Carpenter
 * @version 1.2
 * @since 1.2
 */
public class Util {

    /** Read all of the input from the given input stream and write
     * it to the given output stream.
     * @param in Input stream from which to read.
     * @param out Output stream to which to write.
     * @throws IOException If there is an exception reading or writing on the given streams.
     */
    public static void copy(InputStream in, OutputStream out) 
        throws IOException {

        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) >= 0)
            out.write(buf,0,n);
        in.close();
        out.close();
    }

    /**
     * Return the array of bytes read from the specified
     * file.
     *
     * @param file File frmo which to read.
     * @return Bytes read from file.
     * @throws IOException If there is an error opening or readig from
     * the file.
     */
    public static byte[] readBytes(File file) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = new FileInputStream(file);
        copy(in,out);
        return out.toByteArray();
    }



}