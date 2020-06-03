package com.itslp.tareas;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.itslp.tareas.db.TareaRoomDatabase;
import com.itslp.tareas.db.dao.TareasDao;
import com.itslp.tareas.db.entity.TareasEntity;

import java.util.List;

public class TareasRepository {
    private TareasDao tareasDao;
    public LiveData<List<TareasEntity>> allTareas;

    public TareasRepository(Application application) {
        TareaRoomDatabase db = TareaRoomDatabase.getDataBase(application);
        tareasDao            = db.tareasDao();
        allTareas            = tareasDao.retrieveList();
    }

    //La inserción puede tardar un poco y podría bloquearse la interface principal, por eso
    //ponemos éste método en un AsyncTask
    public void Create (TareasEntity tarea) {
        new InsertAsyncTask(tareasDao).execute(tarea);
    }//insert

    public LiveData<List<TareasEntity>> RetrieveList() { return allTareas; }//getAll

    public void Update(TareasEntity tareasEntity) {
        new UpdateAsyncTask(tareasDao).execute(tareasEntity);
    }

    public void Delete(TareasEntity tarea) {
        new DeleteAsyncTask(tareasDao).execute(tarea);
    }

    //=================================================================================
    private static class InsertAsyncTask extends AsyncTask<TareasEntity, Void, Void> {
        private TareasDao notaDaoAsyncTask;

        InsertAsyncTask (TareasDao dao){
            notaDaoAsyncTask = dao;
        }//Constructor

        @Override
        protected Void doInBackground(TareasEntity... notaEntities) {
            notaDaoAsyncTask.create(notaEntities[0]);
            return null;
        }//doInBackground
    }//insertAsyncTask

    private static class UpdateAsyncTask extends AsyncTask<TareasEntity, Void, Void> {
        private TareasDao notaDaoAsyncTask;

        UpdateAsyncTask (TareasDao dao){
            notaDaoAsyncTask = dao;
        }//Constructor

        @Override
        protected Void doInBackground(TareasEntity... notaEntities) {
            notaDaoAsyncTask.update(notaEntities[0]);
            return null;
        }//doInBackground
    }//DeleteAsyncTask

    private static class DeleteAsyncTask extends AsyncTask<TareasEntity, Void, Void> {
        private TareasDao notaDaoAsyncTask;

        DeleteAsyncTask (TareasDao dao){
            notaDaoAsyncTask = dao;
        }//Constructor

        @Override
        protected Void doInBackground(TareasEntity... notaEntities) {
            notaDaoAsyncTask.delete(notaEntities[0]);
            return null;
        }//doInBackground
    }//DeleteAsyncTask
}