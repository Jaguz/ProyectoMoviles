package itesm.mx.proyectomoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class proyectoInStaff extends AppCompatActivity {
    private static final String LOG_TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto_in_staff);
        final Bundle datos = getIntent().getExtras();

        final Button monitoreoButton = (Button) findViewById(R.id.monitoreoBT);
        final Button asistenciaButton = (Button) findViewById(R.id.asistenciaBT);
        final TextView nombre = (TextView) findViewById(R.id.nameTV);
        final TextView lugar = (TextView) findViewById(R.id.espacioTV);
        final TextView incubadora = (TextView) findViewById(R.id.layoutID);

        nombre.setText(datos.getString("proyecto"));
        lugar.setText(datos.getString("espacio"));
        incubadora.setText(datos.getString("incubadora"));

        View.OnClickListener encuesta = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proyectoInStaff.this, encuesta.class);
                intent.putExtra("proyecto", datos.getString("proyecto"));
                intent.putExtra("espacio", datos.getString("espacio"));
                intent.putExtra("incubadora", datos.getString("incubadora"));
                startActivityForResult(intent,1);
            }
        };

        View.OnClickListener asistencia = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proyectoInStaff.this, asistenciaStaff.class);
                intent.putExtra("proyecto", datos.getString("proyecto"));
                intent.putExtra("espacio", datos.getString("espacio"));
                intent.putExtra("incubadora", datos.getString("incubadora"));
                startActivityForResult(intent,1);
            }
        };
        asistenciaButton.setOnClickListener(asistencia);
        monitoreoButton.setOnClickListener(encuesta);




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
}
