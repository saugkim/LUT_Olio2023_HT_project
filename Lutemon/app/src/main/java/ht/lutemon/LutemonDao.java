package ht.lutemon;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LutemonDao {

//    @Query("SELECT * FROM LutemonDB")
//    LiveData<List<Lutemon>> getAll();
//
//    @Query("SELECT COUNT(id) FROM Kukat")
//    LiveData<Integer> getCounts();
//
//    @Query("SELECT COUNT(id) FROM Kukat")
//    int getCount();
//
//    @Query("DELETE FROM Kukat")
//    void deleteAll();
//
//    @Delete
//    void delete(Lutemon lutemon);
//
//    @Query("DELETE FROM Kukat WHERE id = :id")
//    void deleteById(long id);
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    long insert(Lutemon flower);
//
//    @Update
//    void update(Lutemon lutemon);
}
