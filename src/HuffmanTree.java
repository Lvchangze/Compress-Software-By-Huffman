import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanTree {
    Comparator<HuffmanNode> order = new Comparator<HuffmanNode>() {
        public int compare(HuffmanNode o1, HuffmanNode o2) {
            return o1.rate - o2.rate;
        }
    };
    HuffmanNode root;
    HuffmanNode[] array = new HuffmanNode[256];
    long codeLength=0;

    long codeNum=0;
    HuffmanTree(int[] bytes) {
        for (int i = 0; i < 256; i++) {
            array[i] = new HuffmanNode(bytes[i], null, null, true,i);
        }
        buildTree(256, array);
        getCode(this.root,"");
        for (int i = 0; i < 256; i++) {
            codeLength+= (array[i].code.length())*(array[i].rate);
            codeNum+=array[i].rate;
            array[i].codelen=array[i].code.length();
        }
    }

    public void buildTree(int capacity, HuffmanNode[] nodes) {//构树
        Queue<HuffmanNode> priorityQueue = new PriorityQueue<HuffmanNode>(order);
        for (int i = 0; i < capacity; i++) {
            priorityQueue.add(nodes[i]);
        }
        while (priorityQueue.size() > 1) {
            HuffmanNode n1 = priorityQueue.poll();
            HuffmanNode n2 = priorityQueue.poll();
            HuffmanNode n3 = new HuffmanNode(n1.rate + n2.rate, n1, n2, false,-1);
            priorityQueue.add(n3);
        }
        this.root = priorityQueue.peek();
    }

    public void getCode(HuffmanNode node,String code){
        node.code=code;
        if(!node.isLeaf){
            getCode(node.left,code+"0");
            getCode(node.right,code+"1");
        }
    }
}
