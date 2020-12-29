
package estructuras;

import java.util.LinkedList;

public class TomboSalvo {
    //Atributos:
    private static Persistencia p = new Persistencia();
    private LinkedList<Arte> cad = new LinkedList<>();
    private int tombo;
    private static int tomboAux = 0;
    
   //Métodos:
     /** Método que Pega o Número de Tombo Adquirido
      *    Descrição:
      *      O método lê a coleção de objetos salvos no arquivo e retorna o maior
      *      número de tombo salvo.
      */
    public int getTomboSalvo() {
        p.setupLer();
        p.readRecords();
        p.cleanupLer();
        cad = p.getCad();

        for (Arte a : cad) {
            tombo = a.getTombo();
            if (tombo > tomboAux) {
                tomboAux = tombo;
            }
        }

        return tomboAux;
    }
}
