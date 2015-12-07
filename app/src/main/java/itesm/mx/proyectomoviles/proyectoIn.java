package itesm.mx.proyectomoviles;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class proyectoIn extends AppCompatActivity {

    private static final String LOG_TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto_in);

        final Bundle datos = getIntent().getExtras();
        final TextView nombre = (TextView) findViewById(R.id.nameTV);
        final Button asistenciaButton = (Button) findViewById(R.id.asistenciaBT);
        final Button monitoreoButton = (Button) findViewById(R.id.agregarBT);

        nombre.setText(datos.getString("username"));

        View.OnClickListener asistencia = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent intent = new Intent(proyectoIn.this, Administrador.class);
                    intent.putExtra("username", datos.getString("username"));
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(proyectoIn.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };

        View.OnClickListener reporte = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent intent = new Intent(proyectoIn.this, filtrosMonitoreo.class);
                    intent.putExtra("username", datos.getString("username"));
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(proyectoIn.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };
        asistenciaButton.setOnClickListener(asistencia);
        monitoreoButton.setOnClickListener(reporte);

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {

            }
        }, this).execute("https://spreadsheets.google.com/tq?key=1pWC4p-9M_yWUpg0iYTDgUADvHBfoPqG4rBlv6j3jXD8");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proyecto_in, menu);
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
