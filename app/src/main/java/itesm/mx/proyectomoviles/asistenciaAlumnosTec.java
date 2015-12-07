package itesm.mx.proyectomoviles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
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

public class asistenciaAlumnosTec extends Activity implements AdapterView.OnItemClickListener {
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
        setContentView(R.layout.activity_asistencia_alumnos_tec);
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
        }, this).execute("https://spreadsheets.google.com/tq?key=12aBrk-jw5Dhfdh6otjHyufpl48HsKm6KQj255Nz5hEA");

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
                    spin_adapter = new ArrayAdapter<String>(context,  R.layout.my_spinner, fechas);
                    spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(spin_adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, this).execute("https://spreadsheets.google.com/tq?key=1-8-lwlgfjzrld4FdhEYCqoi1fQrtpnPneuI_cP8oxd8");

        Toast.makeText(this, "Finalizado", Toast.LENGTH_SHORT).show();

        View.OnClickListener guardar = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    String str1 = Integer.toString(adapter.counter);
                    String str2 = spinner.getSelectedItem().toString();
                    String str3 = datos.getString("proyecto");
                    String str4 = datos.getString("incubadora");
                    String str5 = datos.getString("espacio");
                    String str6 = Integer.toString(list.size());
                    String urlParameters = "";
                    try {
                        urlParameters = "entry_366698164=" + URLEncoder.encode(str1, "UTF-8") + "&" +
                                "entry_1218448724=" + URLEncoder.encode(str2, "UTF-8") + "&" +
                                "entry_1905273648=" + URLEncoder.encode(str3, "UTF-8") + "&" +
                                "entry_2086384079=" + URLEncoder.encode(str4, "UTF-8") + "&" +
                                "entry_1704396944=" + URLEncoder.encode(str5, "UTF-8") + "&" +
                                "entry_446605895=" + URLEncoder.encode(str6, "UTF-8");
                        new PostTask(new AsyncResult() {
                            @Override
                            public void onResult(JSONObject object) {

                            }
                        }).execute(urlParameters);
                    } catch (UnsupportedEncodingException ex) {
                        Toast.makeText(asistenciaAlumnosTec.this, "D=", Toast.LENGTH_LONG).show();
                    }
                    System.out.println(urlParameters);
                    Toast.makeText(asistenciaAlumnosTec.this, "Se han registrado " + Integer.toString(adapter.counter) + " asistencias.", Toast.LENGTH_SHORT).show();

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
                            urlParameters = "entry_324389209=" + URLEncoder.encode(str1, "UTF-8") + "&" +
                                    "entry_885529990=" + URLEncoder.encode(str2, "UTF-8") + "&" +
                                    "entry_972164826=" + URLEncoder.encode(str3, "UTF-8") + "&" +
                                    "entry_1693376882=" + URLEncoder.encode(str4, "UTF-8") + "&" +
                                    "entry_50541225=" + URLEncoder.encode(str5, "UTF-8") + "&" +
                                    "entry_1562616259=" + URLEncoder.encode(str6, "UTF-8");
                            new PostInd(new AsyncResult() {
                                @Override
                                public void onResult(JSONObject object) {

                                }
                            }).execute(urlParameters);
                        } catch (UnsupportedEncodingException ex) {
                            Toast.makeText(asistenciaAlumnosTec.this, "D=", Toast.LENGTH_LONG).show();
                        }
                    }

                    finish();
                }
                else
                    Toast.makeText(asistenciaAlumnosTec.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
            }
        };
        guardarBtn.setOnClickListener(guardar);

        View.OnClickListener agregar = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Intent intent = new Intent(asistenciaAlumnosTec.this, AgregarAlumnoTec.class);
                    intent.putExtra("proyecto", datos.getString("proyecto"));
                    intent.putExtra("espacio", datos.getString("espacio"));
                    intent.putExtra("incubadora", datos.getString("incubadora"));
                    intent.putExtra("username", nombreUsuario.getText());
                    startActivityForResult(intent, 1);
                }
                else
                    Toast.makeText(asistenciaAlumnosTec.this, "No hay conexión a internet.", Toast.LENGTH_SHORT).show();
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
        }, this).execute("https://spreadsheets.google.com/tq?key=12aBrk-jw5Dhfdh6otjHyufpl48HsKm6KQj255Nz5hEA");
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
                URL myUrl = new URL("https://docs.google.com/forms/d/10ZJCyyfgJWoIjJvq57FAvvw95VtnWR_lLJ4M0M2Pgd4/formResponse");
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
                URL myUrl = new URL("https://docs.google.com/forms/d/1yMw8EHn5LWqy1YZDak_2J-r6WtOpHUJUzLiF1h_ZaAw/formResponse");
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