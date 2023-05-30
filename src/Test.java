import java.util.Random;
import ChainMetod.HashTab;
import OpenAddressing.OpenAddressingHashTable;
import AVL_Tree.AVL_Tree;
import Tree_Black_Red.TreeBlackRed;

public class Test {
    public static void main(String[] args) {
        String test_keys[] = {"Key_0_jjrl", "Key_1_uxxg", "Key_2_gutk", "Key_3_ncsq", "Key_4_tljb", "Key_5_xftc", "Key_6_bllu", "Key_7_ghtb", "Key_8_pmqb", "Key_9_nern"};
        String test_values[] = {"jxzw", "yzxt", "wfoe", "cidt", "fone", "spls", "lbio", "mgce", "yykm", "tgoj"};

        int test_val = 1000000;
        int test_circles = 100;
        long buf1 = 0;
        long buf2 = 0;
        long buf3 = 0;



        System.out.println("Test for Chaine method: ");
        for (int i = 1; i < test_circles; i ++) {
            HashTab hashTab = new HashTab(18);
            buf1 += InsertChain(test_val, hashTab);
            buf2 += RetrieveChain(test_val, hashTab);
            buf3 += RemoveChain(test_val, hashTab);
        }
        System.out.println("Time taken to add " + test_val + " entries: " + buf1/test_circles + "ms");
        System.out.println("Time taken to retrieve " + test_val + " entries: " + buf2/test_circles + "ms");
        System.out.println("Time taken to remove " + test_val + " entries: " + buf3/test_circles + "ms");

        buf1 = 0;
        buf2 = 0;
        buf3 = 0;

        System.out.println("\n Test for Open addressing method: ");
        for (int i = 1; i < test_circles; i ++) {
            OpenAddressingHashTable hashTab_1 = new OpenAddressingHashTable(18);
            buf1 += InsertOpenAdr(test_val, hashTab_1);
            buf2 += RetrieveOpenAdr(test_val, hashTab_1);
            buf3 += RemoveOpenAdr(test_val, hashTab_1);
        }
        System.out.println("Time taken to add " + test_val + " entries: " + buf1/test_circles + "ms");
        System.out.println("Time taken to retrieve " + test_val + " entries: " + buf2/test_circles + "ms");
        System.out.println("Time taken to remove " + test_val + " entries: " + buf3/test_circles + "ms");


        buf1 = 0;
        buf2 = 0;
        buf3 = 0;

        System.out.println("\n Test for AVL tree: ");
        for (int i = 1; i < test_circles; i ++) {
        AVL_Tree<String> tree = new AVL_Tree<String>();
            buf1 += InsertAVL(test_val, tree);
            buf2 += RetrieveAVL(test_val, tree);
            buf3 += RemoveAVL(test_val, tree);
        }
        System.out.println("Time taken to add " + test_val + " entries: " + buf1/test_circles + "ms");
        System.out.println("Time taken to retrieve " + test_val + " entries: " + buf2/test_circles + "ms");
        System.out.println("Time taken to remove " + test_val + " entries: " + buf3/test_circles + "ms");

        buf1 = 0;
        buf2 = 0;
        buf3 = 0;

        System.out.println("\n Test for Black-Red tree: ");
        for (int i = 1; i < test_circles; i ++) {
            TreeBlackRed<String> tree_1 = new TreeBlackRed<String>();
            buf1 += InsertBlackRed(test_val, tree_1);
            buf2 += RetrieveBlackRed(test_val, tree_1);
            buf3 += RemoveBlackRed(test_val, tree_1);
        }
        System.out.println("Time taken to add " + test_val + " entries: " + buf1/test_circles + "ms");
        System.out.println("Time taken to retrieve " + test_val + " entries: " + buf2/test_circles + "ms");
        System.out.println("Time taken to remove " + test_val + " entries: " + buf3/test_circles + "ms");



    }

    static long InsertChain ( int test_val, HashTab hashTab){
            //String random = generateRandomString;
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            String value = generateRandomString(random);

            hashTab.put(key, value);
        }
        long endTime = System.currentTimeMillis();
       // System.out.println("Time taken to add " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long RetrieveChain ( int test_val, HashTab hashTab){
        long startTime, endTime;
        Random random = new Random();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            String value = hashTab.get(key);
            //if (value != null) {
            //    System.out.println("Retrieved value for key " + key + ": " + value);
            //    }
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to retrieve " + test_val + " entries: " + (endTime - startTime) + "ms");

        return (endTime - startTime);
    }

    static long RemoveChain ( int test_val, HashTab hashTab) {
        long startTime, endTime;
        Random random = new Random();

        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            //String key = test_keys[i];
            hashTab.remove(key);
            //if (hashTab.remove(key) == 1) {
            //    System.out.println("Deleted value for key " + key);
            //}
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to remove " + test_val + " entries: " + (endTime - startTime) + "ms");

        return (endTime - startTime);
    }

    static long InsertOpenAdr ( int test_val, OpenAddressingHashTable hashTab){
        //String random = generateRandomString;
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            String value = generateRandomString(random);

            hashTab.put(key, value);
        }
        long endTime = System.currentTimeMillis();
        //System.out.println("Time taken to add " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long RetrieveOpenAdr ( int test_val, OpenAddressingHashTable hashTab){
        long startTime, endTime;
        Random random = new Random();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            String value = hashTab.get(key);
            //if (value != null) {
            //    System.out.println("Retrieved value for key " + key + ": " + value);
            //}
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to retrieve " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }
   // int test_val, OpenAddressingHashTable hashTab
    static long RemoveOpenAdr (int test_val,  OpenAddressingHashTable hashTab) {
        long startTime, endTime;
        Random random = new Random();

        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            //String key = test_keys[i];
            hashTab.remove(key);
           // if (hashTab.remove(key) == 1) {
            //  System.out.println("Deleted value for key " + key);
           // }
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to remove " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long InsertAVL ( int test_val, AVL_Tree tree){

        Random random = new Random();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            String value = generateRandomString(random);
            tree.insert(key, value);

        }
        long endTime = System.currentTimeMillis();
        //System.out.println("Time taken to add " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long RetrieveAVL ( int test_val, AVL_Tree tree){
        long startTime, endTime;
        Random random = new Random();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            tree.getKnot(key);
            //if (value != null) {
            //    System.out.println("Retrieved value for key " + key + ": " + value);
            //}
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to retrieve " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long RemoveAVL (int test_val,  AVL_Tree tree) {
        long startTime, endTime;
        Random random = new Random();

        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            //String key = test_keys[i];
            tree.remove(key);
            // if (tree.remove(key) == 1) {
            //  System.out.println("Deleted value for key " + key);
            // }
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to remove " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long InsertBlackRed ( int test_val, TreeBlackRed tree){

        Random random = new Random();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            String value = generateRandomString(random);
            tree.add(key, value);

        }
        long endTime = System.currentTimeMillis();
        //System.out.println("Time taken to add " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long RetrieveBlackRed ( int test_val, TreeBlackRed tree){
        long startTime, endTime;
        Random random = new Random();
        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            tree.getKnote(key);
            //if (value != null) {
            //    System.out.println("Retrieved value for key " + key + ": " + value);
            //}
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to retrieve " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }

    static long RemoveBlackRed (int test_val,  TreeBlackRed tree) {
        long startTime, endTime;
        Random random = new Random();

        startTime = System.currentTimeMillis();
        for (int i = 0; i < test_val; i++) {
            String key = "Key_" + i + "_" + generateRandomString(random);
            //String key = test_keys[i];
            tree.remove(key);
            // if (tree.remove(key) == 1) {
            //  System.out.println("Deleted value for key " + key);
            // }
        }
        endTime = System.currentTimeMillis();
        //System.out.println("Time taken to remove " + test_val + " entries: " + (endTime - startTime) + "ms");
        return (endTime - startTime);
    }
    // Helper method to generate a random string of length 10
    private static String generateRandomString (Random random){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append((char) (random.nextInt(26) + 'a'));
        }
        return sb.toString();
    }

}
