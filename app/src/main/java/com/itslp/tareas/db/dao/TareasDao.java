package com.itslp.tareas.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.itslp.tareas.db.entity.TareasEntity;

import java.util.List;

@Dao
public interface TareasDao {
    @Insert
    void create(TareasEntity tarea);

    @Query("SELECT * FROM tareas")
    LiveData<List<TareasEntity>> retrieveList();

    @Update
    void update(TareasEntity tarea);

    @Delete
    void delete(TareasEntity tarea);

    @Query("DELETE FROM tareas")
    void deleteAll();
}