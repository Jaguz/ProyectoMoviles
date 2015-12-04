package itesm.mx.proyectomoviles;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> usuario = new ArrayList<String>();
    List<String> contrasena = new ArrayList<String>();
    List<String> usuarioStaff = new ArrayList<String>();
    List<String> contrasenaStaff = new ArrayList<String>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        final EditText userET = (EditText) findViewById(R.id.usuarioET);
        final EditText passET = (EditText) findViewById(R.id.contrasenaET);
        final Button entrarButton = (Button) findViewById(R.id.entrarBT);


        final View.OnClickListener registro = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isOnline()) {
                Intent intent = new Intent(MainActivity.this, proyectoIn.class);
                Intent intent2 = new Intent(MainActivity.this, Staff.class);

                for (int i = 0; i < usuario.size(); i++) {
                    if (userET.getText().toString().equals(usuario.get(i)) && passET.getText().toString().equals(contrasena.get(i))) {

                        intent.putExtra("username", userET.getText().toString());
                        intent.putExtra("password", passET.getText().toString());
                        startActivityForResult(intent, 1);
                    }

                }
                for (int i = 0; i < usuarioStaff.size(); i++) {
                    if (userET.getText().toString().equals(usuarioStaff.get(i)) &&
                            passET.getText().toString().equals(contrasenaStaff.get(i))) {
                        intent2.putExtra("username", userET.getText().toString());
                        intent2.putExtra("password", passET.getText().toString());
                        startActivityForResult(intent2, 1);
                    }
                }
            }
                else
                    Toast.makeText(MainActivity.this, "No hay conexión a internet.", Toast.LENGTH_LONG).show();

            }

        };

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJsonStaff(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1atmo4rfSPICcsjDxZBbq0QohQqzZDYuKpx7j4ExNoQw");

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1EaWzs2mN10HUr-nwV9C1tanRAzTkCJ27pntkxqeIPyw");

        entrarButton.setOnClickListener(registro);
    }

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String usu = columns.getJSONObject(1).getString("v");
                String pass = columns.getJSONObject(2).getString("v");
                usuario.add(usu);
                contrasena.add(pass);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void processJsonStaff(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                String usu = columns.getJSONObject(1).getString("v");
                String pass = columns.getJSONObject(2).getString("v");
                usuarioStaff.add(usu);
                contrasenaStaff.add(pass);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
