package com.itslp.tareas.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.ActividadesEntity;
import com.itslp.tareas.viewmodel.ActividadesDialogViewModel;

public class DialogInsertActividad extends DialogFragment {
    public static int EXTRA_ID = 0;

    private DialogInsertActividad.OnSimpleDialogListener listener;
    public interface OnSimpleDialogListener {
        void clickBotonOK();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflar y establecer el layout para el dialogo
        builder.setView(inflater.inflate(R.layout.dialog_insert_activitidad, null))
                .setTitle("Añadir actividad " + String.valueOf(EXTRA_ID))
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText etnombre = getDialog().findViewById(R.id.EditActividad);

                        String actividad = etnombre.getText().toString();
                        if (!actividad.equals("")) {
                            ActividadesDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(ActividadesDialogViewModel.class);
                            mViewModel.Create(new ActividadesEntity(EXTRA_ID, actividad, false));

                            Toast.makeText(getContext(), "Actividad guardada", Toast.LENGTH_SHORT).show();

                            dialogInterface.dismiss();
                        }
                        else {
                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setTitle("AVISO");
                            builder1.setMessage("La actividad no puede estar vacía");
                            builder1.setCancelable(false);
                            builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    listener.clickBotonOK();
                                }
                            });

                            AlertDialog alertDialog = builder1.create();
                            alertDialog.show();
                        }
                    }
                })

                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (DialogInsertActividad.OnSimpleDialogListener) context;
    }
}
