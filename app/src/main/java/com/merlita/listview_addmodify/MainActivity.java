package com.merlita.listview_addmodify;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.merlita.*;
import com.merlita.listview_addmodify.classes.Titular;
import com.merlita.listview_addmodify.dialogs.FragmentoPersonalizado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity
{
    TextView tv;
    ListView lv;
    AdaptadorTitulares adaptadorTitulares;
    Button btAlta, btSalir;


    private final Titular[] datos =
            new Titular[]{
                    new Titular("Sin ítems", "Añade un ítem desde el menú")};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.listView);
        adaptadorTitulares = new AdaptadorTitulares(this, datos);
        btAlta = findViewById(R.id.btAlta);
        btSalir = findViewById(R.id.btSalir);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Alternativa 1:
                /*String opcionSeleccionada =
                        ((Titular)adapterView.getItemAtPosition(i)).getTitle();
                tv.setText(opcionSeleccionada);*/

                //Alternativa 2:
                String opcionSeleccionada =
                        ((TextView)view.findViewById(R.id.tvTitulo))
                                .getText().toString();
                tv.setText(opcionSeleccionada);
            }
        });

        btAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentoPersonalizado f2b = new FragmentoPersonalizado();
                Bundle bundle = new Bundle();
                bundle.putString("mensajeInput", "Introduce el nombre del alumno: ");
                f2b.show(getSupportFragmentManager(),"xxx");
            }
        });


        lv.setAdapter(adaptadorTitulares);
        registerForContextMenu(lv);
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

    class AdaptadorTitulares extends ArrayAdapter<Titular> {
        Titular[] datos;
        public AdaptadorTitulares(Context context, Titular[] datos) {
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
                item.setTag(contenedor);
            }else{
                contenedor = (ViewHolder)item.getTag();
            }

            contenedor.title.setText(datos[position].getTitle());
            contenedor.subtitle.setText(datos[position].getSubtitle());

            return(item);
        }


        class ViewHolder{
            TextView title, subtitle;
        }
    }

}
