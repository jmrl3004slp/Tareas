package com.itslp.tareas.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.itslp.tareas.R;
import com.itslp.tareas.db.entity.TareasEntity;
import com.itslp.tareas.viewmodel.TareasDialogViewModel;

import java.security.PublicKey;
import java.util.Date;

public class DialogEditTarea extends DialogFragment {
    public static final int EXTRA_ID = 0;
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE = "EXTRA_DATE";

    DialogInsertTarea.OnSimpleDialogListener listener;
    public interface OnSimpleDialogListener {
        void clickBotonOK();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflar y establecer el layout para el dialogo
        builder.setView(inflater.inflate(R.layout.dialog_insert_tarea, null))
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText etnombre = getDialog().findViewById(R.id.EditTarea);

                        String tarea = etnombre.getText().toString();
                        if (!tarea.equals("")) {
                            TareasDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(TareasDialogViewModel.class);
                            mViewModel.Update(new TareasEntity(tarea, new Date().toString()));

                            Toast.makeText(getContext(), "Tarea actualizada", Toast.LENGTH_SHORT).show();

                            dialogInterface.dismiss();
                        }
                        else {
                            //Toast.makeText(getContext(), "Error: PASSWORD INCORRECTO", Toast.LENGTH_LONG);
                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setTitle("AVISO");
                            builder1.setMessage("La tarea no puede estar vacía");
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
                        getActivity().finish();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (DialogInsertTarea.OnSimpleDialogListener) context;
    }
}
