package itesm.mx.proyectomoviles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class asistenciaStaff extends AppCompatActivity {
    private static final String LOG_TAG = "";
    ArrayList<Alumnos> alumnos = new ArrayList<Alumnos>();
    ListView alumnosLV;
    Button alumnosButton;
    AlumnosAdapter alumnosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_staff);
        alumnosLV = (ListView) findViewById(R.id.listaAlumnos);
        alumnosButton = (Button) findViewById(R.id.alumnosBT);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            alumnosButton.setEnabled(true);
        } else {
            alumnosButton.setEnabled(false);
        }
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1GbTumbQeUZXbQ2nNiA2VxetiU5tsw1RSHHY2QL9KZ4E");

        final Bundle datos = getIntent().getExtras();
        final TextView nombre = (TextView) findViewById(R.id.nombreTV);
        nombre.setText(datos.getString("nombre"));

    }

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                String nombre = columns.getJSONObject(1).getString("v");
                Alumnos alumno = new Alumnos(nombre);
                alumnos.add(alumno);
            }

            alumnosAdapter = new AlumnosAdapter(this, R.layout.rowasistencia, alumnos);
            alumnosLV.setAdapter(alumnosAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = alumnosLV.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Alumnos alumno = alumnos.get(pos);
            alumno.setSelected(isChecked);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asistencia_staff, menu);
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
