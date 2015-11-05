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


        final TextView nombre = (TextView) findViewById(R.id.nombreTV);
        final TextView lugar = (TextView) findViewById(R.id.nombreTV);
        final Button monitoreoButton = (Button) findViewById(R.id.encuestaBT);
        final Button reporteButton = (Button) findViewById(R.id.reporteBT);

        nombre.setText(datos.getString("nombre"));
        lugar.setText(datos.getString("lugar"));

        View.OnClickListener monitoreo = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proyectoIn.this, Monitoreo.class);
                intent.putExtra("lugar", datos.getString("lugar"));
                intent.putExtra("nombre", datos.getString("nombre"));
                startActivityForResult(intent,1);
            }
        };

        View.OnClickListener reporte = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(proyectoIn.this, Issues.class);
                intent.putExtra("lugar", datos.getString("lugar"));
                intent.putExtra("nombre", datos.getString("nombre"));
                startActivityForResult(intent,1);
            }
        };
        reporteButton.setOnClickListener(reporte);
        monitoreoButton.setOnClickListener(monitoreo);


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
