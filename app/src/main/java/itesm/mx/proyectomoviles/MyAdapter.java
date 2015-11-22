package itesm.mx.proyectomoviles;

/**
 * Created by Javier on 11/17/2015.
 */
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Model> {

    private final List<Model> list;
    private final Activity context;
    boolean checkAll_flag = false;
    boolean checkItem_flag = false;
    public int counter=0;

    public MyAdapter(Activity context, List<Model> list) {
        super(context, R.layout.rowasistencia, list);
        this.context = context;
        this.list = list;
        this.counter=0;
    }

    static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.rowasistencia, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.nameTV);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    list.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    if(isChecked) counter++;
                    else counter--;
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.nameTV, viewHolder.text);
            convertView.setTag(R.id.checkBox1, viewHolder.checkbox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkbox.setTag(position); // This line is important.

        viewHolder.text.setText(list.get(position).getName());
        viewHolder.checkbox.setChecked(list.get(position).isSelected());

        return convertView;
    }
}
