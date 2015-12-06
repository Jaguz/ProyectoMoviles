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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class filtrosMonitoreo extends AppCompatActivity {
    private static final String LOG_TAG = "";
    List<String> proyectos = new ArrayList<String>();
    List<String> espacios = new ArrayList<String>();
    List<String> incubadoras = new ArrayList<String>();
    ArrayAdapter<String> proyAdapter;
    ArrayAdapter<String> espAdapter;
    ArrayAdapter<String> incAdapter;
    Button cuantiBT;
    Button cualiBT;
    Context context;
    Spinner incSpin;
    Spinner espSpin;
    Spinner proySpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_monitoreo);
        context = this;
        TextView nameTV = (TextView) findViewById(R.id.nameTV);
        cuantiBT = (Button) findViewById(R.id.proyectosBT);
        cualiBT = (Button) findViewById(R.id.cualiBT);
        incSpin = (Spinner) findViewById(R.id.spinInc);
        espSpin = (Spinner) findViewById(R.id.spinEsp);
        proySpin = (Spinner) findViewById(R.id.spinProy);

        final Bundle datos = getIntent().getExtras();
        nameTV.setText(datos.getString("username"));

        proyectos.add("-");
        espacios.add("-");
        incubadoras.add("-");

        final View.OnClickListener verCuanti = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    String incubadora = " ";
                    String espacio = " ";
                    String proyecto = " ";
                    incubadora = incSpin.getSelectedItem().toString();
                    espacio = espSpin.getSelectedItem().toString();
                    proyecto = proySpin.getSelectedItem().toString();
                    Intent intent = new Intent(filtrosMonitoreo.this, Cuanti.class);
                    intent.putExtra("username", datos.getString("username"));
                    intent.putExtra("incubadora", incubadora);
                    intent.putExtra("espacio", espacio);
                    intent.putExtra("proyecto", proyecto);
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(filtrosMonitoreo.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };

        final View.OnClickListener verCuali = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    String incubadora = " ";
                    String espacio = " ";
                    String proyecto = " ";
                    incubadora = incSpin.getSelectedItem().toString();
                    espacio = espSpin.getSelectedItem().toString();
                    proyecto = proySpin.getSelectedItem().toString();
                    Intent intent = new Intent(filtrosMonitoreo.this, Cuali.class);
                    intent.putExtra("username", datos.getString("username"));
                    intent.putExtra("incubadora", incubadora);
                    intent.putExtra("espacio", espacio);
                    intent.putExtra("proyecto", proyecto);
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(filtrosMonitoreo.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
                cuantiBT.setOnClickListener(verCuanti);
                cualiBT.setOnClickListener(verCuali);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1pWC4p-9M_yWUpg0iYTDgUADvHBfoPqG4rBlv6j3jXD8");




    }

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String inc = columns.getJSONObject(1).getString("v");
                String esp = columns.getJSONObject(2).getString("v");
                String proyect = columns.getJSONObject(3).getString("v");
                boolean flag = false;
                for(int i=0; i<proyectos.size(); i++){
                    if(proyectos.get(i).equals(proyect)) flag = true;
                }
                if(!flag)proyectos.add(proyect);
                flag = false;
                for(int i=0; i<espacios.size(); i++){
                    if(espacios.get(i).equals(esp)) flag = true;
                }
                if(!flag)espacios.add(esp);
                flag = false;
                for(int i=0; i<incubadoras.size(); i++){
                    if(incubadoras.get(i).equals(inc)) flag = true;
                }
                if(!flag)incubadoras.add(inc);
            }

            incAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, incubadoras);
            incAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            incSpin.setAdapter(incAdapter);
            espAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, espacios);
            espAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            espSpin.setAdapter(espAdapter);
            proyAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, proyectos);
            proyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            proySpin.setAdapter(proyAdapter);

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
