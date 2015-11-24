package itesm.mx.proyectomoviles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Javier on 11/16/2015.
 */
public class SampleObject {
    public String header1;
    public String header2;
    public String header3;
    public List<String> asistencias = new ArrayList<String>();

    public SampleObject(String header1, String header2, String header3, List<String> asistencias){

        this.header1 = header1;
        this.header2 = header2;
        this.header3 = header3;
        this.asistencias = asistencias;
    }
}