package itesm.mx.proyectomoviles;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class AgregarAlumno extends AppCompatActivity {
Context context;
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
                String str1 = newName.getText().toString();
                String str2 = datos.getString("proyecto");
                String str3 = datos.getString("incubadora");
                String str4 = datos.getString("espacio");
                String urlParameters="";
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
                }
                catch (UnsupportedEncodingException ex) {
                    Toast.makeText(AgregarAlumno.this, "D=", Toast.LENGTH_LONG).show();
                }
                System.out.println(urlParameters);

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
                finish();
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
        }
    }
}
