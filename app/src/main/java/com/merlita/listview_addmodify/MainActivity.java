package com.merlita.listview_addmodify;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.merlita.listview_addmodify.classes.Titular;
import com.merlita.listview_addmodify.dialogs.FragmentoPersonalizado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements FragmentoPersonalizado.MensajeItem {
    TextView tv;
    ListView lv;
    AdaptadorTitulares adaptadorTitulares;
    Button btAlta, btSalir;
    Titular auxiliar = new Titular(
            "Sin ítems",
            "-1");


    private final ArrayList<Titular> datos =
            new ArrayList<>();
    private final ArrayList<Titular> datosVacio =
            new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        datosVacio.add(auxiliar);

        lv = findViewById(R.id.listView);
        tv = findViewById(R.id.tvTituloMain);
        btAlta = findViewById(R.id.btAlta);
        btSalir = findViewById(R.id.btSalir);


        btAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoInput("Introduce el nombre del alumno: ", "", "", -1);
            }
        });

        hacerListaVacia();
        registerForContextMenu(lv);
    }

    private void dialogoInput(String titulo, String nombre, String nota, int i) {
        FragmentoPersonalizado f2b = new FragmentoPersonalizado();
        Bundle bundle = new Bundle();
        bundle.putString("mensajeInput", titulo);
        bundle.putString("nombre", nombre);
        bundle.putString("nota", nota);
        bundle.putInt("numLista", i);
        f2b.setArguments(bundle);
        f2b.show(getSupportFragmentManager(),"xxx");
    }

    private void hacerLista() {
        adaptadorTitulares = new AdaptadorTitulares(this, datos);
        lv.setAdapter(adaptadorTitulares);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String nombre =((TextView)view.findViewById(R.id.tvTitulo))
                        .getText().toString();
                String nota = ((TextView)view.findViewById(R.id.tvSubTitulo))
                        .getText().toString();
                String opcionSeleccionada = "Nuevo nombre para "+nombre+": ";
                dialogoInput(opcionSeleccionada, nombre, nota, i);


            }
        });
    }
    private void hacerListaVacia() {
        adaptadorTitulares = new AdaptadorTitulares(this, datosVacio);
        lv.setAdapter(adaptadorTitulares);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflador = getMenuInflater();

        AdapterView.AdapterContextMenuInfo informacionItem =
                (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(lv.getAdapter().getItem(informacionItem.position).toString());
        inflador.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id1 = R.id.item1, id2 = R.id.item2;
        String opcion;
        AdapterView.AdapterContextMenuInfo informacionItem =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        assert informacionItem != null;
        opcion = informacionItem.position+1+"\t";
        tv.setText(opcion);
        if(item.getItemId()==id1){
            tv.append(" Opcion 1 pulsada!");
            return true;
        }else if (item.getItemId()==id2){
            tv.append(" Opcion 2 pulsada!");
            return true;
        }else{
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void mensajeItem(String[] mensaje, int i) {
        if(datos.size()==0){
            hacerListaVacia();
        }else{
            hacerLista();
        }
        if(i==-1){
            datos.add(new Titular(mensaje[0], mensaje[1]));
        }else{
            datos.set(i, new Titular(mensaje[0], mensaje[1]));
        }
        hacerLista();
    }

    class AdaptadorTitulares extends ArrayAdapter<Titular> {
        ArrayList<Titular> datos;
        public AdaptadorTitulares(Context context, ArrayList<Titular> datos) {
            super(context, R.layout.layout, datos);
            this.datos = datos;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            ViewHolder contenedor;

            if(item == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                item = inflater.inflate(R.layout.layout, null);
                contenedor = new ViewHolder();
                contenedor.title =(TextView)item.findViewById(R.id.tvTitulo);
                contenedor.subtitle = (TextView)item.findViewById(R.id.tvSubTitulo);
                contenedor.img = (ImageView)item.findViewById(R.id.imagen);
                item.setTag(contenedor);
            }else{
                contenedor = (ViewHolder)item.getTag();
            }

            contenedor.title.setText(datos.get(position).getTitle());
            contenedor.subtitle.setText(datos.get(position).getNota());
            if(datos.get(position).getRojo()){
                contenedor.img.setImageResource(R.drawable.no);
                contenedor.subtitle.setTextColor(Color.parseColor("Red"));
            }else{
                contenedor.img.setImageResource(R.drawable.ok);
                contenedor.subtitle.setTextColor(Color.parseColor("Green"));
            }

            return(item);
        }


        class ViewHolder{
            TextView title, subtitle;
            ImageView img;
        }
    }

}
