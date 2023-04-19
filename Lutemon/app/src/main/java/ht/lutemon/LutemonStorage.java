package ht.lutemon;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class LutemonStorage {

    ArrayList<Lutemon> lutemons;
    ArrayList<Lutemon> lutemonsInBattles;
    ArrayList<Lutemon> lutemonsInTrain;
    ArrayList<Lutemon> lutemonsAtHome;
    private static LutemonStorage storage = null;

    private static final String FILE_NAME = "lut_ht_lutemon.data";
    private static String TAG = "ZZ lutemonStorage: ";
    protected LutemonStorage() {
        lutemons = new ArrayList<>();
        lutemonsInBattles = new ArrayList<>();
        lutemonsInTrain = new ArrayList<>();
        lutemonsAtHome = new ArrayList<>();
    }

    public static LutemonStorage getInstance() {
        if (storage == null) {
            storage = new LutemonStorage();
        }
        return storage;
    }
    public ArrayList<Lutemon> getLutemonsAtHome() {
        return lutemonsAtHome;
    }

    public ArrayList<Lutemon> getLutemonsInBattles() {
        return lutemonsInBattles;
    }
    public ArrayList<Lutemon> getLutemonsInTrain() {
        return lutemonsInTrain;
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
    public void removeById (int id) {
        lutemons.remove(id);
    }

    public void removeAll() {
        lutemons.clear();
    }

    public void update(int index, Lutemon lutemon) {
        lutemons.set(index, lutemon);
    }

    public void load(Context context) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(FILE_NAME));
            //noinspection unchecked
            lutemons = (ArrayList<Lutemon>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Loading data from file failed: file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "Loading data from file failed: IO exception");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "Loading from file failed: User class not found");
            e.printStackTrace();
        }
        Log.d(TAG, "data loaded");
//        loadAll();
    }
    public void save(Context context) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(LutemonStorage.getInstance().getLutemons());
            objectOutputStream.close();
            Log.d(TAG, "save data to file");
        } catch (IOException e) {
            Log.d(TAG, "save data to file failed");
            e.printStackTrace();
        }
    }


    public void loadAll() {
        for (Lutemon lutemon : LutemonStorage.getInstance().getLutemons()) {
            if (lutemon.getArena().equals(Arena.HOME.name())) {
                lutemonsAtHome.add(lutemon);
            } else if (lutemon.getArena().equals(Arena.TRAIN.name())) {
                lutemonsInTrain.add(lutemon);
            } else {
                lutemonsInBattles.add(lutemon);
            }
        }
    }

    public int getMaxID() {
        ArrayList<Lutemon> lutemons = LutemonStorage.getInstance().getLutemons();

        if (lutemons.size() == 0) {
            return 0;
        }
        int maxId = -1;
        for (int i = 0; i < lutemons.size(); i++) {
            if (lutemons.get(i).getId() > maxId) {
                maxId = lutemons.get(i).getId();
            }
        }
        Log.d(TAG, "max id? " + maxId);
        return maxId;
    }
}