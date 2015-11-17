package itesm.mx.proyectomoviles;

/**
 * Created by USUARIO on 17/11/2015.
 */
public class Alumnos {
    String name;
    boolean selected = false;

    Alumnos(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isSelected(){
        return selected;
    }

}
