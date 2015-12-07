package itesm.mx.proyectomoviles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import javax.net.ssl.HttpsURLConnection;

public class AgregarAlumno extends AppCompatActivity {
Context context;
ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);
        final Bundle datos = getIntent().getExtras();
        context = this;

        final TextView nombreUsuario = (TextView) findViewById(R.id.nombreTV);
        final Button agregarBT = (Button) findViewById(R.id.agregarBT);
        final TextView proy = (TextView) findViewById(R.id.nameTV);
        final TextView espa = (TextView) findViewById(R.id.espacioTV);
        final TextView incubadora = (TextView) findViewById(R.id.layoutID);
        final EditText newName = (EditText) findViewById(R.id.editText);

        proy.setText(datos.getString("proyecto"));
        espa.setText(datos.getString("espacio"));
        incubadora.setText(datos.getString("incubadora"));
        nombreUsuario.setText(datos.getString("username"));


        View.OnClickListener agregar = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    final String str1 = newName.getText().toString();
                    if(str1.equals("")){
                        Toast.makeText(AgregarAlumno.this, "Favor de escribir un nombre.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    new DownloadWebpageTask(new AsyncResult() {
                        @Override
                        public void onResult(JSONObject object) {
                            processJson(object);
                            boolean b=false;
                            for(int i=0; i<list.size(); i++){
                                System.out.println(list.get(i));
                                if(list.get(i).equals(str1)){
                                    b = true;
                                    new AlertDialog.Builder(AgregarAlumno.this)
                                            .setMessage("Ya existe un alumno con ese nombre. ¿Deseas agregarlo de todas formas?")
                                            .setCancelable(false)
                                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    String str2 = datos.getString("proyecto");
                                                    String str3 = datos.getString("incubadora");
                                                    String str4 = datos.getString("espacio");
                                                    String urlParameters = "";
                                                    try {
                                                        urlParameters = "entry_1030089059=" + URLEncoder.encode(str1, "UTF-8") + "&" +
                                                                "entry_173953152=" + URLEncoder.encode(str2, "UTF-8") + "&" +
                                                                "entry_125978482=" + URLEncoder.encode(str3, "UTF-8") + "&" +
                                                                "entry_1481530749=" + URLEncoder.encode(str4, "UTF-8");
                                                        new PostTask(new AsyncResult() {
                                                            @Override
                                                            public void onResult(JSONObject object) {

                                                            }
                                                        }).execute(urlParameters);
                                                    } catch (UnsupportedEncodingException ex) {
                                                        Toast.makeText(AgregarAlumno.this, "D=", Toast.LENGTH_LONG).show();
                                                    }
                                                    System.out.println(urlParameters);
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    return;
                                                }
                                            })
                                            .show();
                                    break;
                                }
                            }
                            if(b) return;
                            String str2 = datos.getString("proyecto");
                            String str3 = datos.getString("incubadora");
                            String str4 = datos.getString("espacio");
                            String urlParameters = "";
                            try {
                                urlParameters = "entry_1030089059=" + URLEncoder.encode(str1, "UTF-8") + "&" +
                                        "entry_173953152=" + URLEncoder.encode(str2, "UTF-8") + "&" +
                                        "entry_125978482=" + URLEncoder.encode(str3, "UTF-8") + "&" +
                                        "entry_1481530749=" + URLEncoder.encode(str4, "UTF-8");
                                new PostTask(new AsyncResult() {
                                    @Override
                                    public void onResult(JSONObject object) {

                                    }
                                }).execute(urlParameters);
                            } catch (UnsupportedEncodingException ex) {
                                Toast.makeText(AgregarAlumno.this, "D=", Toast.LENGTH_LONG).show();
                            }
                            System.out.println(urlParameters);
                        }
                    }, context).execute("https://spreadsheets.google.com/tq?key=1GbTumbQeUZXbQ2nNiA2VxetiU5tsw1RSHHY2QL9KZ4E");


                }
                else
                    Toast.makeText(AgregarAlumno.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };
        agregarBT.setOnClickListener(agregar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_alumno, menu);
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
                URL myUrl = new URL("https://docs.google.com/forms/d/1vo7JdyzQSEQKwtYCglMv8SW--Nxodb7vs-XB9v4QfWM/formResponse");
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
                        connection.getOutputStream ());
                wr.writeBytes(param[0]);
                wr.flush();
                wr.close();
                System.out.println("Response Code : " + connection.getResponseCode());

            }catch (Exception e){
                result = false;
                e.printStackTrace();
            }finally {
                if(null!=connection)
                    connection.disconnect();
            }
            return result;
        }

        // onPostExecute displays the results of the AsyncTask.
        protected void onPostExecute(Boolean result){
            //Print Success or failure message accordingly
            Toast.makeText(context, result ? "Se agregó el alumno correctamente.":"Hubo un error agregando al alumno.", Toast.LENGTH_LONG).show();
            finish();
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

    private void processJson(JSONObject object) {
        try {
            final Bundle datos = getIntent().getExtras();
            JSONArray rows = object.getJSONArray("rows");
            list.clear();
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                String nombre = columns.getJSONObject(1).getString("v");
                String proy = columns.getJSONObject(2).getString("v");
                String esp = columns.getJSONObject(4).getString("v");
                String inc = columns.getJSONObject(3).getString("v");
                if(proy.equals(datos.getString("proyecto"))&&esp.equals(datos.getString("espacio"))&&inc.equals(datos.getString("incubadora"))) {
                    list.add(nombre);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
