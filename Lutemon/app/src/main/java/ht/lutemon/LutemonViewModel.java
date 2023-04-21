package ht.lutemon;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LutemonViewModel extends AndroidViewModel {

    private final LiveData<List<Lutemon>> allLutemons;

    LiveData<Integer> total_number;
    LutemonRepository repository;

    public LutemonViewModel(@NonNull Application application) {
        super(application);

        repository = new LutemonRepository(application);
        allLutemons = repository.getAllLutemons();
        total_number = repository.getCounts();
    }

    public LiveData<Lutemon> getLutemonById(int id) {
        return repository.findById(id);
    }
    public LiveData<List<Lutemon>> getAllLutemons() {
        return allLutemons;
    }
    public LiveData<List<Lutemon>> getHomeLutemons() { return repository.getHomeLutemons(); }
    public LiveData<List<Lutemon>> getBattleLutemons() { return repository.getBattleLutemons(); }
    public LiveData<List<Lutemon>> getTrainLutemons() { return repository.getTrainLutemons(); }

    public LiveData<List<Lutemon>> getSortedByColor() {
        return repository.getSortedByColor();
    }
    public LiveData<List<Lutemon>> getSortedByXP() {
        return repository.getSortedByXp();
    }
}
