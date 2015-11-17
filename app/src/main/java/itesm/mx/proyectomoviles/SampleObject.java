package itesm.mx.proyectomoviles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javier on 11/16/2015.
 */
public class SampleObject {
    String header1;
    public List<String> asistencias = new ArrayList<String>();

    public SampleObject(String header1, List<String> asistencias){

        this.header1 = header1;
        this.asistencias = asistencias;
    }
}