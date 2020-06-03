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
                adapter.setTareas(notes);
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
                Toast.makeText(getApplicationContext(), tareasEntity.getId(), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void clickBotonOK() {
        new DialogInsertTarea().show(getSupportFragmentManager(), "DialogoInserTarea");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            new DialogInsertTarea().show(getSupportFragmentManager(), "DialogoInserTarea");
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}