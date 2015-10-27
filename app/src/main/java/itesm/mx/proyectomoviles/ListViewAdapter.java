package itesm.mx.proyectomoviles;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by USUARIO on 26/10/2015.
 */
public class ListViewAdapter extends ArrayAdapter<Proyecto> {
    private Context context;
    int layoutResourceId;
    List<Proyecto> proyectoList;


    public ListViewAdapter(Context context, int idResource, List<Proyecto> proyectos) {
        super(context, idResource, proyectos);
        this.context= context;
        this.layoutResourceId= idResource;
        this.proyectoList = proyectos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
        }
        TextView nombre = (TextView) row.findViewById(R.id.nombreTV);
        TextView lugar = (TextView) row.findViewById(R.id.lugarTV);

        Proyecto proyecto = proyectoList.get(position);
        nombre.setText(proyecto.getNombre());
        lugar.setText(proyecto.getLugar());


        nombre.setTextColor(Color.BLACK);
        lugar.setTextColor(Color.BLACK);

        return row;
    }
}
