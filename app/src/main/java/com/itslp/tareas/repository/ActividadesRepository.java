package com.itslp.tareas.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.itslp.tareas.db.TareaRoomDatabase;
import com.itslp.tareas.db.dao.ActividadesDao;
import com.itslp.tareas.db.entity.ActividadesEntity;

import java.util.List;

public class ActividadesRepository {
    private ActividadesDao actividadesDao;

    public ActividadesRepository(Application application) {
        TareaRoomDatabase db = TareaRoomDatabase.getDataBase(application);
        actividadesDao            = db.actividadesDao();
    }

    //La inserción puede tardar un poco y podría bloquearse la interface principal, por eso
    //ponemos éste método en un AsyncTask
    public void Create (ActividadesEntity actividadesEntity) {
        new ActividadesRepository.CreateAsyncTask(actividadesDao).execute(actividadesEntity);
    }//insert

    public LiveData<List<ActividadesEntity>> RetrieveList(int idTarea) {
        return actividadesDao.retrieveList(idTarea);
    }//getAll

    public void Update(ActividadesEntity actividadesEntity) {
        new ActividadesRepository.UpdateAsyncTask(actividadesDao).execute(actividadesEntity);
    }

    public void Update(int idTarea, String actividad, boolean terminado) {
        new ActividadesRepository.UpdateAsyncTask(actividadesDao).execute(new ActividadesEntity(idTarea, actividad, terminado));
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

    private static class UpdateAnsynTask extends AsyncTask<ActividadesEntity, Void, Void> {
        private ActividadesDao actividadesAsyncTask;

        UpdateAnsynTask(ActividadesDao dao) {
            this.actividadesAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(ActividadesEntity... actividadesEntities) {
            int idTarea = actividadesEntities[0].getIdTarea();
            String actividad = actividadesEntities[0].getActividad();
            boolean terminado = actividadesEntities[0].isTerminada();

            actividadesAsyncTask.update(idTarea, actividad, terminado);
            return null;
        }
    }

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
