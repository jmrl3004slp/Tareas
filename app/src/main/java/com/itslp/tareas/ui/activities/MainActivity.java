package com.itslp.tareas.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.ActividadesEntity;
import com.itslp.tareas.db.entity.TareasEntity;
import com.itslp.tareas.ui.adapters.MyActividadRecyclerViewAdapter;
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

        final MyTareaRecyclerViewAdapter adapter = new MyTareaRecyclerViewAdapter(this, MainActivity.this);
        recyclerView.setAdapter(adapter);

        actividadesDialogViewModel = ViewModelProviders.of(MainActivity.this).get(ActividadesDialogViewModel.class);

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

        adapter.setOnItemClickListener(new MyTareaRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TareasEntity tareasEntity) {
                final int idTarea = tareasEntity.getId();
                final MyActividadRecyclerViewAdapter recyclerViewAdapter = new MyActividadRecyclerViewAdapter();

                Log.i("MainActivity", "ID WORK: " + String.valueOf(idTarea));

                actividadesDialogViewModel.RetrieveList(idTarea).observe(MainActivity.this, new Observer<List<ActividadesEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<ActividadesEntity> actividadesEntitiesList) {
                        final int length = actividadesEntitiesList.size();
                        final CharSequence items[] = new CharSequence[length];
                        final boolean marcados[] = new boolean[length];
                        final int idItems[] = new int[length];

                        for(int i = 0; i < length; i ++) {
                            try {
                                items[i] = actividadesEntitiesList.get(i).getActividad();
                                marcados[i] = actividadesEntitiesList.get(i).isTerminada();
                                idItems[i] = actividadesEntitiesList.get(i).getId();

                                Log.i("TAREAS_ACTIVIADES", actividadesEntitiesList.get(i).getActividad());
                            } catch (Exception e) {
                                Log.e("MainActivity", e.getMessage());
                            }
                        }

                        Log.i("MainActivity","Se obtuvo arreglo");

                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Selecciona las actividades");
                        builder.setMultiChoiceItems(items, marcados, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                if (isChecked) {
                                    marcados[i] = isChecked;
                                }//if
                                else {
                                    marcados[i] = false;
                                }//else

                                Log.i("MainActivity","Selecciono: " + String.valueOf(items[i]));

                                ActividadesEntity entityUpdate = new ActividadesEntity(idTarea, String.valueOf(items[i]), isChecked);
                                entityUpdate.setId(idItems[i]);
                                actividadesDialogViewModel.Update(entityUpdate);
                            }
                        });
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this,"Actividades actualizadas", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        builder.setNeutralButton("Más Opciones", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(MainActivity.this, ListActivitiesActivity.class);
                                intent.putExtra("EXTRA_ID_WORK", idTarea);
                                startActivity(intent);
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        recyclerViewAdapter.submitList(actividadesEntitiesList);
                    }
                });
            }
        });
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