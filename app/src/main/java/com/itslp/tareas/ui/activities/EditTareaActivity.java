package com.itslp.tareas.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.CharSequenceTransformation;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.ActividadesEntity;
import com.itslp.tareas.ui.adapters.MyActividadRecyclerViewAdapter;
import com.itslp.tareas.ui.dialogs.DialogInsertActividad;
import com.itslp.tareas.viewmodel.ActividadesDialogViewModel;

import java.util.List;

public class EditTareaActivity extends AppCompatActivity implements DialogInsertActividad.OnSimpleDialogListener {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_DATE = "EXTRA_DATE";

    private int _id;
    private String _Fecha;

    private EditText etDescripcion;
    private Button btnEditTarea;
    private Button btnCancelar;
    private RecyclerView recyclerView;

    private ActividadesDialogViewModel actividadesDialogViewModel;
    private CharSequence [] items;
    private boolean[] marcados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tarea);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        final MyActividadRecyclerViewAdapter adapter = new MyActividadRecyclerViewAdapter();

        etDescripcion = findViewById(R.id.EditTarea2);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edición Tareas");
            etDescripcion.setText(intent.getStringExtra(EXTRA_NAME));
            _Fecha = intent.getStringExtra(EXTRA_DATE);
            _id = intent.getIntExtra(EXTRA_ID, -1);
        }

        btnEditTarea = findViewById(R.id.btn_edit_tarea);
        btnEditTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etDescripcion.getText().toString().trim();
                if (etDescripcion.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "La tarea no puede estar vacía", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_NAME, nombre);
                data.putExtra(EXTRA_DATE, _Fecha);

                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1) {
                    data.putExtra(EXTRA_ID, id);
                }
                setResult(RESULT_OK, data);
                finish();
            }
        });

        btnCancelar = findViewById(R.id.btn_cancel_tarea);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.list_actividades2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        actividadesDialogViewModel = ViewModelProviders.of(this).get(ActividadesDialogViewModel.class);
        actividadesDialogViewModel.RetrieveList(_id).observe(this, new Observer<List<ActividadesEntity>>() {
            @Override
            public void onChanged(@Nullable List<ActividadesEntity> actividadesEntitiesList) {
                int length = actividadesEntitiesList.size();
                items = new CharSequence[length];
                marcados = new boolean[length];

                for(int i = 0; i < length; i ++) {
                    items[i] = actividadesEntitiesList.get(i).getActividad();
                    Log.i("TAREAS_ACTIVIADES", actividadesEntitiesList.get(i).getActividad());
                }
                adapter.submitList(actividadesEntitiesList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tareas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_add_actividad:
                DialogInsertActividad dialog = new DialogInsertActividad();
                dialog.EXTRA_ID = _id;
                dialog.show(getSupportFragmentManager(), "DialogInsertActividad");
                return true;
            case R.id.btn_update_actividad:
                Log.i("ACTIVIDAD_UPDATE", "SE HIZO CLICK");

                AlertDialog.Builder  builder = new AlertDialog.Builder(EditTareaActivity.this);
                builder.setTitle("Selecciona las actividades");
                builder.setMultiChoiceItems(items, marcados, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if(isChecked){
                            marcados[i] = isChecked;
                        }//if
                        else {
                            marcados[i] = false;
                        }//else
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void clickBotonOK() {
        DialogInsertActividad dialog = new DialogInsertActividad();
        dialog.EXTRA_ID = _id;
        dialog.show(getSupportFragmentManager(), "DialogInsertActividad");
    }
}