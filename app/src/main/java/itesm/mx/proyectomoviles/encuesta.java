package itesm.mx.proyectomoviles;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class encuesta extends AppCompatActivity {
    private static final String LOG_TAG = "";
    ArrayAdapter<String> spin_adapter;
    Context context;
    List<String> fechas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        context= this;
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final TextView respuesta1 = (TextView) findViewById(R.id.respuesta1);
        final TextView respuesta2 = (TextView) findViewById(R.id.respuesta2);
        final TextView respuesta3 = (TextView) findViewById(R.id.respuesta3);
        final TextView respuesta4 = (TextView) findViewById(R.id.respuesta4);
        final TextView respuesta5 = (TextView) findViewById(R.id.respuesta5);
        final TextView respuesta6 = (TextView) findViewById(R.id.respuesta6);
        final TextView respuesta7 = (TextView) findViewById(R.id.respuesta7);
        final TextView respuesta8 = (TextView) findViewById(R.id.respuesta8);
        final TextView respuesta9 = (TextView) findViewById(R.id.respuesta9);
        final TextView respuesta10 = (TextView) findViewById(R.id.respuesta10);

        final Bundle datos = getIntent().getExtras();
        final Button terminarButton = (Button) findViewById(R.id.terminarBT);
        final TextView nombre = (TextView) findViewById(R.id.nombreTV);
        nombre.setText(datos.getString("proyecto")  );


        View.OnClickListener terminar = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){
                String r1 = respuesta1.getText().toString();
                    if(r1.equals("")||Integer.parseInt(r1)>5||Integer.parseInt(r1)<1) {
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r2 = respuesta2.getText().toString();
                    if(r2.equals("")||Integer.parseInt(r2)>5||Integer.parseInt(r2)<1){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r3 = respuesta3.getText().toString();
                    if(r3.equals("")||Integer.parseInt(r3)>5||Integer.parseInt(r3)<1){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r4 = respuesta4.getText().toString();
                    if(r4.equals("")||Integer.parseInt(r4)>5||Integer.parseInt(r4)<1){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r5 = respuesta5.getText().toString();
                    if(r5.equals("")||Integer.parseInt(r5)>5||Integer.parseInt(r5)<1){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r6 = respuesta6.getText().toString();
                    if(r6.equals("")||Integer.parseInt(r6)>5||Integer.parseInt(r6)<1){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r7 = respuesta7.getText().toString();
                    if(r7.equals("")){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r8 = respuesta8.getText().toString();
                    if(r8.equals("")){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r9 = respuesta9.getText().toString();
                    if(r9.equals("")){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String r10 = respuesta10.getText().toString();
                    if(r10.equals("")){
                        Toast.makeText(encuesta.this, "Favor de completar la encuesta con respuestas vaildas.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                String proyecto = datos.getString("proyecto");
                String incubadora = datos.getString("incubadora");
                String espacio = datos.getString("espacio");
                String fecha = spinner.getSelectedItem().toString();

                String urlParameters = "";
                boolean res = true;

                try {
                    urlParameters = "entry_1411199782=" + URLEncoder.encode(r1, "UTF-8") + "&" +
                            "entry_1090701511=" + URLEncoder.encode(r2, "UTF-8") + "&" +
                            "entry_1621503727=" + URLEncoder.encode(r3, "UTF-8") + "&" +
                            "entry_754173517=" + URLEncoder.encode(r4, "UTF-8") + "&" +
                            "entry_1034828824=" + URLEncoder.encode(r5, "UTF-8") + "&" +
                            "entry_192959873=" + URLEncoder.encode(r6, "UTF-8") + "&" +
                            "entry_1537846137=" + URLEncoder.encode(r7, "UTF-8") + "&" +
                            "entry_1427077632=" + URLEncoder.encode(r8, "UTF-8") + "&" +
                            "entry_730829470=" + URLEncoder.encode(r9, "UTF-8") + "&" +
                            "entry_145679237=" + URLEncoder.encode(r10, "UTF-8") + "&" +
                            "entry_1186180893=" + URLEncoder.encode(incubadora, "UTF-8") + "&" +
                            "entry_1140484594=" + URLEncoder.encode(espacio, "UTF-8") + "&" +
                            "entry_1863821886=" + URLEncoder.encode(proyecto, "UTF-8") + "&" +
                            "entry_173526216=" + URLEncoder.encode(fecha, "UTF-8");
                    new PostTask(new AsyncResult() {
                        @Override
                        public void onResult(JSONObject object) {

                        }
                    }).execute(urlParameters);
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(encuesta.this, "ño", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(context, "Encuesta Enviada", Toast.LENGTH_LONG).show();
                finish();
            }
                else
                    Toast.makeText(encuesta.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };

        terminarButton.setOnClickListener(terminar);
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                try {
                    final Bundle datos = getIntent().getExtras();
                    JSONArray rows = object.getJSONArray("rows");

                    for (int r = 0; r < rows.length(); ++r) {
                        JSONObject row = rows.getJSONObject(r);
                        JSONArray columns = row.getJSONArray("c");
                        String fecha = columns.getJSONObject(1).getString("v");
                        fechas.add(fecha);
                    }
                    spin_adapter = new ArrayAdapter<String>(context, R.layout.my_spinner, fechas);
                    spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spin_adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).execute("https://spreadsheets.google.com/tq?key=1-8-lwlgfjzrld4FdhEYCqoi1fQrtpnPneuI_cP8oxd8");
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

    public class PostTask extends AsyncTask<String, Void, Boolean> {
        AsyncResult callback;

        public PostTask(AsyncResult callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... param) {

            HttpsURLConnection connection = null;
            Boolean result = true;

            try {
                URL myUrl = new URL("https://docs.google.com/forms/d/1mYgiHAwq-m7hW4xv2vNz1ahaSDbSlaUImq3tW1unpXk/formResponse");
                connection = (HttpsURLConnection) myUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", "ProyectoMoviles/1.0");
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


                //connection.setRequestProperty("Content-Length", "" +
                //Integer.toString(urlParameters.getBytes().length));
                //connection.setRequestProperty("Content-Language", "en-US");

                //connection.setUseCaches (false);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(param[0]);
                wr.flush();
                wr.close();
                System.out.println("Response Code : " + connection.getResponseCode());
            } catch (Exception e) {
                result = false;
                e.printStackTrace();
            } finally {
                if (null != connection)
                    connection.disconnect();
            }
            return result;
        }
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
