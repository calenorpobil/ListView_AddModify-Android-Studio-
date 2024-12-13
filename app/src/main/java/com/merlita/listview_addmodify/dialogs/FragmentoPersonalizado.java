package com.merlita.listview_addmodify.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.merlita.listview_addmodify.R;

import java.util.Objects;

public class FragmentoPersonalizado extends DialogFragment {

    MensajeItem mensajeItem;
    int numLista;
    EditText nombre, nota;
    private String mensajeEntrada, viejoNombre;
    private int viejaNota;

    @Override
    //Envia los datos a la principal
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mensajeItem = (MensajeItem) getActivity();
    }

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Posible error: poner getActivity()
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_nuevo_item,null);
        nombre = dialogView.findViewById(R.id.username);
        nota = dialogView.findViewById(R.id.nota);

        Bundle bundle = getArguments();
        assert bundle != null;
        mensajeEntrada = bundle.getString("mensajeInput");
        numLista = bundle.getInt("numLista");
        viejoNombre = bundle.getString("nombre");
        viejaNota = bundle.getInt("nota");
        nombre.setText(viejoNombre);
        nota.setText(viejaNota+"");

        ventana.setView(dialogView)
                .setMessage(mensajeEntrada)
                .setPositiveButton(R.string.crear, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String[] mensaje = new String[2];
                        if(nombre !=null)
                        {
                            mensaje[0] = nombre.getText().toString();
                            mensaje[1] = nota.getText().toString();
                        }
                        mensajeItem.mensajeItem(mensaje, numLista);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Objects.requireNonNull(FragmentoPersonalizado.this.getDialog()).cancel();
                    }
                });
        return  ventana.create();
    }

    //ENVIAR EL EDITTEXT AL MAINACTIVITY:
    public interface MensajeItem
    {
        void mensajeItem(String[] mensaje, int i);
    }
}
