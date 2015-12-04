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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Staff extends AppCompatActivity {

    private static final String LOG_TAG = "";
    ArrayList<Proyecto> proyectos = new ArrayList<Proyecto>();
    ListView proyectoLV;
    Button cargarBT;
    ListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        proyectoLV =(ListView) findViewById(R.id.ListaStaff);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        final TextView nombreUsuario = (TextView) findViewById(R.id.nombreTV);
        final Bundle datos = getIntent().getExtras();
        nombreUsuario.setText(datos.getString("username"));
        Toast.makeText(this, "Cargando", Toast.LENGTH_SHORT).show();
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1pWC4p-9M_yWUpg0iYTDgUADvHBfoPqG4rBlv6j3jXD8");
        Toast.makeText(this, "Finalizado", Toast.LENGTH_SHORT).show();
        final AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isOnline()) {
                    Intent intent = new Intent(Staff.this, proyectoInStaff.class);
                    Proyecto proyecto = adapter.getItem(position);
                    intent.putExtra("espacio", proyecto.getEspacio());
                    intent.putExtra("proyecto", proyecto.getProyecto());
                    intent.putExtra("incubadora", proyecto.getIncubadora());
                    intent.putExtra("username", nombreUsuario.getText());
                    startActivity(intent);
                }
                else
                    Toast.makeText(Staff.this, "No hay conexión a internet.", Toast.LENGTH_LONG).show();
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
                String proyect = columns.getJSONObject(3).getString("v");
                Proyecto proyecto = new Proyecto(nombre, lugar, proyect);
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
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


}
