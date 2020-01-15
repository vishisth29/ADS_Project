import java.util.ArrayList;
/*
Min Heap Implementation using an ArrayList
*/

public class MinHeap {

    private ArrayList<HNode> data;

    private boolean isMin;//Could be used as Max Heap as well

    //Constructor1
    public MinHeap() {
        this(true);
        this.data = new ArrayList<HNode>();
    }
    //Constructor2
    public MinHeap(ArrayList<HNode> arr) {
        this.data = arr;
    }
    //Constructor3
    public MinHeap(boolean isMin) {
        this.data = new ArrayList<>();

        this.isMin = isMin;
    }
    //Constructor4
    public MinHeap(HNode[] items, boolean isMin) {
        this(isMin);

        for (HNode item : items) {
            this.data.add(item);

        }

        for (int i = this.data.size() / 2 - 1; i >= 0; i--) {
            this.downHeapify(i);
        }
    }
    //return the size of the heap
    public int size() {
        return this.data.size();
    }
    //return if the heap is empty
    public boolean isEmpty() {
        return this.size() == 0;
    }

    //Adds a new HNode
    public void add(HNode item) {
        this.data.add(item);


        this.upHeapify(this.data.size() - 1);
    }
    //Heapify function used to maintain the heap property
    public void upHeapify(int ci) {
        if (ci == 0) {
            return;
        }

        int pi = (ci - 1) / 2;

        if (!isLarger(pi, ci)) {
            swap(pi, ci);
            this.upHeapify(pi);
        }
    }
    //Remove function
    public HNode remove() {
        HNode rv = this.data.get(0);

        swap(0, this.data.size() - 1);


        this.data.remove(this.data.size() - 1);
        //call to maintain the heap conditions
        this.downHeapify(0);

        return rv;
    }
    //Works to maintain the heap condition
    private void downHeapify(int pi) {
        int lci = 2 * pi + 1;
        int rci = 2 * pi + 2;

        int mi = pi;

        if (lci < this.data.size() && this.isLarger(lci, mi)) {
            mi = lci;
        }

        if (rci < this.data.size() && this.isLarger(rci, mi)) {
            mi = rci;
        }

        if (mi != pi) {
            this.swap(mi, pi);
            this.downHeapify(mi);
        }
    }
    //swaps the HNode data
    private void swap(int i, int j) {
        HNode temp = this.data.get(i);
        this.data.set(i, this.data.get(j));
        this.data.set(j, temp);

    }
    //Check which is larger
    //Contains the case for the tie condition
    private boolean isLarger(int i, int j) {
        HNode ithItem = this.data.get(i);
        HNode jthItem = this.data.get(j);

        // Extra case added to handle tie cases while working with upHeapify
        //45 case in sample_input2
        if (ithItem.executedTime == jthItem.executedTime) {
            if (ithItem.rbNode.Building_ID < jthItem.rbNode.Building_ID) {
                return true;
            } else {
                return false;
            }
        }


        if (this.isMin) {
            return ithItem.executedTime < jthItem.executedTime;
        } else {
            return ithItem.executedTime > jthItem.executedTime;
        }
    }


}
