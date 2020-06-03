package com.itslp.tareas.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.itslp.tareas.db.dao.TareasDao;
import com.itslp.tareas.db.entity.TareasEntity;

@Database(entities = {TareasEntity.class}, version = 1)
public abstract class TareaRoomDatabase extends RoomDatabase {

    public abstract TareasDao tareasDao();
    public static volatile TareaRoomDatabase INSTANCIA;

    public static TareaRoomDatabase getDataBase(final Context context) {
        if(INSTANCIA == null) {
            synchronized (TareaRoomDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(), TareaRoomDatabase.class, "tareas_database").build();
                }//if
            }//synchronized
        }//if

        return INSTANCIA;
    }//getDataBase
}
