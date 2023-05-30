package Tree_Black_Red;

/**
 * Коллекция хранит данные в структуре Red-black tree и гарантирует
 * логарифмическое время вставки, удаления и поиска.
 */
public interface ITreeBlackRed<AnyType extends Comparable<AnyType>> {

    /**
     * Добавить элемент в дерево
     * @param o
     */
    void add(AnyType o, AnyType v);

    /**
     * Удалить элемент из дерева
     * @param o
     */
    boolean remove(AnyType o);

    /**
     * Возвращает true, если элемент содержится в дереве
     * @param o
     */
    AnyType getKnote(AnyType o);
}
