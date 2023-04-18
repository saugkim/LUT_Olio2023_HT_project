package ht.lutemon;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lutemon.class}, version=1, exportSchema=false)
public abstract class LutemonDatabase extends RoomDatabase {

    public abstract LutemonDao dao();
    private static volatile LutemonDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 2;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LutemonDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized ( LutemonDatabase.class) {
                INSTANCE = Room
                        .databaseBuilder(context.getApplicationContext(), LutemonDatabase.class, "lutemonDB")
                        .addCallback(startRoomDatabaseCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback startRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                LutemonDao dao = INSTANCE.dao();
            });
        }
    };

    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }
}
