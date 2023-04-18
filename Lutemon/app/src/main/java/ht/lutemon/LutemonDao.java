package ht.lutemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LutemonDao {

    @Query("SELECT * FROM lutemons")
    LiveData<List<Lutemon>> getAllLutemons();
    @Query("SELECT * FROM lutemons WHERE arena = :arena_query ")
    LiveData<List<Lutemon>> getArenaLutemons(String arena_query);

    @Query("SELECT COUNT(id) FROM lutemons")
    LiveData<Integer> getCounts();

    @Query("SELECT COUNT(id) FROM Lutemons")
    int getCount();

    @Query("DELETE FROM lutemons")
    void deleteAll();

    @Delete
    void delete(Lutemon lutemon);

    @Query("DELETE FROM lutemons WHERE id = :id")
    void deleteById(long id);

    @Insert
    void insert(Lutemon lutemon);

    @Query("UPDATE lutemons SET arena = :arena WHERE id = :id")
    void update(int id, String arena);

    @Query("UPDATE lutemons SET xp = :newXP WHERE id = :id")
    void updateXp(int id, int newXP);

    @Query("UPDATE lutemons SET xp = :newXp, currentHealth = :newCurrentHealth WHERE id = :id")
    void updateXpHp(int id, int newXp, int newCurrentHealth);

    @Update
    void update(Lutemon lutemon);
    @Query("SELECT * FROM lutemons WHERE id = :id")
    LiveData<Lutemon> getLutemonById(int id);
}
