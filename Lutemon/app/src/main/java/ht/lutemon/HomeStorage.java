package ht.lutemon;

import java.util.ArrayList;

public class HomeStorage extends LutemonStorage {

    private static HomeStorage storage = null;
    protected HomeStorage() {
        super();
    }

    public static HomeStorage getInstance() {
        if (storage == null) {
            storage = new HomeStorage();
        }
        return storage;
    }
}
