package ht.lutemon;

public class TrainStorage extends LutemonStorage {
    private static TrainStorage storage = null;

    protected TrainStorage() {
        super();
    }

    public static TrainStorage getInstance() {
        if (storage == null) {
            storage = new TrainStorage();
        }
        return storage;
    }
}
