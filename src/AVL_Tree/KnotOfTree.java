package AVL_Tree;

// класс узла --- внутренний класс
public class KnotOfTree<AnyType> {
    AnyType t_key;
    AnyType t_value;
    KnotOfTree<AnyType> lKnot;
    KnotOfTree<AnyType> rKnot;
    int hKnot;// Высота узла

    public KnotOfTree(AnyType key, AnyType val) {
        this(key, val, null, null);
    }

    public KnotOfTree(AnyType key, AnyType val, KnotOfTree<AnyType> lKnot, KnotOfTree<AnyType> rKnot) {
        this.t_key = key;
        this.t_value = val;
        this.lKnot = lKnot;
        this.rKnot = rKnot;
        this.hKnot = 0;
    }

}