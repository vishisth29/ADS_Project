public class RBTree {

    public RBNode root;
    public RBNode base_null;

    public enum color{
        Red,Black
    }

    public RBTree(){
        base_null=RBNode.base_null;
        root=base_null;
        root.left=base_null;
        root.right=base_null;
    }




}
