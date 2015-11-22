package itesm.mx.proyectomoviles;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USUARIO on 17/11/2015.
 */
public class AlumnosAdapter extends ArrayAdapter<Alumnos> {
    int layoutResourceId;
    List<Alumnos> alumnosList;
    CheckBox chkBox;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;
    private final Activity context;
    ArrayAdapter<Model> adapter;
    List<Model> list = new ArrayList<Model>();

    public AlumnosAdapter(Activity context, List<Alumnos> alumnosList) {
        super(context, R.layout.rowasistencia, alumnosList);
        this.context = context;
        this.alumnosList = alumnosList;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = null;
        if (row == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.row, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.nameTV);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    alumnosList.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.nameTV, viewHolder.text);
            convertView.setTag(R.id.checkBox1, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.text.setText(alumnosList.get(position).getName());
        viewHolder.checkbox.setChecked(alumnosList.get(position).isSelected());

        return convertView;
    }

}
