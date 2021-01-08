package estructuras;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ManipulacionDeImagen {
    public static BufferedImage setImagenDimension(String caminoImg, Integer imgLargo, Integer imgAltura) {
        Double nuevaImgLargo = null;
        Double nuevaImgAltura = null;
        Double imgProporcion = null;
        Graphics2D g2d = null;
        BufferedImage imagem = null, nuevaImagem = null;

        try {
            //--- Obtém a imagem a ser redimensionada ---
            imagem = ImageIO.read(new File(caminoImg));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ManipulacionDeImagen.class.getName()).log(Level.SEVERE, null, ex);
        }

        //--- Obten el largo de la imagen ---  
        nuevaImgLargo = (double) imagem.getWidth();

        //--- Obtám a altura da imagem ---  
        nuevaImgAltura = (double) imagem.getHeight();

    //--- Verifica se a altura ou largura da imagem recebida é maior do que os ---  
        //--- parâmetros de altura e largura recebidos para o redimensionamento   ---  
        if (nuevaImgLargo >= imgLargo) {
            imgProporcion = (nuevaImgAltura / nuevaImgLargo);//calcula a proporção  
            nuevaImgLargo = (double) imgLargo;

            //--- altura deve <= ao parâmetro imgAltura e proporcional a largura ---  
            nuevaImgAltura = (nuevaImgLargo * imgProporcion);

        //--- se altura for maior do que o parâmetro imgAltura, diminui-se a largura de ---  
            //--- forma que a altura seja igual ao parâmetro imgAltura e proporcional a largura ---  
            while (nuevaImgAltura > imgAltura) {
                nuevaImgLargo = (double) (--imgLargo);
                nuevaImgAltura = (nuevaImgLargo * imgProporcion);
            }
        } else if (nuevaImgAltura >= imgAltura) {
            imgProporcion = (nuevaImgLargo / nuevaImgAltura);//calcula a proporção  
            nuevaImgAltura = (double) imgAltura;

        //--- se largura for maior do que o parâmetro imgLargura, diminui-se a altura de ---  
            //--- forma que a largura seja igual ao parâmetro imglargura e proporcional a altura ---  
            while (nuevaImgLargo > imgLargo) {
                nuevaImgAltura = (double) (--imgAltura);
                nuevaImgLargo = (nuevaImgAltura * imgProporcion);
            }
        }

        nuevaImagem = new BufferedImage(nuevaImgLargo.intValue(), nuevaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
        g2d = nuevaImagem.createGraphics();
        g2d.drawImage(imagem, 0, 0, nuevaImgLargo.intValue(), nuevaImgAltura.intValue(), null);

        return nuevaImagem;
    }

    public static byte[] getImgBytes(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "JPEG", baos);
        } catch (Exception ex) {
            baos.reset();
        }
        
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        
        return baos.toByteArray();
    }
    //Novo método para exibir imagem na tela
    //Recebe o label que queremos exibir E a imagem como array de bytes do banco
    public static void exibiImagemLabel(byte[] minhaimagem, javax.swing.JLabel label)
{
        //primeiro verifica se tem a imagem
        //se tem convert para inputstream que é o formato reconhecido pelo ImageIO
       
        if(minhaimagem!=null)
        {
            InputStream input = new ByteArrayInputStream(minhaimagem);
            try {
                BufferedImage imagem = ImageIO.read(input);
                label.setIcon(new ImageIcon(imagem));
            } catch (IOException ex) {
            }
            
        
        }
        else
        {
            label.setIcon(null);
            
        }

}
}
