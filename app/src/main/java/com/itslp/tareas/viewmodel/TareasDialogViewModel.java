package com.itslp.tareas.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.itslp.tareas.TareasRepository;
import com.itslp.tareas.db.entity.TareasEntity;

import java.util.List;

public class TareasDialogViewModel extends AndroidViewModel {
    private LiveData<List<TareasEntity>> allTareas;
    private TareasRepository tareasRepository;

    public TareasDialogViewModel(Application application){
        super(application);

        tareasRepository = new TareasRepository(application);
        allTareas = tareasRepository.RetrieveList();
    } //NuevaNotaDialogViewModel

    //El fragment que insert una nueva tarea deberá comunicarlo a este ViewModel
    public void Create(TareasEntity nuevaEntity){
        tareasRepository.Create(nuevaEntity);
    }

    public LiveData<List<TareasEntity>> RetrieveList() {
        return allTareas;
    }

    public void Update(TareasEntity tareasEntity) {
        tareasRepository.Update(tareasEntity);
    }

    public void Delete(TareasEntity eliminaEntity) {
        tareasRepository.Delete(eliminaEntity);
    }
}