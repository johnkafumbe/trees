package edu.ttap.compression;

/**
 * The driver for the Grin compression program.
 */
public class Grin {
    private static final int MAGIC_NUMBER = 1846;
    
    /**
     * Decodes the .grin file denoted by infile and writes the output to the
     * .grin file denoted by outfile.
     * @param infile the file to decode
     * @param outfile the file to ouptut to
     */
    public static void decode(String infile, String outfile) {
        try {
            BitInputStream in = new BitInputStream(infile);
            BitOutputStream out = new BitOutputStream(outfile);

            // Read and verify the magic number (32 bits)
            int magic = in.readBits(32);
            if (magic != MAGIC_NUMBER) {
                in.close();
                out.close();
                throw new IllegalArgumentException(
                    "Not a valid .grin file. Expected magic number");
            }

            // Reconstruct the Huffman tree
            HuffmanTree tree = new HuffmanTree(in);

            tree.decode(in, out);

            in.close();
            out.close();
        } catch (java.io.IOException e) {
            throw new RuntimeException("I/O error: " + e.getMessage(), e);
        }
    }

    /**
     * The entry point to the program.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Grin <infile> <outfile>");
            System.exit(1);
        }
        
        decode(args[0], args[1]);
    }
}
