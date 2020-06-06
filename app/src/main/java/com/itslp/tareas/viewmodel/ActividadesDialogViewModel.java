package com.itslp.tareas.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.itslp.tareas.repository.ActividadesRepository;
import com.itslp.tareas.db.entity.ActividadesEntity;

import java.util.List;

public class ActividadesDialogViewModel extends AndroidViewModel {
    private ActividadesRepository actividadesRepository;

    public ActividadesDialogViewModel(@NonNull Application application){
        super(application);

        actividadesRepository = new ActividadesRepository(application);
    }

    //El fragment que insert una nueva tarea deberá comunicarlo a este ViewModel
    public void Create(ActividadesEntity nuevaEntity){
        actividadesRepository.Create(nuevaEntity);
    }

    public LiveData<List<ActividadesEntity>> RetrieveList(int idTarea) {
        return actividadesRepository.RetrieveList(idTarea);
    }

    public void Update(ActividadesEntity tareasEntity) {
        actividadesRepository.Update(tareasEntity);
    }

    public void Update(int idTarea, String actividad, boolean terminado) {
        actividadesRepository.Update(idTarea, actividad, terminado);
    }

    public void Delete(ActividadesEntity eliminaEntity) {
        actividadesRepository.Delete(eliminaEntity);
    }
}