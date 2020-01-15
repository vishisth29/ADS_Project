//Basic Node implementation for the heap

public class HNode {

    //executed time
    public int executedTime;
    //Reference to the RBNode
    public RBNode rbNode;

    //Constructor
    public HNode(int executed_time){
        this.executedTime = executed_time;
    }

    @Override
    public String toString() {
        return Integer.toString(executedTime);
    }
}