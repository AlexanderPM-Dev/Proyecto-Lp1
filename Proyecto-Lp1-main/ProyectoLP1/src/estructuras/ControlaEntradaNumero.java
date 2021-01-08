
package estructuras;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ControlaEntradaNumero extends PlainDocument{
    private boolean numero;


    public ControlaEntradaNumero(boolean isNumber) {
        super();
        numero = isNumber;
    }
    @Override
    public void insertString(int offs, String str, AttributeSet attribute)
            throws BadLocationException
    {
        if(numero == true)
        {
            for (char c : str.toCharArray())
            {
                if(!Character.isDigit(c))
                {
                    return;
                }
            }
        } 
        super.insertString(offs, str.toUpperCase(), attribute);     
    }
    
}
