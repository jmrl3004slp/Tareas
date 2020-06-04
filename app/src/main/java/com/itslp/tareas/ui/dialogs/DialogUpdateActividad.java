package com.itslp.tareas.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.itslp.tareas.R;

public class DialogUpdateActividad extends Dialog implements View.OnClickListener {
    private Activity activity;

    public DialogUpdateActividad(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_actividad);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
