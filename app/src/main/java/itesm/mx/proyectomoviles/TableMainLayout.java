package itesm.mx.proyectomoviles;

/**
 * Created by Javier on 11/16/2015.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TableMainLayout extends RelativeLayout {

    public final String TAG = "TableMainLayout.java";

    // set the header titles
    String headers[]/* = {
            "PROYECTO   ",
            "(Agosto 31 - Septiembre 5)",
            "(Septiembre 7 - 12)",
            "(Septiembre 14 - 19)",
            "(Septiembre 28 - Octubre 3)",
            "(Octubre 5 - 10)",
            "(Octubre 12 - 17)",
            "(Octubre 19 - 24)",
            "(Octubre 26 - 31)"
    }*/;
    List<String> fechas = new ArrayList<String>();
    List<String> proyectos = new ArrayList<String>();
    TableLayout tableA;
    TableLayout tableB;
    TableLayout tableC;
    TableLayout tableD;

    HorizontalScrollView horizontalScrollViewB;
    HorizontalScrollView horizontalScrollViewD;

    ScrollView scrollViewC;
    ScrollView scrollViewD;

    Context context;

    List<SampleObject> sampleObjects = new ArrayList<SampleObject>();

    int headerCellsWidth[];

    public TableMainLayout(Context contex) {

        super(contex);

        this.context = contex;


        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        fechas.add("PROYECTO    ");
        setBackgroundColor(Color.BLUE);
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                try {
                    JSONArray rows = object.getJSONArray("rows");

                    for (int r = 0; r < rows.length(); ++r) {
                        JSONObject row = rows.getJSONObject(r);
                        JSONArray columns = row.getJSONArray("c");
                        String nombre = columns.getJSONObject(1).getString("v");
                        String fecha = columns.getJSONObject(2).getString("v");
                        boolean flag = false;
                        for(int j=0; j<fechas.size(); j++){
                            if(fechas.get(j).equals(fecha)){
                                flag=true;
                            }
                        }
                        if(!flag){
                            //Toast.makeText(context, fecha, Toast.LENGTH_SHORT).show();
                            fechas.add(fecha);
                        }
                        String proyect = columns.getJSONObject(3).getString("v");
                        //Proyecto proyecto = new Proyecto(nombre, lugar, proyect);
                        //proyectos.add(proyecto);
                    }
                    for (int r = 0; r < rows.length(); ++r) {
                        JSONObject row = rows.getJSONObject(r);
                        JSONArray columns = row.getJSONArray("c");
                        String nombre = columns.getJSONObject(3).getString("v");
                        String fecha = columns.getJSONObject(2).getString("v");
                        String asistencia = columns.getJSONObject(1).getString("v");
                        boolean flag = false;
                            for(int i=0; i<proyectos.size(); i++){
                                if(proyectos.get(i).equals(nombre)){
                                    flag=true;
                                }
                            }
                        if(!flag){
                            //Toast.makeText(context, fecha, Toast.LENGTH_SHORT).show();
                            proyectos.add(nombre);
                        }
                        String proyect = columns.getJSONObject(3).getString("v");
                        //Proyecto proyecto = new Proyecto(nombre, lugar, proyect);
                        //proyectos.add(proyecto);
                    }

                    String mat[][] = new String[proyectos.size()][fechas.size()];
                    for(int j=0; j<proyectos.size(); j++) {

                        for (int i = 0; i < fechas.size(); i++) {
                            mat[j][i]=" ";
                        }
                    }
                    for (int r = 0; r < rows.length(); ++r) {
                        JSONObject row = rows.getJSONObject(r);
                        JSONArray columns = row.getJSONArray("c");
                        String nombre = columns.getJSONObject(3).getString("v");
                        String fecha = columns.getJSONObject(2).getString("v");
                        String asistencia = columns.getJSONObject(1).getString("v");
                        boolean flag = false;
                        for(int i=0; i<proyectos.size(); i++){
                            for(int j=0; j<fechas.size(); j++){
                                if(proyectos.get(i).equals(nombre)&&fechas.get(j).equals(fecha)){
                                    mat[i][j] = asistencia;
                                }
                            }
                        }
                    }
                    for(int j=0; j<proyectos.size(); j++){
                        SampleObject so = new SampleObject(proyectos.get(j), new ArrayList<String>());
                        for(int i=1; i<fechas.size(); i++){
                            so.asistencias.add(mat[j][i]);
                        }
                        sampleObjects.add(so);
                    }
                    headerCellsWidth = new int[fechas.size()];
                    //adapter = new ListViewAdapter(this, R.layout.row, proyectos);
                    //proyectoLV.setAdapter(adapter);

                    initComponents();
                    setComponentsId();
                    setScrollViewAndHorizontalScrollViewTag();


                    // no need to assemble component A, since it is just a table
                    horizontalScrollViewB.addView(tableB);

                    scrollViewC.addView(tableC);

                    scrollViewD.addView(horizontalScrollViewD);
                    horizontalScrollViewD.addView(tableD);

                    // add the components to be part of the main layout
                    addComponentToMainLayout();
                    setBackgroundColor(Color.BLUE);

                    // add some table rows
                    addTableRowToTableA();
                    addTableRowToTableB();

                    resizeHeaderHeight();

                    getTableRowHeaderCellWidth();

                    generateTableC_AndTable_B();

                    resizeBodyTableRowHeight();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("https://spreadsheets.google.com/tq?key=1ECcVX2RlkyILoUxpX5eVbtt8gfo7GgdyGAJKxhT8JVk");
        // initialize the main components (TableLayouts, HorizontalScrollView, ScrollView)


    }

    // this is just the sample data
    List<SampleObject> sampleObjects(){
    /*
        List<SampleObject> sampleObjects = new ArrayList<SampleObject>();

        for(int x=1; x<=30; x++){

            SampleObject sampleObject = new SampleObject(
                    "Proyecto " + x,
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10",
                    "11"
            );

            sampleObjects.add(sampleObject);
        }
*/
        return sampleObjects;


    }

    // initalized components
    private void initComponents(){

        this.tableA = new TableLayout(this.context);
        this.tableB = new TableLayout(this.context);
        this.tableC = new TableLayout(this.context);
        this.tableD = new TableLayout(this.context);

        this.horizontalScrollViewB = new MyHorizontalScrollView(this.context);
        this.horizontalScrollViewD = new MyHorizontalScrollView(this.context);

        this.scrollViewC = new MyScrollView(this.context);
        this.scrollViewD = new MyScrollView(this.context);

        this.tableA.setBackgroundColor(Color.GREEN);
        this.horizontalScrollViewB.setBackgroundColor(Color.LTGRAY);

    }

    // set essential component IDs
    private void setComponentsId(){
        this.tableA.setId(1);
        this.horizontalScrollViewB.setId(2);
        this.scrollViewC.setId(3);
        this.scrollViewD.setId(4);
    }

    // set tags for some horizontal and vertical scroll view
    private void setScrollViewAndHorizontalScrollViewTag(){

        this.horizontalScrollViewB.setTag("horizontal scroll view b");
        this.horizontalScrollViewD.setTag("horizontal scroll view d");

        this.scrollViewC.setTag("scroll view c");
        this.scrollViewD.setTag("scroll view d");
    }

    // we add the components here in our TableMainLayout
    private void addComponentToMainLayout(){

        // RelativeLayout params were very useful here
        // the addRule method is the key to arrange the components properly
        RelativeLayout.LayoutParams componentB_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        componentB_Params.addRule(RelativeLayout.RIGHT_OF, this.tableA.getId());

        RelativeLayout.LayoutParams componentC_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        componentC_Params.addRule(RelativeLayout.BELOW, this.tableA.getId());

        RelativeLayout.LayoutParams componentD_Params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        componentD_Params.addRule(RelativeLayout.RIGHT_OF, this.scrollViewC.getId());
        componentD_Params.addRule(RelativeLayout.BELOW, this.horizontalScrollViewB.getId());

        // 'this' is a relative layout,
        // we extend this table layout as relative layout as seen during the creation of this class
        this.addView(this.tableA);
        this.addView(this.horizontalScrollViewB, componentB_Params);
        this.addView(this.scrollViewC, componentC_Params);
        this.addView(this.scrollViewD, componentD_Params);

    }



    private void addTableRowToTableA(){
        this.tableA.addView(this.componentATableRow());
    }

    private void addTableRowToTableB(){
        this.tableB.addView(this.componentBTableRow());
    }

    // generate table row of table A
    TableRow componentATableRow(){

        TableRow componentATableRow = new TableRow(this.context);
        TextView textView = this.headerTextView(this.fechas.get(0));
        componentATableRow.addView(textView);

        return componentATableRow;
    }

    // generate table row of table B
    TableRow componentBTableRow(){

        TableRow componentBTableRow = new TableRow(this.context);
        int headerFieldCount = this.fechas.size();

        TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        params.setMargins(2, 0, 0, 0);

        for(int x=0; x<(headerFieldCount-1); x++){
            TextView textView = this.headerTextView(this.fechas.get(x+1));
            textView.setLayoutParams(params);
            componentBTableRow.addView(textView);
        }

        return componentBTableRow;
    }

    // generate table row of table C and table D
    private void generateTableC_AndTable_B(){

        // just seeing some header cell width
        for(int x=0; x<this.headerCellsWidth.length; x++){
            Log.v("TableMainLayout.java", this.headerCellsWidth[x]+"");
        }

        for(SampleObject sampleObject : this.sampleObjects){

            TableRow tableRowForTableC = this.tableRowForTableC(sampleObject);
            TableRow taleRowForTableD = this.taleRowForTableD(sampleObject);

            tableRowForTableC.setBackgroundColor(Color.LTGRAY);
            taleRowForTableD.setBackgroundColor(Color.LTGRAY);

            this.tableC.addView(tableRowForTableC);
            this.tableD.addView(taleRowForTableD);

        }
    }

    // a TableRow for table C
    TableRow tableRowForTableC(SampleObject sampleObject){

        TableRow.LayoutParams params = new TableRow.LayoutParams( this.headerCellsWidth[0],LayoutParams.MATCH_PARENT);
        params.setMargins(0, 2, 0, 0);

        TableRow tableRowForTableC = new TableRow(this.context);
        TextView textView = this.bodyTextView(sampleObject.header1);
        tableRowForTableC.addView(textView, params);

        return tableRowForTableC;
    }

    TableRow taleRowForTableD(SampleObject sampleObject){

        TableRow taleRowForTableD = new TableRow(this.context);

        int loopCount = ((TableRow)this.tableB.getChildAt(0)).getChildCount();
        List<String> info = sampleObject.asistencias;

        for(int x=0 ; x<loopCount; x++){
            TableRow.LayoutParams params = new TableRow.LayoutParams( headerCellsWidth[x+1],LayoutParams.MATCH_PARENT);
            params.setMargins(2, 2, 0, 0);
            TextView textViewB;
            if(x<info.size()) {
                textViewB = this.bodyTextView(info.get(x));
            }
            else{
                textViewB = this.bodyTextView(" ");
            }
            taleRowForTableD.addView(textViewB, params);
        }

        return taleRowForTableD;

    }

    // table cell standard TextView
    TextView bodyTextView(String label){

        TextView bodyTextView = new TextView(this.context);
        bodyTextView.setBackgroundColor(Color.WHITE);
        bodyTextView.setText(label);
        bodyTextView.setGravity(Gravity.CENTER);
        bodyTextView.setPadding(5, 5, 5, 5);

        return bodyTextView;
    }

    // header standard TextView
    TextView headerTextView(String label){

        TextView headerTextView = new TextView(this.context);
        headerTextView.setBackgroundColor(Color.WHITE);
        headerTextView.setText(label);
        headerTextView.setGravity(Gravity.CENTER);
        headerTextView.setPadding(5, 5, 5, 5);

        return headerTextView;
    }

    // resizing TableRow height starts here
    void resizeHeaderHeight() {

        TableRow productNameHeaderTableRow = (TableRow) this.tableA.getChildAt(0);
        TableRow productInfoTableRow = (TableRow)  this.tableB.getChildAt(0);

        int rowAHeight = this.viewHeight(productNameHeaderTableRow);
        int rowBHeight = this.viewHeight(productInfoTableRow);

        TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
        int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

        this.matchLayoutHeight(tableRow, finalHeight);
    }

    void getTableRowHeaderCellWidth(){

        int tableAChildCount = ((TableRow)this.tableA.getChildAt(0)).getChildCount();
        int tableBChildCount = ((TableRow)this.tableB.getChildAt(0)).getChildCount();;

        for(int x=0; x<(tableAChildCount+tableBChildCount); x++){

            if(x==0){
                this.headerCellsWidth[x] = this.viewWidth(((TableRow)this.tableA.getChildAt(0)).getChildAt(x));
            }else{
                this.headerCellsWidth[x] = this.viewWidth(((TableRow)this.tableB.getChildAt(0)).getChildAt(x-1));
            }

        }
    }

    // resize body table row height
    void resizeBodyTableRowHeight(){

        int tableC_ChildCount = this.tableC.getChildCount();

        for(int x=0; x<tableC_ChildCount; x++){

            TableRow productNameHeaderTableRow = (TableRow) this.tableC.getChildAt(x);
            TableRow productInfoTableRow = (TableRow)  this.tableD.getChildAt(x);

            int rowAHeight = this.viewHeight(productNameHeaderTableRow);
            int rowBHeight = this.viewHeight(productInfoTableRow);

            TableRow tableRow = rowAHeight < rowBHeight ? productNameHeaderTableRow : productInfoTableRow;
            int finalHeight = rowAHeight > rowBHeight ? rowAHeight : rowBHeight;

            this.matchLayoutHeight(tableRow, finalHeight);
        }

    }

    // match all height in a table row
    // to make a standard TableRow height
    private void matchLayoutHeight(TableRow tableRow, int height) {

        int tableRowChildCount = tableRow.getChildCount();

        // if a TableRow has only 1 child
        if(tableRow.getChildCount()==1){

            View view = tableRow.getChildAt(0);
            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();
            params.height = height - (params.bottomMargin + params.topMargin);

            return ;
        }

        // if a TableRow has more than 1 child
        for (int x = 0; x < tableRowChildCount; x++) {

            View view = tableRow.getChildAt(x);

            TableRow.LayoutParams params = (TableRow.LayoutParams) view.getLayoutParams();

            if (!isTheHeighestLayout(tableRow, x)) {
                params.height = height - (params.bottomMargin + params.topMargin);
                return;
            }
        }

    }

    // check if the view has the highest height in a TableRow
    private boolean isTheHeighestLayout(TableRow tableRow, int layoutPosition) {

        int tableRowChildCount = tableRow.getChildCount();
        int heighestViewPosition = -1;
        int viewHeight = 0;

        for (int x = 0; x < tableRowChildCount; x++) {
            View view = tableRow.getChildAt(x);
            int height = this.viewHeight(view);

            if (viewHeight < height) {
                heighestViewPosition = x;
                viewHeight = height;
            }
        }

        return heighestViewPosition == layoutPosition;
    }

    // read a view's height
    private int viewHeight(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredHeight();
    }

    // read a view's width
    private int viewWidth(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        return view.getMeasuredWidth();
    }

    // horizontal scroll view custom class
    class MyHorizontalScrollView extends HorizontalScrollView{

        public MyHorizontalScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            String tag = (String) this.getTag();

            if(tag.equalsIgnoreCase("horizontal scroll view b")){
                horizontalScrollViewD.scrollTo(l, 0);
            }else{
                horizontalScrollViewB.scrollTo(l, 0);
            }
        }

    }

    // scroll view custom class
    class MyScrollView extends ScrollView{

        public MyScrollView(Context context) {
            super(context);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {

            String tag = (String) this.getTag();

            if(tag.equalsIgnoreCase("scroll view c")){
                scrollViewD.scrollTo(0, t);
            }else{
                scrollViewC.scrollTo(0,t);
            }
        }
    }


}
