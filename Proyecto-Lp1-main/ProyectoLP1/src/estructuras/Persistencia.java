package estructuras;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Persistencia implements Serializable{
    //atributos
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean continua = true;
    private boolean moreRecords = true;
    private LinkedList<Arte> cad = new LinkedList<Arte>();
    //metodos
    public boolean getContinua() {
        return continua;
    }    
    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public boolean isContinua() {
        return continua;
    }

    public boolean isMoreRecords() {
        return moreRecords;
    }

    public LinkedList<Arte> getCad() {
        return cad;
    }
    public void setupLer() {
        try {
            FileInputStream leitura = new FileInputStream("Arte.ser");
            input = new ObjectInputStream(leitura);
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Archivo de salvamento inexistente," + "\n" +
                                                "se creo un nuevio archivo.", "Informe", JOptionPane.INFORMATION_MESSAGE);
            setupGravar();
            addRecords(cad);
            cleanupGravar();
        } catch (EOFException eof) {
            continua = false;           //Se o arquivo estiver vazio.
        } catch (IOException e) {
            System.err.println("Error al abrir el archivo para su lectura\n" + e.toString());
            System.exit(1);
        }

    }
    public void readRecords() {


       try{
           cad = (LinkedList<Arte>) input.readObject();
        } catch(NullPointerException a){
        }catch (IOException e) {
            System.err.println("Error al leer el archivo\n" + e.toString());
            System.exit(1);
        } catch (ClassNotFoundException c) {
            System.err.println("Error al leer el archivo - Objeto Inválido\n" + c.toString());
        }
    }
    public void cleanupLer() {

        try {
            input.close();
        }catch(NullPointerException a){
        }catch (IOException e) {
            System.err.println("Error al cerrar el archivo durante la lectura\n" + e.toString());
            System.exit(1);
        }
    }
    public void setupGravar() {

        try {
            FileOutputStream escrita = new FileOutputStream("Arte.ser");
            output = new ObjectOutputStream(escrita);
        } catch (IOException e) {
            System.err.println("Error al abrir el archivo para la grabación\n" + e.toString());
            System.exit(1);
        }
    }
    public void addRecords(LinkedList<Arte> cad) {

        try {
                output.writeObject(cad);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo\n" + e.toString());
            System.exit(1);
        }
    }
    public void cleanupGravar() {

        try {
            output.flush();
            output.close();
        } catch (IOException e) {
            System.err.println("Error al cerrar el archivo: durante la grabación!!\n" + e.toString());
            System.exit(1);
        }

    }
    public Arte getArte(int x) {
        return cad.get(x);
    }
}
