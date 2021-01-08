package expeciones;

public class Datosrepetido extends Exception{

    public Datosrepetido() {
        super("Uno de los campos de texto tenía un nombre repetido");
    }
    
}
