package itesm.mx.proyectomoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class proyectoIn extends AppCompatActivity {

    private static final String LOG_TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyecto_in);

        final Bundle datos = getIntent().getExtras();
        final TextView nombre = (TextView) findViewById(R.id.nameTV);
        final Button asistenciaButton = (Button) findViewById(R.id.asistenciaBT);
        final Button monitoreoButton = (Button) findViewById(R.id.monitoreoBT);

        nombre.setText(datos.getString("username"));

        View.OnClickListener asistencia = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proyectoIn.this, Administrador.class);
                intent.putExtra("username", datos.getString("username"));
                startActivityForResult(intent,1);
            }
        };

        View.OnClickListener reporte = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proyectoIn.this, Issues.class);
                intent.putExtra("username", datos.getString("username"));
                startActivityForResult(intent,1);
            }
        };
        asistenciaButton.setOnClickListener(asistencia);
        monitoreoButton.setOnClickListener(reporte);


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
}
