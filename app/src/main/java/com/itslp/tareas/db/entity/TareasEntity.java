package com.itslp.tareas.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tareas")
public class TareasEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;

    public TareasEntity(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String _nombre){
        this.nombre = _nombre;
    }
}