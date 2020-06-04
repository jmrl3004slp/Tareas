package com.itslp.tareas.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "actividades")
public class ActividadesEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idTarea;
    private String actividad;
    private boolean terminada;

    public ActividadesEntity(int idTarea, String actividad, boolean terminada) {
        this.idTarea = idTarea;
        this.actividad = actividad;
        this.terminada = terminada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividades) {
        this.actividad = actividades;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }
}