package itesm.mx.proyectomoviles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



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

public class asistenciaStaff extends Activity implements OnItemClickListener{
    private static final String LOG_TAG = "";
    ArrayList<Alumnos> alumnos = new ArrayList<Alumnos>();
    ListView alumnosLV;
    MyAdapter adapter;
    List<Model> list = new ArrayList<Model>();
    List<String> fechas = new ArrayList<String>();
    ArrayAdapter<String> spin_adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_staff);
        Toast.makeText(this, "Cargando", Toast.LENGTH_SHORT).show();
        alumnosLV = (ListView) findViewById(R.id.listaAlumnos);
        Button guardarBtn = (Button) findViewById(R.id.guardarBtn);
        Button agregarBtn =(Button) findViewById(R.id.agregarBtn);
        context = this;

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1GbTumbQeUZXbQ2nNiA2VxetiU5tsw1RSHHY2QL9KZ4E");

        final Bundle datos = getIntent().getExtras();

        final TextView nombre = (TextView) findViewById(R.id.nameTV);
        nombre.setText(datos.getString("proyecto"));
        final TextView nombreUsuario = (TextView) findViewById(R.id.nombreTV);
        nombreUsuario.setText(datos.getString("username"));

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
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
                    spin_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, fechas);
                    spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spin_adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).execute("https://spreadsheets.google.com/tq?key=1-8-lwlgfjzrld4FdhEYCqoi1fQrtpnPneuI_cP8oxd8");

        Toast.makeText(this, "Finalizado", Toast.LENGTH_SHORT).show();

        View.OnClickListener guardar = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isOnline()){
                String str1 = Integer.toString(adapter.counter);
                String str2 = spinner.getSelectedItem().toString();
                String str3 = datos.getString("proyecto");
                String str4 = datos.getString("incubadora");
                String str5 = datos.getString("espacio");
                String str6 = Integer.toString(list.size());
                String urlParameters = "";
                try {
                    urlParameters = "entry_1579137901=" + URLEncoder.encode(str1, "UTF-8") + "&" +
                            "entry_1621524700=" + URLEncoder.encode(str2, "UTF-8") + "&" +
                            "entry_995735811=" + URLEncoder.encode(str3, "UTF-8") + "&" +
                            "entry_1657960210=" + URLEncoder.encode(str4, "UTF-8") + "&" +
                            "entry_1376801195=" + URLEncoder.encode(str5, "UTF-8") + "&" +
                            "entry_181768415=" + URLEncoder.encode(str6, "UTF-8");
                    new PostTask(new AsyncResult() {
                        @Override
                        public void onResult(JSONObject object) {

                        }
                    }).execute(urlParameters);
                } catch (UnsupportedEncodingException ex) {
                    Toast.makeText(asistenciaStaff.this, "D=", Toast.LENGTH_LONG).show();
                }
                System.out.println(urlParameters);
                Toast.makeText(asistenciaStaff.this, "Se han registrado " + Integer.toString(adapter.counter) + " asistencias.", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < list.size(); i++) {
                    CheckBox checkbox = (CheckBox) alumnosLV.getChildAt(i).findViewById(R.id.checkBox1);
                    str1 = list.get(i).getName();
                    str2 = datos.getString("proyecto");
                    str3 = datos.getString("incubadora");
                    str4 = datos.getString("espacio");
                    str5 = spinner.getSelectedItem().toString();
                    str6 = checkbox.isChecked() ? "1" : "0";
                    urlParameters = "";
                    try {
                        urlParameters = "entry_1736803261=" + URLEncoder.encode(str1, "UTF-8") + "&" +
                                "entry_1389547154=" + URLEncoder.encode(str2, "UTF-8") + "&" +
                                "entry_902302539=" + URLEncoder.encode(str3, "UTF-8") + "&" +
                                "entry_324048957=" + URLEncoder.encode(str4, "UTF-8") + "&" +
                                "entry_1743044751=" + URLEncoder.encode(str5, "UTF-8") + "&" +
                                "entry_1372177941=" + URLEncoder.encode(str6, "UTF-8");
                        new PostInd(new AsyncResult() {
                            @Override
                            public void onResult(JSONObject object) {

                            }
                        }).execute(urlParameters);
                    } catch (UnsupportedEncodingException ex) {
                        Toast.makeText(asistenciaStaff.this, "D=", Toast.LENGTH_LONG).show();
                    }
                }

                finish();
            }
                else
                    Toast.makeText(asistenciaStaff.this, "No hay conexión a internet.", Toast.LENGTH_LONG).show();
            }
        };
        guardarBtn.setOnClickListener(guardar);

        View.OnClickListener agregar = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Intent intent = new Intent(asistenciaStaff.this, AgregarAlumno.class);
                    intent.putExtra("proyecto", datos.getString("proyecto"));
                    intent.putExtra("espacio", datos.getString("espacio"));
                    intent.putExtra("incubadora", datos.getString("incubadora"));
                    intent.putExtra("username", nombreUsuario.getText());
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(asistenciaStaff.this, "No hay conexión a internet.", Toast.LENGTH_LONG).show();
            }
        };
        agregarBtn.setOnClickListener(agregar);
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
                    list.add(new Model(nombre));
                }
            }

            adapter = new MyAdapter(this,list);
            alumnosLV.setAdapter(adapter);
            alumnosLV.setOnItemClickListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        TextView label = (TextView) v.getTag(R.id.nameTV);
        CheckBox checkbox = (CheckBox) v.getTag(R.id.checkBox1);
        Toast.makeText(v.getContext(), label.getText().toString()+" "+isCheckedOrNot(checkbox), Toast.LENGTH_LONG).show();
    }

    private String isCheckedOrNot(CheckBox checkbox) {
        if(checkbox.isChecked())
            return "is checked";
        else
            return "is not checked";
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = alumnosLV.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Alumnos alumno = alumnos.get(pos);
            alumno.setSelected(isChecked);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asistencia_staff, menu);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1GbTumbQeUZXbQ2nNiA2VxetiU5tsw1RSHHY2QL9KZ4E");
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
                URL myUrl = new URL("https://docs.google.com/forms/d/1BE2CjR-kFxHqn1-h7TxISGzebAsR-itdAPeC_bPYSNo/formResponse");
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
                wr.writeBytes (param[0]);
                wr.flush ();
                wr.close ();
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
        }
    }
    public class PostInd extends AsyncTask<String, Void, Boolean> {
        AsyncResult callback;

        public PostInd(AsyncResult callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... param) {

            HttpsURLConnection connection = null;
            Boolean result = true;

            try {
                URL myUrl = new URL("https://docs.google.com/forms/d/1X_Dh77rhmysLXf8NwbJI0AVCS8WfyhzmXAF2B1GH2HY/formResponse");
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
