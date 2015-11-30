package itesm.mx.proyectomoviles;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Cuanti extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle datos = getIntent().getExtras();
        final String filterProy = datos.getString("proyecto");
        final String filterInc = datos.getString("incubadora");
        final String filterEsp = datos.getString("espacio");
        setContentView(new TableCuantiLayout(this, filterInc, filterEsp, filterProy));
    }
}
