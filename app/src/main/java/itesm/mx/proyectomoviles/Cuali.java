package itesm.mx.proyectomoviles;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Cuali extends AppCompatActivity  {
    private GestureDetectorCompat mDetector;
    private static final String DEBUG_TAG = "Gestures";
    TextView incTV;
    TextView espTV;
    TextView proyTV;
    TextView fechaTV;
    TextView ans1TV;
    TextView ans2TV;
    TextView ans3TV;
    Context context;

    boolean scrolling = false;

    List<String> fechas = new ArrayList<String>();
    List<String> proyectos = new ArrayList<String>();
    List<String> espacios = new ArrayList<String>();
    List<String> incubadoras = new ArrayList<String>();

    String answers[][][];

    String filterProy;
    String filterInc;
    String filterEsp;

    int x = 0;
    int y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuali);

        context = this;

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        final Bundle datos = getIntent().getExtras();
        filterProy = datos.getString("proyecto");
        filterInc = datos.getString("incubadora");
        filterEsp = datos.getString("espacio");

        incTV = (TextView) findViewById(R.id.incuTV);
        espTV = (TextView) findViewById(R.id.espaTV);
        proyTV = (TextView) findViewById(R.id.proyTV);
        fechaTV = (TextView) findViewById(R.id.fechaTV);
        ans1TV = (TextView) findViewById(R.id.ans1);
        ans2TV = (TextView) findViewById(R.id.ans2);
        ans3TV = (TextView) findViewById(R.id.ans3);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cuali, menu);
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

    public void init(){
         new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                try {
                    final JSONArray rows = object.getJSONArray("rows");

                    new DownloadWebpageTask(new AsyncResult() {
                        @Override
                        public void onResult(JSONObject object) {
                            try {
                                JSONArray rows2 = object.getJSONArray("rows");

                                for (int r = 0; r < rows2.length(); ++r) {
                                    JSONObject row = rows2.getJSONObject(r);
                                    JSONArray columns = row.getJSONArray("c");
                                    String fecha = columns.getJSONObject(1).getString("v");
                                    fechas.add(fecha);
                                }

                                new DownloadWebpageTask(new AsyncResult() {
                                    @Override
                                    public void onResult(JSONObject object) {
                                        try {
                                            JSONArray rows3 = object.getJSONArray("rows");

                                            for (int r = 0; r < rows3.length(); ++r) {
                                                JSONObject row = rows3.getJSONObject(r);
                                                JSONArray columns = row.getJSONArray("c");

                                                String inc = columns.getJSONObject(1).getString("v");
                                                String esp = columns.getJSONObject(2).getString("v");
                                                String proy = columns.getJSONObject(3).getString("v");
                                                boolean flag = false;
                                                if (!((proy.equals(filterProy) || filterProy.equals("-")) && (esp.equals(filterEsp) || filterEsp.equals("-")) && (inc.equals(filterInc) || filterInc.equals("-")))) {
                                                    flag = true;
                                                }
                                                if(!flag){
                                                    proyectos.add(proy);
                                                    incubadoras.add(inc);
                                                    espacios.add(esp);
                                                }
                                            }

                                            answers = new String[proyectos.size()][fechas.size()][3];

                                            for(int j=0; j<proyectos.size(); j++) {
                                                for (int i = 0; i < fechas.size(); i++) {
                                                    answers[j][i][0]="Sin responder";
                                                    answers[j][i][1]="Sin responder";
                                                    answers[j][i][2]="Sin responder";
                                                }
                                            }

                                            for (int r = 0; r < rows.length(); ++r) {
                                                JSONObject row = rows.getJSONObject(r);
                                                JSONArray columns = row.getJSONArray("c");
                                                String esp = columns.getJSONObject(12).getString("v");
                                                String inc = columns.getJSONObject(11).getString("v");
                                                String proy = columns.getJSONObject(13).getString("v");
                                                String fecha = columns.getJSONObject(14).getString("v");
                                                String ans1 = columns.getJSONObject(6).getString("v");
                                                String ans2 = columns.getJSONObject(7).getString("v");
                                                String ans3 = columns.getJSONObject(8).getString("v");
                                                boolean flag = false;
                                                for(int i=0; i<proyectos.size(); i++){
                                                    for(int j=0; j<fechas.size(); j++){
                                                        if(incubadoras.get(i).equals(inc)&&espacios.get(i).equals(esp)&&proyectos.get(i).equals(proy)&&fechas.get(j).equals(fecha)){
                                                            answers[i][j][0] = ans1;
                                                            answers[i][j][1] = ans2;
                                                            answers[i][j][2] = ans3;
                                                        }
                                                    }
                                                }
                                            }
                                            setData();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, Cuali.this).execute("https://spreadsheets.google.com/tq?key=1pWC4p-9M_yWUpg0iYTDgUADvHBfoPqG4rBlv6j3jXD8");



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, Cuali.this).execute("https://spreadsheets.google.com/tq?key=1-8-lwlgfjzrld4FdhEYCqoi1fQrtpnPneuI_cP8oxd8");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Cuali.this).execute("https://spreadsheets.google.com/tq?key=1fg-dApPcbjP6dfzCZJVPAL4xUGIlVDdslZol6ilb0Kc");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void init2(){
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Cuali.this).execute("https://spreadsheets.google.com/tq?key=1-8-lwlgfjzrld4FdhEYCqoi1fQrtpnPneuI_cP8oxd8");
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if(proyectos.size()==0 || fechas.size()==0) return true;
            if(Math.abs(e1.getX()- e2.getX())>Math.abs(e1.getY()- e2.getY())) {
                if (e1.getX() > e2.getX()) {
                    x = (x + 1) % fechas.size();
                    setData();
                }
                if (e1.getX() < e2.getX()) {
                    x = (x - 1 + fechas.size()) % fechas.size();
                    setData();
                }
            } else {
                if (e1.getY() > e2.getY()) {
                    y = (y + 1) % proyectos.size();
                    setData();
                }
                if (e1.getY() < e2.getY()) {
                    y = (y - 1 + proyectos.size()) % proyectos.size();
                    setData();
                }
            }
            return true;
        }
    }



    public void setData(){
        if(proyectos.size()==0) return;
        if(fechas.size()==0) return;
        incTV.setText(incubadoras.get(y));
        espTV.setText(espacios.get(y));
        proyTV.setText(proyectos.get(y));
        fechaTV.setText(fechas.get(x));
        ans1TV.setText(answers[y][x][0]);
        ans2TV.setText(answers[y][x][1]);
        ans3TV.setText(answers[y][x][2]);
    }
}
