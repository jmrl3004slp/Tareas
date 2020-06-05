package com.itslp.tareas.ui.dialogs.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.ActividadesEntity;
import com.itslp.tareas.db.entity.TareasEntity;
import com.itslp.tareas.ui.activities.MainActivity;

public class DialogAddActividad extends Dialog implements View.OnClickListener {
    public static int EXTRA_ID_WORK = 0;
    private static String EXTRA_NAME = "EXTRA_NAME";

    private Activity activity;
    private EditText etDescripcion;
    private Button btnAddActividad;
    private Button btnCancelarAddActividad;

    public DialogAddActividad(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_tarea);

        etDescripcion = findViewById(R.id.etAddActivity);

        btnAddActividad = findViewById(R.id.btn_add_activity);
        btnAddActividad.setOnClickListener(this);

        btnCancelarAddActividad = findViewById(R.id.btn_cancel_add_activity);
        btnCancelarAddActividad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_activity:
                EXTRA_NAME = etDescripcion.getText().toString().trim();
                if (etDescripcion.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity.getApplicationContext(), "La tarea no puede estar vacía", Toast.LENGTH_SHORT).show();
                    return;
                }

                MainActivity.actividadesDialogViewModel.Create(new ActividadesEntity(EXTRA_ID_WORK, EXTRA_NAME, false));
                break;
            case R.id.btn_cancel_add_activity:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();

    }
}