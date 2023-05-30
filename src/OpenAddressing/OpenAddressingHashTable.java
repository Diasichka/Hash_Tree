package OpenAddressing;


public class OpenAddressingHashTable {

    private int capacity;
    private int size;
    private String[] keys;
    private String[] values;
    private final double loadFactor = 0.7;

    public OpenAddressingHashTable(int capacity) {
        this.capacity = capacity;
        this.keys = new String[capacity];
        this.values = new String[capacity];
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(String key) {
        int index = getIndex(key);
        return keys[index] != null && keys[index].equals(key);
    }

    public String get(String key) {
        int index = getIndex(key);

        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                return values[index];
            }
            index = (index + 1) % capacity;
        }
        return null;
    }

    public void put(String key, String value) {
        if ((double) size / capacity >= loadFactor) {
            resize(capacity * 2);
        }

        int index = getIndex(key);
        while (keys[index] != null && !keys[index].equals(key)) {
            index = (index + 1) % capacity;
        }

        if (keys[index] == null) {
            size++;
        }

        keys[index] = key;
        values[index] = value;
    }

    public int remove(String key) {
        int index = getIndex(key);
        int res = 0;

        while (keys[index] != null) {
            if (keys[index].equals(key)) {
                //String value = values[index];
                keys[index] = null;
                values[index] = null;
                size--;
                res = 1;
                if ((double) size / capacity <= 1 - loadFactor && capacity > 16) {
                    resize(capacity / 2);
                }
                return res;
            }
            index = (index + 1) % capacity;
        }
        return res;
    }

    private int getIndex(String key) {
        int hash = key.hashCode() & 0x7fffffff;
        return hash % capacity;
    }

    private void resize(int newCapacity) {
        OpenAddressingHashTable newHashTable = new OpenAddressingHashTable(newCapacity);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                newHashTable.put(keys[i], values[i]);
            }
        }
        keys = newHashTable.keys;
        values = newHashTable.values;
        capacity = newCapacity;
    }
}

