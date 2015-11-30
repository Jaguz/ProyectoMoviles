package itesm.mx.proyectomoviles;

import android.app.ProgressDialog;
import android.os.Handler;
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
<<<<<<< HEAD
        Toast.makeText(this, "Cargando", Toast.LENGTH_SHORT).show();
=======
>>>>>>> refs/remotes/origin/master
        setContentView(new TableMainLayout(this, filterInc, filterEsp, filterProy));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                  //Do something after 100ms
            }
        }, 200);
        Toast.makeText(this, "Finalizado", Toast.LENGTH_SHORT).show();
    }
}