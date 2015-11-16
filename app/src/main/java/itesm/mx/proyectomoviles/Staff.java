package itesm.mx.proyectomoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Staff extends AppCompatActivity {

    private static final String LOG_TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        final ListView proyectoLV;
        proyectoLV =(ListView) findViewById(R.id.ListaStaff);

        Bundle datos = getIntent().getExtras();
/*

        final ListViewAdapter miAdaptador = new ListViewAdapter(getApplicationContext(),R.layout.row,getDataForListView());
        proyectoLV.setAdapter(miAdaptador);

        final AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (Staff.this, proyectoInStaff.class);
                Proyecto proyecto= (Proyecto) miAdaptador.getItem(position);
                Bundle bundleAct = new Bundle();
                intent.putExtra("lugar", proyecto.getEspacio());
                intent.putExtra("nombre", proyecto.getIncubadora());
                startActivity(intent);
            }
        };
        proyectoLV.setOnItemClickListener(itemListener);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_staff, menu);
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
