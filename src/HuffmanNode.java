public class HuffmanNode {
    int rate;
    boolean isLeaf;
    int data;
    HuffmanNode left;
    HuffmanNode right;
    String code;
    int codelen;

    HuffmanNode(int rate, HuffmanNode left, HuffmanNode right, boolean isLeaf,int data) {
        this.rate = rate;
        this.left = left;
        this.right = right;
        this.isLeaf = isLeaf;
        this.data=data;
        this.code="";
    }
}
