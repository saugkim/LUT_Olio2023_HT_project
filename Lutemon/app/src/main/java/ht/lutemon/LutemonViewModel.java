package ht.lutemon;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LutemonViewModel extends AndroidViewModel {

    private final LiveData<List<Lutemon>> allLutemons;
    private final LiveData<List<Lutemon>> homeLutemons;
    private final LiveData<List<Lutemon>> trainLutemons;
    private final LiveData<List<Lutemon>> battleLutemons;

    LiveData<Integer> total_number;
    LutemonRepository repository;

    public LutemonViewModel(@NonNull Application application) {
        super(application);

        repository = new LutemonRepository(application);
        allLutemons = repository.getAllLutemons();
        total_number = repository.getCounts();
        homeLutemons = repository.homeLutemons;
        trainLutemons = repository.getTrainLutemons();
        battleLutemons = repository.getBattleLutemons();
    }

    public LiveData<Lutemon> getLutemonById(int id) {
        return repository.findById(id);
    }
    public LiveData<List<Lutemon>> getAllLutemons() {
        return allLutemons;
    }
    public LiveData<List<Lutemon>> getHomeLutemons() { return homeLutemons; }
    public LiveData<List<Lutemon>> getBattleLutemons() { return battleLutemons; }
    public LiveData<List<Lutemon>> getTrainLutemons() { return trainLutemons; }
}
