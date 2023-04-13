package ht.lutemon;

import java.util.ArrayList;

public class LutemonStorage {

    ArrayList<Lutemon> lutemons;
    private static LutemonStorage storage = null;
    final static String TAG = "ZZZ LutemonStorage";

    private LutemonStorage() {
        lutemons = new ArrayList<Lutemon>();
    }

    public static LutemonStorage getInstance() {
        if (storage == null) {
            storage = new LutemonStorage();
        }
        return storage;
    }

    public ArrayList<Lutemon> getLutemons() {
        return lutemons;
    }

    public void addLutemon(Lutemon item) {
        lutemons.add(item);
    }

    public void removeLutemon(Lutemon item) {
        lutemons.remove(item);
    }
}