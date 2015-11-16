package itesm.mx.proyectomoviles;

/**
 * Created by USUARIO on 26/10/2015.
 */
public class Proyecto {

    private String incubadora;
    private String espacio;
    private String proyecto;

    public Proyecto (){
        super();
    }

    public Proyecto(String incubadora, String lugar, String proyecto){
        this.incubadora = incubadora;
        this.espacio = lugar;
        this.proyecto = proyecto;
    }

    public String getIncubadora(){
        return incubadora;
    }
    public void setIncubadora(String incubadora){
        this.incubadora = incubadora;
    }
    public String getEspacio(){
        return espacio;
    }
    public void setEspacio(String espacio){
        this.espacio=espacio;
    }
    public String getProyecto(){
        return proyecto;
    }
    public void setProyecto(String proyecto){
        this.proyecto=proyecto;
    }

}
