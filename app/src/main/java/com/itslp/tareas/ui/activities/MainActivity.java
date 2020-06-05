package com.itslp.tareas.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.itslp.tareas.ui.adapters.MyTareaRecyclerViewAdapter;
import com.itslp.tareas.ui.dialogs.works.DialogAddWork;
import com.itslp.tareas.viewmodel.ActividadesDialogViewModel;
import com.itslp.tareas.viewmodel.TareasDialogViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogAddWork.OnSimpleDialogListener {
    public static TareasDialogViewModel tareasDialogViewModel;
    public static ActividadesDialogViewModel actividadesDialogViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_Tarea);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogAddWork().show(getSupportFragmentManager(), "DialogoInserTarea");
            }
        });

        RecyclerView recyclerView = findViewById(R.id.list_tareas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MyTareaRecyclerViewAdapter adapter = new MyTareaRecyclerViewAdapter(this, MainActivity.this, getSupportFragmentManager());
        recyclerView.setAdapter(adapter);

        tareasDialogViewModel = ViewModelProviders.of(this).get(TareasDialogViewModel.class);
        tareasDialogViewModel.RetrieveList().observe(this, new Observer<List<TareasEntity>>() {
            @Override
            public void onChanged(@Nullable List<TareasEntity> notes) {
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
                tareasDialogViewModel.Delete(adapter.getTareaAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Tarea Eliminada", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                tareasDialogViewModel.DeleteAll();
                Toast.makeText(this, "Todas las tareas eliminadas", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void clickBotonOK() {
        new DialogAddWork().show(getSupportFragmentManager(), "DialogoInserTarea");
    }
}