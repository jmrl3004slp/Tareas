package com.itslp.tareas.ui.dialogs.works;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.TareasEntity;
import com.itslp.tareas.ui.activities.MainActivity;

public class DialogUpdateWork extends Dialog implements View.OnClickListener {
    public static int EXTRA_ID = 0;
    public static String EXTRA_NAME = "EXTRA_NAME";

    private EditText etDescripcion;
    private Button btnEditTarea;
    private Button btnCancelarEditTarea;

    private Activity activity;

    public DialogUpdateWork(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_tarea);

        etDescripcion = findViewById(R.id.etEditWork);
        etDescripcion.setText(EXTRA_NAME);

        btnEditTarea = findViewById(R.id.btn_edit_work);
        btnEditTarea.setOnClickListener(this);

        btnCancelarEditTarea = findViewById(R.id.btn_cancel_edit_work);
        btnCancelarEditTarea.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_work:
                EXTRA_NAME = etDescripcion.getText().toString().trim();
                if (etDescripcion.getText().toString().trim().isEmpty()) {
                    Toast.makeText(activity.getApplicationContext(), "La tarea no puede estar vacía", Toast.LENGTH_SHORT).show();
                    return;
                }

                TareasEntity note = new TareasEntity(EXTRA_NAME);

                note.setId(EXTRA_ID);
                MainActivity.tareasDialogViewModel.Update(note);
                break;
            case R.id.btn_cancel_edit_work:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
