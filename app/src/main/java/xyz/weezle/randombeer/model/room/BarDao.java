package xyz.weezle.randombeer.model.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BarDao {
    @Query("SELECT * FROM bar")
    List<Bar> getAll();

    @Query("SELECT * FROM bar ORDER BY id DESC LIMIT 1")
    Bar getLast();

    @Query("SELECT * FROM bar WHERE id = :id")
    Bar findById(int id);

    @Insert
    void insert(Bar bar);

    @Update
    void update(Bar bar);

    @Delete
    void delete(Bar bar);
}
