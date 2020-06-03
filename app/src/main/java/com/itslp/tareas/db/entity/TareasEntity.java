package com.itslp.tareas.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tareas")
public class TareasEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String nombre;
    private String fecha;

    public TareasEntity(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
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

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha){
        this.fecha = fecha;
    }
}
