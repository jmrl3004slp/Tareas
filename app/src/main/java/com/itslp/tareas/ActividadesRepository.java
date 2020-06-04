package com.itslp.tareas;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.itslp.tareas.db.TareaRoomDatabase;
import com.itslp.tareas.db.dao.ActividadesDao;
import com.itslp.tareas.db.entity.ActividadesEntity;

import java.util.List;

public class ActividadesRepository {
    private ActividadesDao actividadesDao;
    public List<ActividadesEntity> allActividades;

    public ActividadesRepository(Application application) {
        TareaRoomDatabase db = TareaRoomDatabase.getDataBase(application);
        actividadesDao            = db.actividadesDao();
        allActividades            = actividadesDao.retrieveList();
    }

    //La inserción puede tardar un poco y podría bloquearse la interface principal, por eso
    //ponemos éste método en un AsyncTask
    public void Create (ActividadesEntity actividadesEntity) {
        new ActividadesRepository.CreateAsyncTask(actividadesDao).execute(actividadesEntity);
    }//insert

    public List<ActividadesEntity> RetrieveList() {
        return allActividades;
    }//getAll

    public void Update(ActividadesEntity actividadesEntity) {
        new ActividadesRepository.UpdateAsyncTask(actividadesDao).execute(actividadesEntity);
    }

    public void Delete(ActividadesEntity actividadesEntity) {
        new ActividadesRepository.DeleteAsyncTask(actividadesDao).execute(actividadesEntity);
    }

    //=================================================================================
    private static class CreateAsyncTask extends AsyncTask<ActividadesEntity, Void, Void> {
        private ActividadesDao notaDaoAsyncTask;

        CreateAsyncTask(ActividadesDao dao){
            notaDaoAsyncTask = dao;
        }//Constructor

        @Override
        protected Void doInBackground(ActividadesEntity... notaEntities) {
            notaDaoAsyncTask.create(notaEntities[0]);
            return null;
        }//doInBackground
    }//insertAsyncTask

    private static class UpdateAsyncTask extends AsyncTask<ActividadesEntity, Void, Void> {
        private ActividadesDao notaDaoAsyncTask;

        UpdateAsyncTask (ActividadesDao dao){
            notaDaoAsyncTask = dao;
        }//Constructor

        @Override
        protected Void doInBackground(ActividadesEntity... notaEntities) {
            notaDaoAsyncTask.update(notaEntities[0]);
            return null;
        }//doInBackground
    }//DeleteAsyncTask

    private static class DeleteAsyncTask extends AsyncTask<ActividadesEntity, Void, Void> {
        private ActividadesDao notaDaoAsyncTask;

        DeleteAsyncTask (ActividadesDao dao){
            notaDaoAsyncTask = dao;
        }//Constructor

        @Override
        protected Void doInBackground(ActividadesEntity... notaEntities) {
            notaDaoAsyncTask.delete(notaEntities[0]);
            return null;
        }//doInBackground
    }//DeleteAsyncTask

}
