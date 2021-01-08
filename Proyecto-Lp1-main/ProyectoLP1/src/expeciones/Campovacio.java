package expeciones;

public class Campovacio extends Exception{

    public Campovacio() {
        super("Uno de los campos de texto tenía un nombre repetido");
    }

    
    
}
