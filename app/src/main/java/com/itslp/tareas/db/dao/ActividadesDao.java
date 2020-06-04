package com.itslp.tareas.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.itslp.tareas.db.entity.ActividadesEntity;

import java.util.List;

@Dao
public interface ActividadesDao {
    @Insert
    void create(ActividadesEntity actividad);

    @Query("SELECT * FROM actividades")
    List<ActividadesEntity> retrieveList();

    @Update
    void update(ActividadesEntity actividad);

    @Delete
    void delete(ActividadesEntity actividad);
}