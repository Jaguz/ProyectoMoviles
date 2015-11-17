package itesm.mx.proyectomoviles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by USUARIO on 17/11/2015.
 */
public class AlumnosAdapter extends ArrayAdapter<Alumnos> {
    private Context context;
    int layoutResourceId;
    List<Alumnos> alumnosList;
    CheckBox chkBox;

    public AlumnosAdapter(Context context, int idResource, List<Alumnos> alumnosList) {
        super(context, idResource, alumnosList);
        this.context= context;
        this.layoutResourceId= idResource;
        this.alumnosList = alumnosList;
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
        chkBox = (CheckBox) row.findViewById(R.id.checkBox1);

        Alumnos alumno = alumnosList.get(position);
        nombre.setText(alumno.getName());
        chkBox.setChecked(alumno.isSelected());
        chkBox.setTag(alumno);



        return row;
    }

}
