package com.itslp.tareas.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.itslp.tareas.ActividadesRepository;
import com.itslp.tareas.db.entity.ActividadesEntity;

import java.util.List;

public class ActividadesDialogViewModel extends AndroidViewModel {
    private List<ActividadesEntity> allActividadess;
    private ActividadesRepository actividadesRepository;

    public ActividadesDialogViewModel(@NonNull Application application){
        super(application);

        actividadesRepository = new ActividadesRepository(application);
        allActividadess = actividadesRepository.RetrieveList();
    }

    //El fragment que insert una nueva tarea deberá comunicarlo a este ViewModel
    public void Create(ActividadesEntity nuevaEntity){
        actividadesRepository.Create(nuevaEntity);
    }

    public List<ActividadesEntity> RetrieveList() {
        return allActividadess;
    }

    public void Update(ActividadesEntity tareasEntity) {
        actividadesRepository.Update(tareasEntity);
    }

    public void Delete(ActividadesEntity eliminaEntity) {
        actividadesRepository.Delete(eliminaEntity);
    }

}
