package itesm.mx.proyectomoviles;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 26/10/2015.
 */
public class ListViewAdapter extends ArrayAdapter<Proyecto> {
    private Context context;
    int layoutResourceId;
    List<Proyecto> proyectoList;


    public ListViewAdapter(Context context, int idResource, ArrayList<Proyecto> proyectos) {
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

        TextView incubadora = (TextView) row.findViewById(R.id.incubadoraTV);
        TextView espacio = (TextView) row.findViewById(R.id.espacioTV);
        TextView proyect = (TextView) row.findViewById(R.id.nameTV);

        Proyecto proyecto = proyectoList.get(position);
        incubadora.setText(proyecto.getIncubadora());
        espacio.setText(proyecto.getEspacio());
        proyect.setText(proyecto.getProyecto());
        incubadora.setTextColor(Color.BLACK);
        espacio.setTextColor(Color.BLACK);
        proyect.setTextColor(Color.BLACK);

        return row;
    }
}
