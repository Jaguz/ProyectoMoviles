package itesm.mx.proyectomoviles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

public class RevisionAsistencia extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle datos = getIntent().getExtras();
        final String filterProy = datos.getString("proyecto");
        final String filterInc = datos.getString("incubadora");
        final String filterEsp = datos.getString("espacio");
        Toast.makeText(this, filterInc + " " + filterEsp + " " + filterProy, Toast.LENGTH_SHORT).show();
        setContentView(new TableMainLayout(this, filterInc, filterEsp, filterProy));
    }
}