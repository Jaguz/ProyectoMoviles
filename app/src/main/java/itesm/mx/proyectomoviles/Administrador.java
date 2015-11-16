package itesm.mx.proyectomoviles;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends AppCompatActivity {
    private static final String LOG_TAG = "";
    ArrayList<Proyecto> teams = new ArrayList<Proyecto>();
    ListView proyectoLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        final TextView mensaje = (TextView) findViewById(R.id.nombreTV);
        proyectoLV =(ListView) findViewById(R.id.listViewProyecto);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        Bundle datos = getIntent().getExtras();

        try{
            mensaje.setText(datos.getString("username"));

        }catch(Exception e){
            Log.e(LOG_TAG, "Failed to display message", e);
        }

        final ListViewAdapter miAdaptador = new ListViewAdapter(getApplicationContext(),R.layout.row,getDataForListView());
        proyectoLV.setAdapter(miAdaptador);

        final AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (Administrador.this, proyectoIn.class);
                Proyecto proyecto= (Proyecto) miAdaptador.getItem(position);
                Bundle bundleAct = new Bundle();
                intent.putExtra("lugar", proyecto.getLugar());
                intent.putExtra("nombre", proyecto.getNombre());
                startActivity(intent);
            }
        };
        proyectoLV.setOnItemClickListener(itemListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_administrador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Proyecto> getDataForListView(){
        Proyecto proyecto;
        List<Proyecto> listproyectos = new ArrayList<Proyecto>();
        proyecto = new Proyecto("Diverciencia", "Caracol");
        listproyectos.add(proyecto);

        return listproyectos;



    }
}
