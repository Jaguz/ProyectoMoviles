package itesm.mx.proyectomoviles;

/**
 * Created by USUARIO on 26/10/2015.
 */
public class Proyecto {

    private String nombre;
    private String lugar;

    public Proyecto (){
        super();
    }

    public Proyecto(String nombre, String lugar){
        this.nombre = nombre;
        this.lugar = lugar;
    }

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre= nombre;
    }
    public String getLugar(){
        return lugar;
    }
    public void setLugar(String lugar){
        this.lugar=lugar;
    }
}
