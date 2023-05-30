package AVL_Tree;

// AVL дерево
public class AVL_Tree<AnyType extends Comparable<? super AnyType>> {
    private static final int ALLOWED_IMBALANCE = 1;
    private KnotOfTree<AnyType> rootKnot;// root node

    public AVL_Tree() {
        rootKnot = null;
    }

    // Очистка
    public void clearKnot() {
        rootKnot = null;
    }

    // Determining if a node is empty
    public boolean isClear() {
        return rootKnot == null;
    }

    // Determine if the tree contains element x
    public AnyType getKnot(AnyType x) {
        return getKnot(x, rootKnot);
    }

    // Find the smallest element in the tree and return
    public AnyType getMin() throws Exception {
        if (isClear()) {
            throw new Exception();
        }
        return getMin(rootKnot).t_key;
    }

    // Find the largest element in the tree and return
    public AnyType getMax() throws Exception {
        if (isClear()) {
            throw new Exception();
        }
        return getMax(rootKnot).t_key;
    }

    // insert element x
    public void insert(AnyType x, AnyType val) {
        rootKnot = insert(x, val, rootKnot);
    }

    // remove element x
    public void remove(AnyType x) {
        rootKnot = remove(x, rootKnot);
    }

    // print the tree
    public void printTree() {
        if (isClear()) {
            System.out.println("Пустое дерево");
        } else {
            printTree(rootKnot);
        }
    }

    //Print the tree ---- "Bypass by order
    private void printTree(KnotOfTree<AnyType> knot) {
        if (knot != null) {
            printTree(knot.lKnot);
            System.out.print(knot.t_key + " : " + knot.t_value + "\n");
            printTree(knot.rKnot);
        }
    }

    // returns the height of the tree
    private int getHeight(KnotOfTree<AnyType> node) {
        return node == null ? -1 : node.hKnot;
    }

    // Insert node x in the tree
    private KnotOfTree<AnyType> insert(AnyType x, AnyType val, KnotOfTree<AnyType> knot) {
        if (knot == null) {
            return new KnotOfTree<AnyType>(x, val, null, null);
        }
        int res = x.compareTo(knot.t_key);

        if (res  < 0) {
            knot.lKnot = insert(x, val, knot.lKnot);
        }
        else
        if (res  > 0) {
            knot.rKnot = insert(x, val, knot.rKnot);
        }
        else {
            ;
        }
        return balance(knot);// Balancing the tree
    }

    // Balancing the tree
    private KnotOfTree<AnyType> balance(KnotOfTree<AnyType> knot) {
        if (knot == null) {
            return knot;
        }
        // The difference in height between the left sub-tree and the right sub-tree exceeds 1
        if (getHeight(knot.lKnot) - getHeight(knot.rKnot) > ALLOWED_IMBALANCE) {
            if (getHeight(knot.lKnot.lKnot) >= getHeight(knot.lKnot.rKnot)) {
                knot = rotateLeftChild(knot);
            }
            else {
                knot = doubleRotateLeftChild(knot);
            }
        }
        else {
            if (getHeight(knot.rKnot) - getHeight(knot.rKnot) > ALLOWED_IMBALANCE) {
                if (getHeight(knot.rKnot.rKnot) >= getHeight(knot.rKnot.lKnot)) {
                    knot = rotateRightChild(knot);
                }
                else {
                    knot = doubleRotateRightChild(knot);
                }
            }
        }
        knot.hKnot = Math.max(getHeight(knot.lKnot), getHeight(knot.rKnot));
        return knot;
    }

    //remove node x
    private KnotOfTree<AnyType> remove(AnyType x, KnotOfTree<AnyType> knot) {
        if (knot == null) {
            return knot;
        }
        int res  = x.compareTo(knot.t_key);
        if (res  < 0) {
            knot.lKnot = remove(x, knot.lKnot);
        }
        else
        if (res  > 0) {
            knot.rKnot = remove(x, knot.rKnot);
        }
        else
        if (knot.lKnot != null && knot.rKnot != null) {
            knot.t_key = getMin(knot.rKnot).t_key;
            knot.rKnot = remove(knot.t_key, knot.rKnot);
        }
        else {
            knot = (knot.lKnot != null) ? knot.lKnot : knot.rKnot;
        }
        return balance(knot);
    }

    // One rotation ---> Corresponding imbalance: ---> Left subtree node height greater than right subtree node height
    private KnotOfTree<AnyType> rotateLeftChild(KnotOfTree<AnyType> knot_2) {
        KnotOfTree<AnyType> knot_1 = knot_2.lKnot;
        knot_2.lKnot = knot_1.rKnot;
        knot_1.rKnot = knot_2;
        knot_2.hKnot = Math.max(getHeight(knot_2.lKnot), getHeight(knot_2.rKnot)) + 1;
        knot_1.hKnot = Math.max(getHeight(knot_1.lKnot), knot_2.hKnot) + 1;
        return knot_1;
    }

    // Single rotation ---> Corresponding imbalance: ---> The height of the right node of the right subtree is greater than the height of the left node of the right subtree
    private KnotOfTree<AnyType> rotateRightChild(KnotOfTree<AnyType> knot_2) {
        KnotOfTree<AnyType> knot_1 = knot_2.rKnot;
        knot_2.rKnot = knot_1.lKnot;
        knot_1.lKnot = knot_2;
        knot_2.hKnot = Math.max(getHeight(knot_2.rKnot), getHeight(knot_2.lKnot)) + 1;
        knot_1.hKnot = Math.max(getHeight(knot_1.rKnot), knot_2.hKnot) + 1;
        return knot_1;
    }

    // Double rotation ---> Corresponding imbalance: ---> Left subtree node height less than right subtree node height
    private KnotOfTree<AnyType> doubleRotateLeftChild(KnotOfTree<AnyType> knot_3) {
        knot_3.lKnot = rotateLeftChild(knot_3.lKnot);
        return rotateLeftChild(knot_3);
    }

    // Double rotation ---> Corresponding imbalance: ---> Height of right subtree node less than height of right subtree node
    private KnotOfTree<AnyType> doubleRotateRightChild(KnotOfTree<AnyType> knot_3) {
        knot_3.rKnot = rotateLeftChild(knot_3.rKnot);
        return rotateLeftChild(knot_3);
    }

    // Depending on the nature of the tree, the largest element in the tree is on the far right side of the tree
    private KnotOfTree<AnyType> getMax(KnotOfTree<AnyType> knot) {
        if (knot != null) {
            while (knot.rKnot != null) {
                knot = knot.rKnot;
            }
        }
        return knot;
    }

    // According to the nature of the search tree, the smallest element is on the left
    private KnotOfTree<AnyType> getMin(KnotOfTree<AnyType> knot) {
        if (knot == null) {
            return null;
        } else if (knot.lKnot == null) {
            return knot;
        }
        return getMin(knot.lKnot);
    }

    // we find the element x
    private AnyType getKnot(AnyType x, KnotOfTree<AnyType> knot) {
        if (knot == null) {
            return null;
        }
        int res  = x.compareTo(knot.t_key);
        if (res  < 0) {
            return getKnot(x, knot.lKnot);// Находим левое поддерево
        } else if (res  > 0) {
            return getKnot(x, knot.rKnot);// Находим правильное поддерево
        } else {
            return knot.t_value;
        }
    }




}
