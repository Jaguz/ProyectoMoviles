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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Administrador extends AppCompatActivity {
    private static final String LOG_TAG = "";
    ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
    ListView proyectoLV;
    Button cargarBT;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        final TextView mensaje = (TextView) findViewById(R.id.nombreTV);
        proyectoLV =(ListView) findViewById(R.id.listViewProyecto);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        cargarBT = (Button) findViewById(R.id.proyectosBT);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            cargarBT.setEnabled(true);
        } else {
            cargarBT.setEnabled(false);
        }

        Bundle datos = getIntent().getExtras();

        try{
            mensaje.setText(datos.getString("username"));

        }catch(Exception e){
            Log.e(LOG_TAG, "Failed to display message", e);
        }

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1pWC4p-9M_yWUpg0iYTDgUADvHBfoPqG4rBlv6j3jXD8");

        final AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (Administrador.this, proyectoIn.class);
                Proyecto proyecto = adapter.getItem(position);
                intent.putExtra("lugar", proyecto.getLugar());
                intent.putExtra("nombre", proyecto.getNombre());
                startActivity(intent);
            }
        };
        proyectoLV.setOnItemClickListener(itemListener);
    }

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String nombre = columns.getJSONObject(1).getString("v");
                String lugar = columns.getJSONObject(2).getString("v");
                Proyecto proyecto = new Proyecto(nombre, lugar);
                proyectos.add(proyecto);
            }

            adapter = new ListViewAdapter(this, R.layout.row, proyectos);
            proyectoLV.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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



}
