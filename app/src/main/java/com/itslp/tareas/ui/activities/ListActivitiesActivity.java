package com.itslp.tareas.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.ActividadesEntity;
import com.itslp.tareas.ui.adapters.MyActividadRecyclerViewAdapter;
import com.itslp.tareas.ui.dialogs.activity.DialogAddActividad;
import com.itslp.tareas.viewmodel.ActividadesDialogViewModel;

import java.util.List;

public class ListActivitiesActivity extends AppCompatActivity {
    private ActividadesDialogViewModel actividadesDialogViewModel;
    private int idTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activities);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        final MyActividadRecyclerViewAdapter adapter = new MyActividadRecyclerViewAdapter(this, ListActivitiesActivity.this);

        idTarea = getIntent().getIntExtra("EXTRA_ID_WORK", -1);

        FloatingActionButton btnAddListForActivity = findViewById(R.id.btn_add_activity_for_list);
        btnAddListForActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddActividad dialog = new DialogAddActividad(ListActivitiesActivity.this);
                dialog.EXTRA_ID_WORK = idTarea;
                dialog.show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.list_activities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        actividadesDialogViewModel = ViewModelProviders.of(this).get(ActividadesDialogViewModel.class);
        actividadesDialogViewModel.RetrieveList(idTarea).observe(this, new Observer<List<ActividadesEntity>>() {
            @Override
            public void onChanged(@Nullable List<ActividadesEntity> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                actividadesDialogViewModel.Delete(adapter.getTareaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ListActivitiesActivity.this, "Actividad Eliminada", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
