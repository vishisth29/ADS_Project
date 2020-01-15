public class RBNode {

    public static final RBNode base_null = new RBNode(-999, RBTree.COLOR.BLACK);
    //Base null case

    public int key; //Unique JobID
    public int totalTime;//Total executed time of current job
    public RBNode left = base_null;
    public RBNode right = base_null;
    public RBNode parent = base_null;
    public RBTree.COLOR color;//RED/Black color
    public HNode heapNode;//Object reference to HNode in Heap for

    //Constructors:
    public RBNode(int key, HNode heapNode){
        this.key = key;
        this.heapNode = heapNode;
    }

    public RBNode(int unique_id){
        this.key = unique_id;
    }

    public RBNode(int unique_id, RBTree.COLOR color){
        this.key = unique_id;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Key:"+this.key+",Color:"+this.color.name();
    }
}
