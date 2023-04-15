package ht.lutemon;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LutemonStorage {

    ArrayList<Lutemon> lutemons;
    private static LutemonStorage storage = null;

    private static String TAG = "ZZ lutemonStorage: ";
    protected LutemonStorage() {
        lutemons = new ArrayList<>();
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
    public void removeById (int id) {
        lutemons.remove(id);
    }

    public void removeAll() {
        lutemons.clear();
    }

    public void load(Context context) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(Helper.FILE_NAME));
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
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(context.openFileOutput(Helper.FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(LutemonStorage.getInstance().getLutemons());
            objectOutputStream.close();
            Log.d(TAG, "save data to file");
        } catch (IOException e) {
            Log.d(TAG, "save data to file failed");
            e.printStackTrace();
        }
    }
}