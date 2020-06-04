package com.itslp.tareas.ui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.itslp.tareas.R;
import com.itslp.tareas.db.TareaRoomDatabase;
import com.itslp.tareas.db.entity.ActividadesEntity;
import com.itslp.tareas.db.entity.TareasEntity;
import com.itslp.tareas.ui.adapters.MyActividadRecyclerViewAdapter;
import com.itslp.tareas.ui.adapters.MyTareaRecyclerViewAdapter;
import com.itslp.tareas.ui.dialogs.DialogInsertActividad;
import com.itslp.tareas.ui.dialogs.DialogUpdateActividad;
import com.itslp.tareas.viewmodel.ActividadesDialogViewModel;
import com.itslp.tareas.viewmodel.TareasDialogViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditTareaActivity extends AppCompatActivity implements DialogInsertActividad.OnSimpleDialogListener {
    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String EXTRA_DATE = "EXTRA_DATE";

    CharSequence items [];
    boolean marcados[];

    int _id;
    String _Fecha;

    ActividadesDialogViewModel actividadesDialogViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tarea);

        final EditText etDescripcion = findViewById(R.id.EditTarea2);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edición Tareas");
            etDescripcion.setText(intent.getStringExtra(EXTRA_NAME));
            _Fecha = intent.getStringExtra(EXTRA_DATE);
            _id = intent.getIntExtra(EXTRA_ID, -1);
        }

        Button btnEditTarea = findViewById(R.id.btn_edit_tarea);
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

        Button btnCancelar = findViewById(R.id.btn_cancel_tarea);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                DialogUpdateActividad customDialog = new DialogUpdateActividad(this);
                customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                customDialog.show();
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