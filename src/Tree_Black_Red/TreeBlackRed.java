package Tree_Black_Red;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class TreeBlackRed<AnyType extends Comparable<AnyType>> implements ITreeBlackRed<AnyType>, Iterable<AnyType>, Iterator<AnyType> {
// IRedBlackTree<AnyType>,
    /**
     * Перечисление цветов узла дерева.
     */
    enum KnotColor {
        RED, BLACK, NONE
    }

    /**
     * Класс реализующий узел дерева.
     */
    public class Knot {


        private AnyType key;
        private AnyType value;

        private TreeBlackRed.KnotColor colorKnot;
        /**
         * Родительский узел.
         */
        private Knot parentKnot;
        /**
         * Левый дочерниый узел.
         */
        private Knot lKnot;
        /**
         * Правый дочерний узел.
         */
        private Knot rKnot;

        /**
         * Конструктор по-умолчанию.
         */
        public Knot() {
            key = null;
            value = null;
            colorKnot = TreeBlackRed.KnotColor.NONE;
            parentKnot = null;
            lKnot = null;
            rKnot = null;
        }

        /**
         * Конструктор с параметрами, позволящими задать цвет и
         * значение узла.
         * key - ключ
         * value - значение, которое будет сохранено в узле.
         * color - цвет узла.
         */
        public Knot(AnyType in_key, AnyType val, TreeBlackRed.KnotColor color) {
            key = in_key;
            value = val;
            colorKnot = color;
            parentKnot = _nil;
            lKnot = _nil;
            rKnot = _nil;
        }

        /**
         * Конструктор копий.
         * knot - другой узел.
         */
        public Knot(Knot knot) {
            key = knot.key;
            value = knot.value;
            colorKnot = knot.colorKnot;
            parentKnot = knot.parentKnot;
            lKnot = knot.lKnot;
            rKnot = knot.rKnot;
        }

        public boolean isClear() {
            return key == null || key == _nil;
        }

        public boolean isLeftFree() {
            return lKnot == null || lKnot == _nil;
        }

        public boolean isRightFree() {
            return rKnot == null || rKnot == _nil;
        }

        public boolean isParentFree() {
            return parentKnot == null || parentKnot == _nil;
        }

        public AnyType getKey() {
            return key;
        }

        public void setKey(AnyType val) {
            key = val;
        }

        public Knot getParent() {
            return parentKnot;
        }

        public void setParent(Knot knot) {
            parentKnot = knot;
        }

        public Knot getLeft() {
            return lKnot;
        }

        public void setLeft(Knot knot) {
            lKnot = knot;
            if (lKnot != null && lKnot != _nil) lKnot.parentKnot = this;
        }

        public Knot getRight() {
            return rKnot;
        }

        public void setRight(Knot knot) {
            rKnot = knot;
            if (rKnot != null && rKnot != _nil) rKnot.parentKnot = this;
        }

        public boolean isBlack() {
            return colorKnot == TreeBlackRed.KnotColor.BLACK;
        }

        public void makeBlack() {
            colorKnot = TreeBlackRed.KnotColor.BLACK;
        }

        public boolean isRed() {
            return colorKnot == TreeBlackRed.KnotColor.RED;
        }

        public void makeRed() {
            colorKnot = TreeBlackRed.KnotColor.RED;
        }

        public TreeBlackRed.KnotColor getColor() {
            return colorKnot;
        }

        public void setColor(TreeBlackRed.KnotColor color) {
            colorKnot = color;
        }

        /**
         * Возвращет "дедушку" узла дерева.
         */
        public Knot getGrandfather() {
            if (parentKnot != null && parentKnot != _nil)
                return parentKnot.parentKnot;
            return null;
        }

        /**
         * Возвращает "дядю" узла дерева.
         */
        public Knot getUncle() {
            Knot grand = getGrandfather();
            if (grand != null) {
                if (grand.lKnot == parentKnot)
                    return grand.rKnot;
                else if (grand.rKnot == parentKnot)
                    return grand.lKnot;
            }
            return null;
        }

        /**
         * Возвращает следующий по значению узел дерева.
         */
        public Knot getSuccessor() {
            Knot temp = null;
            Knot knot = this;
            if (!knot.isRightFree()) {
                temp = knot.getRight();
                while (!temp.isLeftFree())
                    temp = temp.getLeft();
                return temp;
            }
            temp = knot.getParent();
            while (temp != _nil && knot == temp.getRight()) {
                knot = temp;
                temp = temp.getParent();
            }
            return temp;
        }

        public String getColorName() {
            return ((isBlack()) ? "B" : "R");
        }


    }
    /**
     * Корень дерева.
     */
    private Knot rootKnot;
    /**
     * Ограничитель, который обозначает нулевую ссылку.
     */
    private Knot _nil;

    /**
     * Ссылка на элемент на который указывает итератор.
     */
    private Knot _current;

    /**
     * Флаг удаления элемента через итератор, необходимый для того, чтобы
     * корректно работали hasNext() и next()
     */
    private boolean _isRemoved;

    /**
     * Конструктор по-умолчанию.
     */
    public TreeBlackRed() {
        rootKnot = new Knot();
        _nil = new Knot();
        _nil.colorKnot = KnotColor.BLACK;
        rootKnot.parentKnot = _nil;
        rootKnot.rKnot = _nil;
        rootKnot.lKnot = _nil;
        _isRemoved = false;
    }

    /**
     * Статический метод, осуществляюший левый поворот дерева tree относительно узла knot.
     * tree - дерево.
     * not - узел, относительно которого осущетвляется левый поворот.
     */
    private static <AnyType extends Comparable<AnyType>> void leftRotate(TreeBlackRed<AnyType> tree, TreeBlackRed<AnyType>.Knot knot) {
        TreeBlackRed<AnyType>.Knot knotParent = knot.getParent();
        TreeBlackRed<AnyType>.Knot knotRight = knot.getRight();
        if(knotParent != tree._nil) {
            if(knotParent.getLeft() == knot)
                knotParent.setLeft(knotRight);
            else
                knotParent.setRight(knotRight);
        }
        else {
            tree.rootKnot = knotRight;
            tree.rootKnot.setParent(tree._nil);
        }
        knot.setRight(knotRight.getLeft());
        knotRight.setLeft(knot);
    }

    /**
     * Статический метод, осуществляюший правый поворот дерева tree относительно узла knot.
     * tree - дерево.
     * knot - узел, относительно которого осущетвляется правый поворот.
     */
    private static <AnyType extends Comparable<AnyType>> void rightRotate(TreeBlackRed<AnyType> tree, TreeBlackRed<AnyType>.Knot knot) {
        TreeBlackRed<AnyType>.Knot knotParent = knot.getParent();
        TreeBlackRed<AnyType>.Knot knotLeft = knot.getLeft();
        if(knotParent != tree._nil) {
            if(knotParent.getLeft() == knot)
                knotParent.setLeft(knotLeft);
            else
                knotParent.setRight(knotLeft);
        }
        else {
            tree.rootKnot = knotLeft;
            tree.rootKnot.setParent(tree._nil);
        }
        knot.setLeft(knotLeft.getRight());
        knotLeft.setRight(knot);
    }

    /**
     * Печать дерева.
     * tree - дерево.
     */
    public static <AnyType extends Comparable<AnyType>> void printTree(TreeBlackRed<AnyType> tree) {
        ArrayList<TreeBlackRed<AnyType>.Knot> knots = new ArrayList<TreeBlackRed<AnyType>.Knot>();
        knots.add(0,  tree.rootKnot);
        printKnots(tree, knots);
    }

    /**
     * Печать информации об узле дерева.
     * tree - ссылка на дерево.
     * knots - список узлов на уровне дерева.
     */
    private static <AnyType extends Comparable<AnyType>> void printKnots(TreeBlackRed<AnyType> tree, ArrayList<TreeBlackRed<AnyType>.Knot> knots) {
        int childsCounter = 0;
        int KnotsAmount = knots.size();
        ArrayList<TreeBlackRed<AnyType>.Knot> childs = new ArrayList<TreeBlackRed<AnyType>.Knot>();

        for(int i = 0; i < KnotsAmount; i++) {
            if(knots.get(i) != null && knots.get(i) != tree._nil) {
                System.out.print("(" + knots.get(i).getKey().toString() + "," + knots.get(i).getColorName() + ")");
                if(!knots.get(i).isLeftFree()) {
                    childs.add(knots.get(i).getLeft());
                    childsCounter++;
                }
                else
                    childs.add(null);
                if(!knots.get(i).isRightFree()) {
                    childs.add(knots.get(i).getRight());
                    childsCounter++;
                }
                else
                    childs.add(null);
            }
            else {
                System.out.print("(nil)");
            }
        }
        System.out.print("\n");

        if(childsCounter != 0)
            printKnots(tree, childs);
    }

    /**
     * Реализация метода добавления элемента дарева. На основе добавляемого значения
     * создается узел дерева типа knot красного цвета.
     * o - значение типа Comparable для вставки в дерево.
     */
    @Override
    public void add(AnyType o, AnyType v) {
        Knot knot = rootKnot, temp = _nil;
        Knot newKnot = new Knot((AnyType)o, (AnyType)v, KnotColor.RED);
        while(knot != null && knot != _nil && !knot.isClear()) {
            temp = knot;
            if(newKnot.getKey().compareTo(knot.getKey()) < 0)
                knot = knot.getLeft();
            else
                knot = knot.getRight();
        }
        newKnot.setParent(temp);
        if(temp == _nil)
            rootKnot.setKey(newKnot.getKey());
        else {
            if(newKnot.getKey().compareTo(temp.getKey()) < 0)
                temp.setLeft(newKnot);
            else
                temp.setRight(newKnot);
        }
        newKnot.setLeft(_nil);
        newKnot.setRight(_nil);
        fixInsert(newKnot);
    }

    /**
     * Исправление древа для сохранения свойств красно-черного дерева.
     * knot - добавленный узел.
     */
    private void fixInsert(Knot knot) {
        Knot temp;
        while(!knot.isParentFree() && knot.getParent().isRed()) {
            if(knot.getParent() == knot.getGrandfather().getLeft()) {
                temp = knot.getGrandfather().getRight();
                if(temp.isRed()) {
                    temp.makeBlack();
                    knot.getParent().makeBlack();
                    knot.getGrandfather().makeRed();
                    knot = knot.getGrandfather();
                }
                else {
                    if(knot == knot.getParent().getRight()) {
                        knot = knot.getParent();
                        leftRotate(this, knot);
                    }
                    knot.getParent().makeBlack();
                    knot.getGrandfather().makeRed();
                    rightRotate(this, knot.getGrandfather());
                }
            }
            else {
                temp = knot.getGrandfather().getLeft();
                if(temp.isRed()) {
                    temp.makeBlack();
                    knot.getParent().makeBlack();
                    knot.getGrandfather().makeRed();
                    knot = knot.getGrandfather();
                }
                else {
                    if(knot == knot.getParent().getLeft()) {
                        knot = knot.getParent();
                        rightRotate(this, knot);
                    }
                    knot.getParent().makeBlack();
                    knot.getGrandfather().makeRed();
                    leftRotate(this, knot.getGrandfather());
                }
            }
        }
        rootKnot.makeBlack();
    }

    /**
     * Реализация удаления элемента дерева.
     * o - значение типа Comparable для удаления из дерева.
     * true - если элемент был удален;
     * false - если элемента в дереве нет и удаление его невозможно.
     */
    @Override
    public boolean remove(AnyType o) {
        return remove(findKnot(o));
    }

    //----------------------
    private boolean remove(Knot knot)
    {
        Knot temp = _nil, successor = _nil;

        if(knot == null || knot == _nil)
            return false;

        if(knot.isLeftFree() || knot.isRightFree())
            successor = knot;
        else
            successor = knot.getSuccessor();

        if(!successor.isLeftFree())
            temp = successor.getLeft();
        else
            temp = successor.getRight();
        temp.setParent(successor.getParent());

        if(successor.isParentFree())
            rootKnot = temp;
        else if(successor == successor.getParent().getLeft())
            successor.getParent().setLeft(temp);
        else
            successor.getParent().setRight(temp);

        if(successor != knot) {
            knot.setKey(successor.getKey());
        }
        if(successor.isBlack())
            fixRemove(temp);
        return true;
    }

    /**
     * Исправляет дерево после удаления элемента для сохранения
     * красно-черных свойств дерева.
     * knot - значение относительно которого необходимо производить
     * исправление дерева.
     */
    private void fixRemove(Knot knot)
    {
        Knot temp;
        while(knot != rootKnot && knot.isBlack()) {
            if(knot == knot.getParent().getLeft()) {
                temp = knot.getParent().getRight();
                if(temp.isRed()) {
                    temp.makeBlack();
                    knot.getParent().makeRed();
                    leftRotate(this, knot.getParent());
                    temp = knot.getParent().getRight();
                }
                if(temp.getLeft().isBlack() && temp.getRight().isBlack()) {
                    temp.makeRed();
                    knot = knot.getParent();
                }
                else {
                    if(temp.getRight().isBlack()) {
                        temp.getLeft().makeBlack();
                        temp.makeRed();
                        rightRotate(this, temp);
                        temp = knot.getParent().getRight();
                    }
                    temp.setColor(knot.getParent().getColor());
                    knot.getParent().makeBlack();
                    temp.getRight().makeBlack();
                    leftRotate(this, knot.getParent());
                    knot = rootKnot;
                }
            }
            else {
                temp = knot.getParent().getLeft();
                if(temp.isRed()) {
                    temp.makeBlack();
                    knot.getParent().makeRed();
                    rightRotate(this, knot.getParent());
                    temp = knot.getParent().getLeft();
                }
                if(temp.getLeft().isBlack() && temp.getRight().isBlack()) {
                    temp.makeRed();
                    knot = knot.getParent();
                }
                else {
                    if(temp.getLeft().isBlack()) {
                        temp.getRight().makeBlack();
                        temp.makeRed();
                        leftRotate(this, temp);
                        temp = knot.getParent().getLeft();
                    }
                    temp.setColor(knot.getParent().getColor());
                    knot.getParent().makeBlack();
                    temp.getLeft().makeBlack();
                    rightRotate(this, knot.getParent());
                    knot = rootKnot;
                }
            }
        }
        knot.makeBlack();
    }

    /**
     * Реализует функцию проверки на содержание элемента в дереве.
     * o - значение типа Comparable для поиска в дерева.
     * return true - если элемент найден; false - если элемент не найда.
     */
    @Override
    public AnyType getKnote(AnyType o) {
        AnyType buf;
        buf = findKnot(o).value;
        if ( buf != _nil){
            return buf;
        }
        return null;
    }

    /**
     * Поиск узла дерева со значением o.
     * o - значение типа  Comparable для поиска в дерева.
     * return узел дерева; если не найден - возвращает value _nil
     */
    private Knot findKnot(AnyType o) {
        Knot knot = rootKnot;
        while(knot != null && knot != _nil && knot.getKey().compareTo(o) != 0) {
            if(knot.getKey().compareTo(o) > 0)
                knot = knot.getLeft();
            else
                knot = knot.getRight();
        }
        return knot;
    }

    /**
     * Метод для получения первого(наименьшего) элемента структуры.
     * return наименьший элемент дерева
     */
    private Knot first()
    {
        Knot knot = rootKnot;
        while(knot.getLeft() != null && knot.getLeft() != _nil) {
            if(!knot.isLeftFree())
                knot = knot.getLeft();
        }
        return knot;
    }

    @Override
    public Iterator<AnyType> iterator() {
        _current = null;
        _isRemoved = false;
        return this;
    }

    @Override
    public boolean hasNext() {
        if(_current != null) {
            if(!_isRemoved) {
                TreeBlackRed<AnyType>.Knot Knot = _current.getSuccessor();
                return (Knot != null && Knot != _nil);
            }
            return (_current != null && _current != _nil);
        }
        else {
            return (rootKnot != null && !rootKnot.isClear());
        }
    }

    @Override
    public AnyType next() {
        if(_current != null) {
            if(!_isRemoved)
                _current = _current.getSuccessor();
            else
                _isRemoved = false;
        }
        else {
            _current = first();
        }
        if(_current == null || _current == _nil)
            throw new NoSuchElementException();
        return _current.getKey();
    }

    @Override
    public void remove() {
        if(_current != null && !_isRemoved) {
            remove(_current);
            _isRemoved = true;
        }
        else
            throw new IllegalStateException();
    }
}
