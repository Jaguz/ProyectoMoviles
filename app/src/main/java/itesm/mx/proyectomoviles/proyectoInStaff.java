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

public class proyectoInStaff extends AppCompatActivity {
    private static final String LOG_TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto_in_staff);
        final Bundle datos = getIntent().getExtras();

        final TextView nombreUsuario = (TextView) findViewById(R.id.nombreTV);
        final Button monitoreoButton = (Button) findViewById(R.id.monitoreoBT);
        final Button asistenciaButton = (Button) findViewById(R.id.asistenciaBT);
        final Button alumnosBtn = (Button) findViewById(R.id.alumnosBtn);
        final TextView nombre = (TextView) findViewById(R.id.nameTV);
        final TextView lugar = (TextView) findViewById(R.id.espacioTV);
        final TextView incubadora = (TextView) findViewById(R.id.layoutID);

        nombre.setText(datos.getString("proyecto"));
        lugar.setText(datos.getString("espacio"));
        incubadora.setText(datos.getString("incubadora"));
        nombreUsuario.setText(datos.getString("username"));

        View.OnClickListener encuesta = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Intent intent = new Intent(proyectoInStaff.this, encuesta.class);
                    intent.putExtra("proyecto", datos.getString("proyecto"));
                    intent.putExtra("espacio", datos.getString("espacio"));
                    intent.putExtra("incubadora", datos.getString("incubadora"));
                    intent.putExtra("username", nombreUsuario.getText());
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(proyectoInStaff.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };

        View.OnClickListener asistencia = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent intent = new Intent(proyectoInStaff.this, asistenciaStaff.class);
                    intent.putExtra("proyecto", datos.getString("proyecto"));
                    intent.putExtra("espacio", datos.getString("espacio"));
                    intent.putExtra("incubadora", datos.getString("incubadora"));
                    intent.putExtra("username", nombreUsuario.getText());
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(proyectoInStaff.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };
        asistenciaButton.setOnClickListener(asistencia);
        monitoreoButton.setOnClickListener(encuesta);

        View.OnClickListener alumnos = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    Intent intent = new Intent(proyectoInStaff.this, asistenciaAlumnosTec.class);
                    intent.putExtra("proyecto", datos.getString("proyecto"));
                    intent.putExtra("espacio", datos.getString("espacio"));
                    intent.putExtra("incubadora", datos.getString("incubadora"));
                    intent.putExtra("username", nombreUsuario.getText());
                    startActivityForResult(intent,1);
                }
                else
                    Toast.makeText(proyectoInStaff.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };
        alumnosBtn.setOnClickListener(alumnos);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proyecto_in_staff, menu);
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
