package ht.lutemon;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LutemonRepository {

    static String TAG = "ZZ LutemonRepository";
    private static final String SEPARATOR = "\\[";
    LutemonDao dao;

    LiveData<List<Lutemon>> lutemons;
    LiveData<List<Lutemon>> homeLutemons;
    LiveData<List<Lutemon>> trainLutemons;
    LiveData<List<Lutemon>> battleLutemons;

    LutemonRepository (Application application) {
        dao = LutemonDatabase.getInstance(application).dao();
        lutemons = dao.getAllLutemons();
        homeLutemons = dao.getArenaLutemons(Arena.HOME.name());
        trainLutemons = dao.getArenaLutemons(Arena.TRAIN.name());
        battleLutemons = dao.getArenaLutemons(Arena.BATTLE.name());
    }

    public LiveData<List<Lutemon>> getAllLutemons() {
        return lutemons;
    }
    public LiveData<List<Lutemon>> getHomeLutemons() {
        return homeLutemons;
    }
    public LiveData<List<Lutemon>> getTrainLutemons() {
        return trainLutemons;
    }
    public LiveData<List<Lutemon>> getBattleLutemons() {
        return battleLutemons;
    }

    public LiveData<List<Lutemon>> getSortedByColor() {
        return dao.getLutemonsSortedByColors();
    }
    public LiveData<List<Lutemon>> getSortedByXp() {
        return dao.getLutemonsSortedByXP();
    }

    public void insert(Lutemon lutemon) {
        LutemonDatabase.databaseWriteExecutor.execute(()-> {
            dao.insert(lutemon);
        });
    }

    public void update(Lutemon lutemon) {
        LutemonDatabase.databaseWriteExecutor.execute( ()-> {
            dao.update(lutemon);
        });
    }

    public void deleteAll() {
        LutemonDatabase.databaseWriteExecutor.execute(()-> dao.deleteAll());
    }

    public void updateArena(int id, String arena) {
        LutemonDatabase.databaseWriteExecutor.execute(() ->{
            dao.update(id, arena);
        });
    }

    public void updateXP(int id, int xp) {
        LutemonDatabase.databaseWriteExecutor.execute(() -> {
            dao.updateXp(id, xp);
        });
    }

    public void updateCurrentHealthToMax(int id, int hp) {
        LutemonDatabase.databaseWriteExecutor.execute(()-> {
            dao.updateHealth(id, hp);
        });
    }

    public void updateXpHp(int id, int xp, int hp) {
        LutemonDatabase.databaseWriteExecutor.execute(() -> {
            dao.updateXpHp(id, xp, hp);
        });
    }

    public void delete(Lutemon lutemon) {
        LutemonDatabase.databaseWriteExecutor.execute(()->dao.delete(lutemon));
    }
    public void deleteById(int id) {
        LutemonDatabase.databaseWriteExecutor.execute(() -> dao.deleteById(id));
    }

    public LiveData<Lutemon> findById(int id) {
        return dao.getLutemonById(id);
    }

    public LiveData<Integer> getCounts() {
        return dao.getCounts();
    }

    public Lutemon getCloned(String lutemonInfoString) {
        Lutemon cloned;
        if (lutemonInfoString == null) {
            return null;
        }

        String name = lutemonInfoString.split(SEPARATOR)[0].trim();
        String[] stats = lutemonInfoString.split(SEPARATOR)[1].split(" ");
        String team = stats[0];
        int xp = Integer.parseInt(stats[8]);
        int cHealth = Integer.parseInt(stats[6].split("/")[0]);

        switch (team) {
            case "White]":
                cloned = new White();
                break;
            case "Green]":
                cloned = new Green();
                break;
            case "Pink]":
                cloned = new Pink();
                break;
            case "Orange]":
                cloned = new Orange();
                break;
            case "Black]":
                cloned = new Black();
                break;
            default:
                cloned = new Lutemon();
                break;
        }
        cloned.setName(name);
        cloned.setXp(xp);
        cloned.setCurrentHealth(cHealth);
        return cloned;
    }
}
