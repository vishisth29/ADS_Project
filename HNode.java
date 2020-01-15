public class HNode {
    int key; //the executed time of the building
    RBNode rbnode;//Reference to the RBNode

    public HNode(int time){
        this.key=time;//Since the heap works on base of the executed time

    }
    @Override
    public String toString(){
        return Integer.toString(this.key);
    }

}
