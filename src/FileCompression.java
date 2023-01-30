import java.io.*;
import java.util.*;

public class FileCompression {
    private final String inputPath;
    private final String outputPath;
    private final int n;
    private long numberN_Bytes;
    private int chunkSize;
    HashMap<ByteArray,Integer> freqMap;
    HashMap<String,ByteArray> codeWordMap;
    HashMap<ByteArray,String> invertedCodeWordMap;
    BufferedOutputStream bufferedOutputStream;
    private StringBuilder stringBuilder;

    public FileCompression(String inputPath, String outputPath, int n) throws FileNotFoundException {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.n = n;
        this.numberN_Bytes = 0;
        this.chunkSize = 10000 * n;
        this.freqMap = new HashMap<>();
        this.codeWordMap = new HashMap<>();
        this.invertedCodeWordMap = new HashMap<>();
        this.bufferedOutputStream = new BufferedOutputStream( new FileOutputStream(this.outputPath));
        this.stringBuilder = new StringBuilder();
    }

    public void compress() throws IOException {
        long time = System.currentTimeMillis(), startTime = System.currentTimeMillis();

        readChunks(this::freqCalculator);
        System.out.println("read file and create frequency map = " + (System.currentTimeMillis() - time) + "ms");
        time = System.currentTimeMillis();

        HuffmanNode root = huffmanTreeBuilder(this.freqMap);
        System.out.println("build huffman tree = " + (System.currentTimeMillis() - time) + "ms");
        time = System.currentTimeMillis();

        codeWordBuilder(root,"");
        invertedCodeWordBuilder();
        System.out.println("build code word map = " + (System.currentTimeMillis() - time) + "ms");
        time = System.currentTimeMillis();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.bufferedOutputStream);
        objectOutputStream.writeLong(this.numberN_Bytes);
        objectOutputStream.writeObject(this.freqMap);
        System.out.println("write map in compressed file = " + (System.currentTimeMillis() - time) + "ms");
        time = System.currentTimeMillis();

        readChunks(this::compressFile);
        if(this.stringBuilder.length() != 0){
            while (this.stringBuilder.length() < 8)
                this.stringBuilder.append('0');
            byte b = (byte) Integer.parseInt(this.stringBuilder.substring(0,8),2);
            this.bufferedOutputStream.write(b);
        }
        System.out.println("write compressed file = " + (System.currentTimeMillis() - time) + "ms");

//        this.bufferedOutputStream.close();
        objectOutputStream.close();
        System.out.println("Total Time = " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private void readChunks(Lambda lambda) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.inputPath));
        while(bufferedInputStream.available() != 0){
            byte[] fileBytes = bufferedInputStream.readNBytes(chunkSize);
            ByteArray[] bytes = groupN_Bytes(fileBytes,this.n);
            lambda.lambdaFunction(bytes);
        }
        bufferedInputStream.close();
    }

    private void freqCalculator(ByteArray[] byteArray){
        for(ByteArray bytes : byteArray) {
            this.freqMap.put(bytes, this.freqMap.getOrDefault(bytes, 0) + 1);
            this.numberN_Bytes++;
        }
    }

    private ByteArray[] groupN_Bytes(byte[] fileBytes, int n){
        byte[] temp = new byte[n];
        int counter = 0;
        List<ByteArray> byteArrays = new ArrayList<>();
        for(int i = 0; i < fileBytes.length; i++){
            temp[counter] = (fileBytes[i]);
            counter++;
            if(i % n == n-1){
                byteArrays.add(new ByteArray(temp));
                counter = 0;
                temp = new byte[n];
            }
        }
        if(counter != 0)
            byteArrays.add(new ByteArray(Arrays.copyOf(temp,counter)));
        ByteArray[] N_Bytes = new ByteArray[byteArrays.size()];
        for(int i = 0; i < N_Bytes.length; i++){
            N_Bytes[i] = byteArrays.get(i);
        }
        return N_Bytes;
    }

    public static HuffmanNode huffmanTreeBuilder(HashMap<ByteArray, Integer> freqMap){
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>((a, b) -> a.freq - b.freq);
        for(Map.Entry<ByteArray,Integer> mapEntry : freqMap.entrySet())
            pq.add(new HuffmanNode(mapEntry.getValue(), mapEntry.getKey(),null,null));
        for(int i = 1; i < freqMap.size(); i++){
            HuffmanNode min1FreqNode = pq.poll();
            HuffmanNode min2FreqNode = pq.poll();
            if(min1FreqNode == null || min2FreqNode == null)
                continue;
            pq.add(new HuffmanNode(min1FreqNode.freq + min2FreqNode.freq,null,
                    min1FreqNode,min2FreqNode));
        }
        return pq.poll();
    }

    private void codeWordBuilder(HuffmanNode node, String codeWord){
        if(node.element != null){
            codeWordMap.put(codeWord,node.element);
            return;
        }
        codeWordBuilder(node.left, codeWord + "0");
        codeWordBuilder(node.right, codeWord + "1");
    }

    private void invertedCodeWordBuilder(){
        for(Map.Entry<String,ByteArray> entry : this.codeWordMap.entrySet())
            this.invertedCodeWordMap.put(entry.getValue(), entry.getKey());
    }

    private void compressFile(ByteArray[] N_Bytes) throws IOException {
        for(ByteArray N_Byte : N_Bytes){
            String codeWord = this.invertedCodeWordMap.get(N_Byte);
            this.stringBuilder.append(codeWord);
            if(this.stringBuilder.length() >= 8){
                while (this.stringBuilder.length() >= 8) {
                    byte b = (byte) Integer.parseInt(this.stringBuilder.substring(0,8),2);
                    this.bufferedOutputStream.write(b);
                    this.stringBuilder.delete(0,8);
                }
            }
        }
    }
}
