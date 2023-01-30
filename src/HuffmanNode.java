public class HuffmanNode {
    // left has zero value and right has one value
    // in order to implement codeWord without need variable in node
    public int freq;
    public ByteArray element;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(int freq, ByteArray elements, HuffmanNode left, HuffmanNode right) {
        this.freq = freq;
        this.element = elements;
        this.left = left;
        this.right = right;
    }
}
