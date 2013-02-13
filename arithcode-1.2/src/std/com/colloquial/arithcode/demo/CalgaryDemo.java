package com.colloquial.arithcode.demo;

import com.colloquial.arithcode.ppm.AdaptiveUnigramModel;
import com.colloquial.arithcode.ppm.ArithCodeInputStream;
import com.colloquial.arithcode.ppm.ArithCodeModel;
import com.colloquial.arithcode.ppm.ArithCodeOutputStream;
import com.colloquial.arithcode.ppm.PPMModel;
import com.colloquial.arithcode.ppm.UniformModel;

import static com.colloquial.io.Util.copy;
import static com.colloquial.io.Util.readBytes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Runs the Calgary corpus through the various models and
 * prints out the time and compression rates.  The utility
 * methods are public so that you can run your own tess
 * using this class.
 *
 * @author Bob Carpenter
 * @version 1.2
 * @since 1.2
 */
public class CalgaryDemo {

    /**
     * Run the Calgary demo of compression time and rate with the
     * specified corpus location.
     * 
     * @param args First argument is used as the location of the corpus.
     * @throws IOException If there is an error reading the corpus.
     */
    public static void main(String[] args) throws IOException {
        File corpusDir = new File(args[0]);
        for (File file : corpusDir.listFiles())
            demo(file);
    }

    /**
     * Run the demo compressors on the specified file.
     */
    public static void demo(File file) throws IOException {
        if (file.getName().startsWith("."))
            return;

        byte[] bytes = readBytes(file);
        System.out.println("\nFILE=" + file +" #bytes=" + bytes.length);

        demo(bytes,UniformModel.MODEL,UniformModel.MODEL);

        demo(bytes,
             new AdaptiveUnigramModel(), 
             new AdaptiveUnigramModel());

        for (int n = 0; n <= 16; n += (n > 7 ? 2 : 1))
            demo(bytes, new PPMModel(n), new PPMModel(n));
    }

    /**
     * Run the specified models on the specified bytes.
     */
    public static void demo(byte[] bytes,
                            ArithCodeModel modelEncode,
                            ArithCodeModel modelDecode) 
        throws IOException {

        ByteArrayOutputStream out
            = new ByteArrayOutputStream();
        OutputStream encoder
            = new ArithCodeOutputStream(out,
                                        modelEncode);
        long startTime = System.currentTimeMillis();
        encoder.write(bytes,0,bytes.length);
        encoder.close();
        long endTime = System.currentTimeMillis();
        double encodeTime = (endTime - startTime)/10000.0;

        byte[] encodedBytes = out.toByteArray();
        ByteArrayInputStream encodedBytesIn
            = new ByteArrayInputStream(encodedBytes);
        ArithCodeInputStream decodedBytesIn
            = new ArithCodeInputStream(encodedBytesIn,
                                       modelDecode);
        ByteArrayOutputStream decodedBytesOut
            = new ByteArrayOutputStream();

        startTime = System.currentTimeMillis();
        copy(decodedBytesIn,decodedBytesOut);
        endTime = System.currentTimeMillis();
        double decodeTime = (endTime - startTime)/10000.0;
        
        System.out.printf("%20s  enc=%6.1f MB/s   dec=%6.1f MB/s   comp= %5.3f b/B\n",
                          modelEncode.toString(),
                          bytes.length/encodeTime/1000000.0,
                          bytes.length/decodeTime/1000000.0,
                          (8.0 * encodedBytes.length)/ bytes.length);

        byte[] decodedBytes = decodedBytesOut.toByteArray();

        if (!java.util.Arrays.equals(bytes,decodedBytes)) {
            System.out.println("ERROR: Decoded bytes do not match original.");
        }
    }


}