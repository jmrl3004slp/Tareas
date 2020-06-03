package com.itslp.tareas.ui;

import android.content.Intent;
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
import com.itslp.tareas.db.entity.TareasEntity;
import com.itslp.tareas.ui.activities.EditTareaActivity;
import com.itslp.tareas.ui.dialogs.DialogInsertTarea;
import com.itslp.tareas.viewmodel.TareasDialogViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogInsertTarea.OnSimpleDialogListener {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private TareasDialogViewModel tareasDialogViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_Tarea);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogInsertTarea().show(getSupportFragmentManager(), "DialogoInserTarea");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.list_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MyTareaRecyclerViewAdapter adapter = new MyTareaRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        tareasDialogViewModel = ViewModelProviders.of(this).get(TareasDialogViewModel.class);
        tareasDialogViewModel.RetrieveList().observe(this, new Observer<List<TareasEntity>>() {
            @Override
            public void onChanged(@Nullable List<TareasEntity> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                tareasDialogViewModel.Delete(adapter.getTareaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Tarea Eliminada", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MyTareaRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TareasEntity tareasEntity) {
                Intent intent = new Intent(MainActivity.this, EditTareaActivity.class);
                intent.putExtra(EditTareaActivity.EXTRA_ID, tareasEntity.getId());
                intent.putExtra(EditTareaActivity.EXTRA_NAME, tareasEntity.getNombre());
                intent.putExtra(EditTareaActivity.EXTRA_DATE, tareasEntity.getFecha());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String nombre = data.getStringExtra(EditTareaActivity.EXTRA_NAME);
            String fecha = data.getStringExtra(EditTareaActivity.EXTRA_DATE);

            TareasEntity note = new TareasEntity(nombre, fecha);

            tareasDialogViewModel.Create(note);

            Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditTareaActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "La tarea no pudo ser actualizada", Toast.LENGTH_SHORT).show();
                return;
            }

            String nombre = data.getStringExtra(EditTareaActivity.EXTRA_NAME);
            String fecha = data.getStringExtra(EditTareaActivity.EXTRA_DATE);

            TareasEntity note = new TareasEntity(nombre, fecha);

            note.setId(id);
            tareasDialogViewModel.Update(note);

            Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tarea no guardada", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clickBotonOK() {
        new DialogInsertTarea().show(getSupportFragmentManager(), "DialogoInserTarea");
    }
}