package com.colloquial.arithcode.demo;

import com.colloquial.arithcode.ppm.PPMModel;
import com.colloquial.arithcode.ppm.ArithCodeInputStream;

import static com.colloquial.io.Util.copy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;


/** 
 * Command-line function for compression.
 *
 * <P>
 * <b>Usage:</b>
 *
 * <blockquote><pre>java PPMCompress <i>Order FileIn FileOut</i></pre></blockquote>
 * 
 * <ul>
 * <li> <code><i>Order</i></code>: Order of PPM model to use.
 * <li><code><i>FileIn</i></code>: Input file to decompress. 
 * <li><code><i>FileOut</i></code>: Output for decompressed file.
 * </ul>
 *
 * <p>This is just meant as a demo.  A more sophisticated version
 * would name arguments and allow input or output files to be ommitted
 * and use standard input and output.  And would write the order as
 * the first byte of the output so that it didn't need to be specified.
 * 
 * @author <a href="http://www.colloquial.com/carp/">Bob Carpenter</a>
 * @version 1.2
 * @since 1.1
 */
public final class PPMDecompress {

    /** 
     * Decompress the specified file using the specified order
     * of PPM.  See the class documentation for more information.
     *
     * @param args Command-line argument.
     * @throws IOException If there is an underlying IO exception.
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println(USAGE_MESSAGE);
            System.exit(1);
        }
        int order = Integer.valueOf(args[0]);
        File inFile = new File(args[1]);
        File outFile = new File(args[2]);
        
        PPMModel model = new PPMModel(order);
        InputStream fileIs = new FileInputStream(inFile);
        InputStream bufIs = new BufferedInputStream(fileIs);
        InputStream arithIs = new ArithCodeInputStream(bufIs,model);
        OutputStream fileOs = new FileOutputStream(outFile);
        OutputStream bufOs = new BufferedOutputStream(fileOs);
        copy(arithIs,bufOs);
        bufOs.close();
    }

    private static String USAGE_MESSAGE
        = "\nUSAGE: java PPMDecompress Order FileIn FileOut";

}
