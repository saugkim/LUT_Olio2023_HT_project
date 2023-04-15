package ht.lutemon;

public class BattleStorage extends LutemonStorage {
    private static BattleStorage storage = null;

    protected BattleStorage() {
        super();
    }

    public static BattleStorage getInstance() {
        if (storage == null) {
            storage = new BattleStorage();
        }
        return storage;
    }
}
