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

    @Query("SELECT * FROM actividades WHERE idTarea = :idTarea")
    LiveData<List<ActividadesEntity>> retrieveList(int idTarea);

    @Update
    void update(ActividadesEntity actividad);

    @Delete
    void delete(ActividadesEntity actividad);

    @Query("UPDATE actividades SET terminada = :terminado WHERE idTarea = :idTarea AND actividad = :actividad")
    void update(int idTarea, String actividad, boolean terminado);
}