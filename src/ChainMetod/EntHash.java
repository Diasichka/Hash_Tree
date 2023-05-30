package ChainMetod;

// This is a class representing an entry in the hash table, consisting of a key, value, and a reference to the next entry
// This class is used to create a linked list of entries that share the same hash code in the hash table

public class EntHash {

    public String key;
    private String value;
    private EntHash next;

    // Constructor that initializes the key, value, and next entry
    EntHash(String key, String value) {
        this.key = key;
        this.value = value;
        this.next = null;

    }

    // Getter method to retrieve the value associated with the entry
    public String getValue() {
        return value;

    }

    // Setter method to set the value associated with the entry
    public void setValue(String value) {
        this.value = value;

    }


    // Getter method to retrieve the key associated with the entry
    public String getKey() {
        return key;

    }


    public EntHash getNext() {
        return next;

    }

    public void setNext(EntHash next) {
        this.next = next;

    }

}



