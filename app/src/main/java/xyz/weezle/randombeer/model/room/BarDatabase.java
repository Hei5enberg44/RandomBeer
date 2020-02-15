package xyz.weezle.randombeer.model.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Bar.class}, version = 2, exportSchema = false)
public abstract class BarDatabase extends RoomDatabase {
    private static final String DB_NAME = "bar.db";
    private static BarDatabase instance;

    public static synchronized BarDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, BarDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract BarDao barDao();
}
