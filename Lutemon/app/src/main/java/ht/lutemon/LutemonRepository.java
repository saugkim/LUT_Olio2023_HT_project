package ht.lutemon;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LutemonRepository {

    static String TAG = "ZZ LutemonRepository";
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

    public void insert(Lutemon lutemon) {
        LutemonDatabase.databaseWriteExecutor.execute(()-> {
            dao.insert(lutemon);
//            int id = (int) dao.insert(lutemon);
//            lutemon.setId(id);
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
}
