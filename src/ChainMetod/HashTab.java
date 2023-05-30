package ChainMetod;

public class HashTab {
    //private final static int TABLE_SIZE = ;
    public int table_size;
    EntHash[] table;
    private final double loadFactor = 0.7;
    private int el_count;

    // A hash function to generate an index value for the given key.
    public HashTab(int t_size) {
        this.table_size = t_size;
        this.table = new EntHash[this.table_size];
        for ( int i = 0; i < this.table_size; i++)//
            this.table[i] = null;

        this.el_count=0;
    }


    // A hash function to generate an index value for the given key.
    public int getHash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % table_size;
        }
        return hash;
    }

    // A method to retrieve the value for a given key from the hash table.
    public String get(String key) {
        int hash = getHash(key);
        if (table[hash] == null)
            return null;

        // If there is an entry present, iterate through the linked list until the end or until the node with the matching key is found
        EntHash entry = table[hash];
        while (entry != null && !entry.getKey().equals(key))
            entry = entry.getNext();

        // If the node with the matching key is not found, return null
        if (entry == null)
            return null;
        else
            return entry.getValue();
    }

    // A method to insert a key-value pair into the hash table.
    public void put(String key, String value) {
        if ((double) el_count / table_size >= loadFactor) {
            resize(table_size * 2);
        }
        int hash = getHash(key);

        if (table[hash] == null) {
            // If there is no entry present, create a new entry with the given key and value and add it to the hash table
            table[hash] = new EntHash(key, value);
            el_count++;
        } else {
            // If there is an entry present, iterate through the linked list until the end or until the node with the matching key is found
            EntHash entry = table[hash];

            while (entry.getNext() != null && !entry.getKey().equals(key)) {
                entry = entry.getNext();
            }

            // If the node with the matching key is found, update its value
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
            } else {
                // Otherwise, add a new node with the given key and value to the end of the linked list
                entry.setNext(new EntHash(key, value));
                el_count++;
            }
        }
    }

    // A method to remove a key-value pair from the hash table.
    public int remove(String key) {
        if ((double) el_count / table_size >= loadFactor) {
            resize(table_size * 2);
        }
        int hash = getHash(key);
        int res = 0;

        if (table[hash] != null) {
            EntHash prevEntry = null;
            EntHash entry = table[hash];

            // Iterate through the linked list until the end or until the node with the matching key is found
            while (entry.getNext() != null && !entry.getKey().equals(key)) {
                prevEntry = entry;
                entry = entry.getNext();
            }


            // If the node with the matching key is found, remove it from the list
            if (entry.getKey().equals(key)) {
                el_count--;
                res = 1;
                if (prevEntry == null)
                    table[hash] = entry.getNext();
                else
                    prevEntry.setNext(entry.getNext());

            }

        }
        return res;
    }
    private void resize(int newCapacity) {
        HashTab newHashTable = new HashTab(newCapacity);
        for (int i = 0; i < table_size; i++) {
            if (table[i] != null) {
                EntHash entry = table[i];
                while (entry != null ) {
                    newHashTable.put(entry.getKey(), entry.getValue());
                    entry = entry.getNext();
                }
            }
        }
        table = newHashTable.table;
        table_size= newCapacity;
    }
}