
public class RBNode {

    public static final RBNode nil = new RBNode(-Integer.MIN_VALUE, RBTree.COLOR.BLACK);//We always take the new added node as Black

    public int Building_ID; //Building_ID
    public int totalTime;//Total executed time of current building
    public RBNode left = nil;
    public RBNode right = nil;
    public RBNode parent = nil;
    public RBTree.COLOR color;//RED/Black color
    public HNode heapNode;//Object reference to heapNode in MinHeap

    //Constructors:
    public RBNode(int building_number, HNode heapNode){
        this.Building_ID = building_number;
        this.heapNode = heapNode;
    }

    public RBNode(int building_number){
        this.Building_ID = building_number;
    }

    public RBNode(int building_number, RBTree.COLOR color){
        this.Building_ID = building_number;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Key:"+this.Building_ID +",Color:"+this.color.name();
    }
}