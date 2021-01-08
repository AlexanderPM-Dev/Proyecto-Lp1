package estructuras;

import java.io.Serializable;

public class Arte extends Artista implements Serializable{
    //Atributos Esenciales
    public String titulo;
    private int año;
    private String categoria;
    private String PeriodoProducido;
    private String Procedencia;
    private String origen;
    private int tombo=0;
    //Atributos auxiliares
    private int indiceCategoria=0;
    private int indiceProcedencia=0;
    private boolean valorIndeterminado;
    private static int AUX=1;
    byte[] imagen;
    
    //constructor

    public Arte(String titulo, int año, String categoria, String PeriodoProducido, String Procedencia, 
            String origen, byte[] imagen, String nombre, String registro) {
        super(nombre, registro);
        this.titulo = titulo;
        this.año = año;
        this.categoria = categoria;
        this.PeriodoProducido = PeriodoProducido;
        this.Procedencia = Procedencia;
        this.origen = origen;
        this.imagen = imagen;
        TomboSalvo tomboSalvo = new TomboSalvo();
        if(tomboSalvo.getTomboSalvo() > AUX){
            AUX = 1 + tomboSalvo.getTomboSalvo();
        }
        if(tomboSalvo.getTomboSalvo() == 1){
            AUX = 2;
        }
        this.tombo = AUX;  
        AUX+=1;
    }

    

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPeriodoProducido(String PeriodoProducido) {
        this.PeriodoProducido = PeriodoProducido;
    }

    public void setProcedencia(String Procedencia) {
        this.Procedencia = Procedencia;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setTombo(int tombo) {
        this.tombo = tombo;
    }

    public void setIndiceCategoria(int indiceCategoria) {
        this.indiceCategoria = indiceCategoria;
    }

    public void setIndiceProcedencia(int indiceProcedencia) {
        this.indiceProcedencia = indiceProcedencia;
    }

 
    public static void setAUX(int AUX) {
        Arte.AUX = AUX;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAño() {
        return año;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPeriodoProducido() {
        return PeriodoProducido;
    }

    public String getProcedencia() {
        return Procedencia;
    }

    public String getOrigen() {
        return origen;
    }

    public int getTombo() {
        return tombo;
    }

    public int getIndiceCategoria() {
        return indiceCategoria;
    }

    public int getIndiceProcedencia() {
        return indiceProcedencia;
    }

    public boolean getValorIndeterminado(){
        return valorIndeterminado;
    }

    public void setValorIndeterminado(boolean valorIndeterminado){
        this.valorIndeterminado = valorIndeterminado;
    }

    public static int getAUX() {
        return AUX;
    }

    public byte[] getImagen() {
        return imagen;
    }
    
    //INFOPRME DE DATOS
    public String toString(){
        return "Título da Obra:" + titulo + '\n' + super.toString() + '\n' + "Año de Producción:" + año + '\n' + "Categoría:" + categoria + '\n' +
               "Período de Producción:" + PeriodoProducido + '\n' + "Procedencia" + Procedencia + '\n' + "Origen da Procedencia:" + origen + '\n' + "Número de Tombo:" + tombo;
    }  
}

