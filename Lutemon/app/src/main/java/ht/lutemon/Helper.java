package ht.lutemon;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Helper {

    public static String FILE_NAME = "lut_ht_lutemon.data";
    private static String TAG = "ZZ Helper";

    public static int getMaxID() {
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

    public static void save(Context context) {
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

    public static void loadAll() {
        for (Lutemon lutemon : LutemonStorage.getInstance().getLutemons()) {
            if (lutemon.getArena().equals(Arena.HOME.name())) {
                HomeStorage.getInstance().addLutemon(lutemon);
            } else if (lutemon.getArena().equals(Arena.TRAIN.name())) {
                TrainStorage.getInstance().addLutemon(lutemon);
            } else {
                BattleStorage.getInstance().addLutemon(lutemon);
            }
        }
    }

    public static void load(Context context) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(FILE_NAME));
            //noinspection unchecked
            LutemonStorage.getInstance().lutemons =
                    (ArrayList<Lutemon>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Loading data failed: file not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "Loading data failed: IO exception");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "Loading data failed: Lutemon class not found");
            e.printStackTrace();
        }
        Log.d(TAG, "data loaded");
        loadAll();
    }
}