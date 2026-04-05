package edu.ttap.compression;

/**
 * A HuffmanTree derives a space-efficient coding of a collection of byte
 * values.
 *
 * The huffman tree encodes values in the range 0--255 which would normally
 * take 8 bits. However, we also need to encode a special EOF character to
 * denote the end of a .grin file. Thus, we need 9 bits to store each
 * byte value. This is fine for file writing (modulo the need to write in
 * byte chunks to the file), but Java does not have a 9-bit data type.
 * Instead, we use the next larger primitive integral type, short, to store
 * our byte values.
 */
public class HuffmanTree {

    private Node root;
    
    private static final short EOF = 256;

    /**
     * Inner class to represent the binary tree structure
     */
    private static class Node {
        short value;
        
        Node left;
        
        Node right;

        Node(short value) {
            this.value = value;
            this.right = null;
            this.left = null;
        }

        Node(Node left, Node right) {
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    /**
     * Constructs a new HuffmanTree from the given file.
     * 
     * @param in the input file (as a BitInputStream)
     */
    public HuffmanTree(BitInputStream in) {
        root = readTree(in);
    }

    /**
     * Reads bits from stream to recontruct the huffman tree
     * 
     * @param in the bit stream to read from
     * @return the reconstructed node
     */

    private Node readTree(BitInputStream in) {
        int bit = in.readBit();

        if (bit == 0) {
            short value = (short) in.readBits(9);
            return new Node(value);
        } else {
            Node left = readTree(in);
            Node right = readTree(in);
            return new Node(left, right);
        }

    }

    /**
     * Decodes a stream of huffman codes from a file given as a stream of
     * bits into their uncompressed form, saving the results to the given
     * output stream. Note that the EOF character is not written to out
     * because it is not a valid 8-bit chunk (it is 9 bits).
     * 
     * @param in  the file to decompress.
     * @param out the file to write the decompressed output to.
     */
    public void decode(BitInputStream in, BitOutputStream out) {
        Node current = root;

        while (true) {

            int bit = in.readBit();

            if (bit == 0) {
                current = current.left;
            } else {
                current = current.right;
            }
            if (current.isLeaf()){
                        if (current.value == EOF) {
                            break;
                        }
                        out.writeBits(current.value, 8);
                        current = root;
                    }
                }
            }
        }
    

