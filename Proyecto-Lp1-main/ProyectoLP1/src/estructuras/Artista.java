package estructuras;

import java.io.Serializable;

public class Artista implements Serializable{
    protected String nombre;
    protected String registro;
    //constructor

    public Artista(String nombre, String registro) {
        this.nombre = nombre;
        this.registro = registro;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRegistro() {
        return registro;
    }
    //INFORME DE DATOS
    public String toString(){
        return "Nombre:" + nombre + '\n' + "Registro Artístico:" + registro;
    }
    
    
    
}
