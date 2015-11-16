package itesm.mx.proyectomoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class encuesta extends AppCompatActivity {
    private static final String LOG_TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        final Bundle datos = getIntent().getExtras();

        final Button terminarButton = (Button) findViewById(R.id.terminarBT);
        final TextView nombre = (TextView) findViewById(R.id.incubadoraTV);
        nombre.setText(datos.getString("nombre"));

        View.OnClickListener terminar = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(encuesta.this, Staff.class);
                startActivityForResult(intent,1);
            }
        };

        terminarButton.setOnClickListener(terminar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_encuesta, menu);
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
