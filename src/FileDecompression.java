import java.io.*;
import java.util.*;

public class FileDecompression {
    private final String inputPath;
    private final String outputPath;
    static final int chunkSize =  10000;
    private HashMap<ByteArray,Integer> freqMap;
    private HuffmanNode root;
    private HuffmanNode currentHuffmanNode;
    private long numberN_Bytes;
    private BufferedOutputStream bufferedOutputStream;
    private BufferedInputStream bufferedInputStream;

    public FileDecompression(String inputPath, String outputPath) throws FileNotFoundException {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.numberN_Bytes = 0;
        this.bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.outputPath));
        this.bufferedInputStream = new BufferedInputStream(new FileInputStream(this.inputPath));
    }

    public void decompress() throws IOException, ClassNotFoundException {
        long time = System.currentTimeMillis(), startTime = System.currentTimeMillis();

        ObjectInputStream objectInputStream = new ObjectInputStream(this.bufferedInputStream);
        this.numberN_Bytes = objectInputStream.readLong();
        this.freqMap = (HashMap<ByteArray, Integer>) objectInputStream.readObject();
        System.out.println("read frequency map = " + (System.currentTimeMillis() - time) + "ms");
        time = System.currentTimeMillis();

        this.root = FileCompression.huffmanTreeBuilder(this.freqMap);
        this.currentHuffmanNode = this.root;
        System.out.println("re build huffman tree = " + (System.currentTimeMillis() - time) + "ms");
        time = System.currentTimeMillis();

        readChunks();
        System.out.println("extract and write file = " + (System.currentTimeMillis() - time) + "ms");

        this.bufferedInputStream.close();
        this.bufferedOutputStream.close();
        System.out.println("Total Time = " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private void readChunks() throws IOException {
        while(this.bufferedInputStream.available() != 0){
            byte[] fileBytes = this.bufferedInputStream.readNBytes(chunkSize);
            extractAndWriteBytes(fileBytes);
        }
    }

    private void extractAndWriteBytes(byte[] fileBytes) throws IOException {
        for(byte b : fileBytes){
            for (int i = 7; i >= 0; i--) {
                int bit = (b >> i) & 1;
                if(bit == 0)
                    this.currentHuffmanNode = this.currentHuffmanNode.left;
                else
                    this.currentHuffmanNode = this.currentHuffmanNode.right;
                if(this.currentHuffmanNode.element != null){
                    this.bufferedOutputStream.write(this.currentHuffmanNode.element.getArray());
                    this.currentHuffmanNode = this.root;
                    this.numberN_Bytes--;
                    if(this.numberN_Bytes == 0)
                        return;
                }
            }
        }
    }


}
