import java.util.LinkedList;
import java.util.List;
//RBTree Implementation Using CLRS Chapter 13

public class RBTree {

    public RBNode BASE_NULL;// base case condition
    public RBNode root;//set to BASE_NULL initially

    //RBNodes colors
    public enum COLOR {
        RED, BLACK
    }

    //Constructor of RBTree
    public RBTree() {
        BASE_NULL = RBNode.nil;
        root = BASE_NULL;
        root.left = BASE_NULL;
        root.right = BASE_NULL;
    }
    //Search Function used for the print operation

    public void insert(int key) {
        RBNode p = new RBNode(key);
        insertNode(p);
    }
    //Inserts the Node in RedBlackTree
    public void insertNode(RBNode p) {
        p.color = COLOR.RED;
        if (root == BASE_NULL || root.Building_ID == p.Building_ID) {
            root = p;
            root.color = COLOR.BLACK;
            root.parent = BASE_NULL;
            return;
        }
        insertUtil(root, p);
        insertFix(p);
    }


    public boolean delete(RBNode p) {
        return delete(p.Building_ID);
    }

    //Delete similar to BST
    public boolean delete(int key) {
        RBNode y = search(root, key);
        if (y == null) {
            return false;
        }
        RBNode v;
        RBNode temp = y;
        COLOR origColor = y.color;

        if (y.left == BASE_NULL) {
            v = y.right;
            levelUp(y, y.right);
        } else if (y.right == BASE_NULL) {
            v = y.left;
            levelUp(y, y.left);
        } else {
            temp = getMin(y.right);
            origColor = temp.color;
            v = temp.right;
            if (temp.parent == y) {
                v.parent = temp;
            }
            else {
                levelUp(temp, temp.right);
                temp.right = y.right;
                temp.right.parent = temp;
            }
            levelUp(y, temp);
            temp.left = y.left;
            temp.left.parent = temp;
            temp.color = y.color;
        }
        if (origColor == COLOR.BLACK) {
            deleteFix(v);
        }
        return true;
    }


    public RBNode search(int key) {
        return search(root, key);
    }

    //Similar to basic BST Search
    private RBNode search(RBNode root, int key) {
        if (root == BASE_NULL) {
            return null;
        }
        if (root.Building_ID == key) {
            return root;
        } else if (key > root.Building_ID) {
            return search(root.right, key);
        } else {
            return search(root.left, key);
        }
    }

    // Range search function used for the purpose of printing for a given range
    public List<RBNode> searchInRange(int key1, int key2) {
        List<RBNode> list = new LinkedList<RBNode>();
        searchInRange(root, list, key1, key2);
        return list;
    }

    private void searchInRange(RBNode root, List<RBNode> list, int key1, int key2) {
        if (root == BASE_NULL) {
            return;
        }
        if (key1 < root.Building_ID) {
            searchInRange(root.left, list, key1, key2);
        }

        if (key1 <= root.Building_ID && key2 >= root.Building_ID) {
            list.add(root);
        }

        if (key2 > root.Building_ID) {
            searchInRange(root.right, list, key1, key2);
        }
    }

    // Basic right Rotation Function
    private RBNode rightRotate(RBNode a) {

        RBNode b = a.left;
        RBNode ar = a.right;
        RBNode bl = b.left;
        RBNode br = b.right;

        a.left = br;
        if (br != BASE_NULL) {
            br.parent = a;
        }

        b.parent = a.parent;

        if (a.parent == BASE_NULL) {
            root = b;
        } else if (a == a.parent.left) {
            a.parent.left = b;
        } else {
            a.parent.right = b;
        }
        b.right = a;
        a.parent = b;
        return b;
    }

    // Basic left Rotation Function
    private RBNode leftRotate(RBNode x) {
        RBNode y, al, bl, br;
        y = x.right;
        RBNode xl = x.left;
        RBNode yl = y.left;
        RBNode yr = y.right;

        x.right = yl;
        if (yl != BASE_NULL) {
            yl.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == BASE_NULL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
        return y;
    }

    //Simple BST insertion used
    private void insertUtil(RBNode root, RBNode p) {
        if (p.Building_ID < root.Building_ID) {
            if (root.left == BASE_NULL) {
                //Insert here and return
                root.left = p;
                p.parent = root;
            } else {
                insertUtil(root.left, p);
            }
        } else {
            if (root.right == BASE_NULL) {
                //Insert here and return
                root.right = p;
                p.parent = root;
            } else {
                insertUtil(root.right, p);
            }
        }
    }

    //Bottom up Violation fixup
    private void insertFix(RBNode p) {
        RBNode pp = BASE_NULL;
        RBNode gp = BASE_NULL;
        if (p.Building_ID == root.Building_ID) {
            p.color = COLOR.BLACK;
            return;
        }
        while (root.Building_ID != p.Building_ID && p.color != COLOR.BLACK && p.parent.color == COLOR.RED) {
            pp = p.parent;
            gp = pp.parent;


            if (pp == gp.left) {
                RBNode u = gp.right;
                if (u != BASE_NULL && u.color == COLOR.RED) {
                    gp.color = COLOR.RED;
                    pp.color = COLOR.BLACK;
                    u.color = COLOR.BLACK;
                    p = gp;
                } else {
                    if (pp.right == p) {
                        pp = leftRotate(pp);
                        p = pp.left;
                    }
                    rightRotate(gp);
                    swapColors(pp, gp);
                    p = pp;
                }

            } else if (pp == gp.right) {
                RBNode u = gp.left;
                if (u != BASE_NULL && u.color == COLOR.RED) {
                    gp.color = COLOR.RED;
                    pp.color = COLOR.BLACK;
                    u.color = COLOR.BLACK;
                    p = gp;
                } else {
                    if (pp.left == p) {
                        pp = rightRotate(pp);
                        p = pp.right;
                    }
                    leftRotate(gp);
                    swapColors(pp, gp);
                    p = pp;
                }
            }
        }
        root.color = COLOR.BLACK;
    }

    //Used in delete function to move the node up a level and replace it
    private void levelUp(RBNode a, RBNode b) {
        if (a.parent == BASE_NULL) {
            root = b;
        } else if (a == a.parent.left) {
            a.parent.left = b;
        } else {
            a.parent.right = b;
        }
        b.parent = a.parent;
    }


    // Corrects the violation  after the delete the function, Bottom up manner
    private void deleteFix(RBNode py) {


        while (py != root && py.color == COLOR.BLACK) {

            if (py == py.parent.left) {

                RBNode v = py.parent.right;


                if (v.color == COLOR.RED) {
                    v.color = COLOR.BLACK;
                    py.parent.color = COLOR.RED;
                    leftRotate(py.parent);
                    v = py.parent.right;
                }

                if (v.left.color == COLOR.BLACK && v.right.color == COLOR.BLACK) {
                    v.color = COLOR.RED;
                    py = py.parent;
                    continue;
                }

                else if (v.right.color == COLOR.BLACK) {
                    v.left.color = COLOR.BLACK;
                    v.color = COLOR.RED;
                    rightRotate(v);
                    v = py.parent.right;
                }

                if (v.right.color == COLOR.RED) {
                    v.color = py.parent.color;
                    py.parent.color = COLOR.BLACK;
                    v.right.color = COLOR.BLACK;
                    leftRotate(py.parent);
                    py = root;
                }


            } else {

                RBNode v = py.parent.left;


                if (v.color == COLOR.RED) {
                    v.color = COLOR.BLACK;
                    py.parent.color = COLOR.RED;
                    rightRotate(py.parent);
                    v = py.parent.left;
                }


                if (v.right.color == COLOR.BLACK && v.left.color == COLOR.BLACK) {
                    v.color = COLOR.RED;
                    py = py.parent;
                    continue;
                }

                else if (v.left.color == COLOR.BLACK) {
                    v.right.color = COLOR.BLACK;
                    v.color = COLOR.RED;
                    leftRotate(v);
                    v = py.parent.left;
                }

                if (v.left.color == COLOR.RED) {
                    v.color = py.parent.color;
                    py.parent.color = COLOR.BLACK;
                    v.left.color = COLOR.BLACK;
                    rightRotate(py.parent);
                    py = root;
                }
            }
        }

        py.color = COLOR.BLACK;
    }

    // As the RBTree stucture suggets the min node it the left most node
    private RBNode getMin(RBNode root) {
        while (root.left != BASE_NULL) {
            root = root.left;
        }
        return root;
    }
    //Swap colors of 2 node
    private void swapColors(RBNode pp, RBNode gp) {
        COLOR temp = pp.color;
        pp.color = gp.color;
        gp.color = temp;
    }

}

