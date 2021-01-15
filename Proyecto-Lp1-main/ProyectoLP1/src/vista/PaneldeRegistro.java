package vista;

import estructuras.Arte;
import estructuras.ManipulacionDeImagen;
import estructuras.Persistencia;
import expeciones.Campovacio;
import expeciones.Datosrepetido;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;
import static vista.PanelPrincipal.jTelaPrincipal;

public class PaneldeRegistro extends javax.swing.JInternalFrame {

    
    public PaneldeRegistro() {
        p.setupLer();
        if (p.getContinua() == true) {
            p.readRecords();
            p.cleanupLer();
            cad = p.getCad();
        }
        painelEditor = false;
        initComponents();
        getRootPane().setDefaultButton(jBSalvarCadastro);
    
    }
    public PaneldeRegistro(int tombo) {
        p.setupLer();
        if (p.getContinua() == true) {
            p.readRecords();
            p.cleanupLer();
            cad = p.getCad();

        }
        painelEditor = true;
        t = tombo;
        initComponents();
        preenche_campos(tombo);
        getRootPane().setDefaultButton(jBSalvarCadastro);
        
        
    }
    private boolean existeNaListaTombo(int tomb) {
        for (Arte a : p.getCad()) {
            if (a.getTombo() == tomb) {
                return true;
            }
        }
        return false;
    }
    private boolean registro_repetido(String a, String b) {
        for (Arte c : cad) {
            if ((a.equals(c.getRegistro())) && (!b.equals(c.getNombre()))) {
                return true;
            }
        }
        return false;
    }
    private boolean teste_dos_campos() {
        String txtTitulo = txtTituloObra.getText();
        String txtNome = txtNomeArtista.getText();
        String txtOrigem = txtOrigemProcedencia.getText();
        String txtCategoria = (String) cbCategoria.getSelectedItem();
        String txtProcedencia = (String) cbProcedencia.getSelectedItem();
        boolean semImagem = false;
        
        try{
            if(!checkbIndeterminado.isSelected()){
                int txtPeriodo = Integer.parseInt(txtTempoPeriodoProducao.getText());
            }
            int txtRegistro = Integer.parseInt(txtResArtista.getText());
            int txtAno = Integer.parseInt(formatTxtAno.getText());
        }catch(NumberFormatException n){
            return true;
        }
        if (labelImagem1.getIcon() == null) {
            semImagem = true;
        }

        if (txtTitulo.equals("") || txtNome.equals("")
                || txtOrigem.equals("") || txtCategoria.equals("Selecione")
                || semImagem || txtProcedencia.equals("Selecione")) {

            return true;
        } else {
            return false;
        }
    }
    private void limpa_campos() {
        txtNomeArtista.setText("");
        txtOrigemProcedencia.setText("");
        txtResArtista.setText("");
        txtTempoPeriodoProducao.setText("");
        txtTituloObra.setText("");
        formatTxtAno.setText("");
        checkbIndeterminado.setSelected(false);
        cbCategoria.setSelectedIndex(0);
        cbProcedencia.setSelectedIndex(0);
        labelImagem1.setIcon(null);
        jCheckBox1.setSelected(false);
    }
    private void preenche_campos(int tombo) {
        for (Arte a : cad) {
            if (a.getTombo() == tombo) {
                txtNomeArtista.setText(a.getNombre());
                txtOrigemProcedencia.setText(a.getOrigen());
                txtResArtista.setText(a.getRegistro());
                txtTituloObra.setText(a.getTitulo());
                formatTxtAno.setText(String.valueOf(a.getAño()));
                checkbIndeterminado.setSelected(a.getValorIndeterminado());
                cbCategoria.setSelectedIndex(a.getIndiceCategoria());
                cbProcedencia.setSelectedIndex(a.getIndiceProcedencia());
                ManipulacionDeImagen.exibiImagemLabel(a.getImagen(), labelImagem1);
                imagemAUX = a.getImagen();
                if (Arrays.equals(imagemAUX, vazioAUX)) {
                    jCheckBox1.setSelected(true);
                    jBSelectImagem.setEnabled(false);
                }
                if (a.getValorIndeterminado()) {
                    txtTempoPeriodoProducao.setText("");
                    txtTempoPeriodoProducao.setEnabled(false);
                } else {
                    txtTempoPeriodoProducao.setText(a.getPeriodoProducido());
                }
            }
        }
    }
    public static DefaultFormatterFactory setFormatoAno() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("####");
        } catch (Exception pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    public static DefaultFormatterFactory setFormatoRegistro() {
        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("########");
        } catch (Exception pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }
    private Arte retorna_arte() {
        String txtPeriodo = txtTempoPeriodoProducao.getText();

        try {
            if (teste_dos_campos()) {
                throw new Campovacio();
            }

            if (registro_repetido(txtResArtista.getText(), txtNomeArtista.getText())) {
                throw new Datosrepetido();
            }

            if (txtPeriodo.equals("") || checkbIndeterminado.isSelected()) {
                checkbIndeterminado.isSelected();
            }
            
            Arte obra = new Arte(
                    txtTituloObra.getText(),
                    Integer.parseInt(formatTxtAno.getText()),
                    String.valueOf(cbCategoria.getSelectedItem()),
                    txtPeriodo,
                    String.valueOf(cbProcedencia.getSelectedItem()) ,
                    txtOrigemProcedencia.getText(),
                    ManipulacionDeImagen.getImgBytes(img),
                    txtNomeArtista.getText(),
                    txtResArtista.getText()); 
                    
                     
                    

            obra.setIndiceCategoria(cbCategoria.getSelectedIndex());
            obra.setIndiceProcedencia(cbProcedencia.getSelectedIndex());
            obra.setValorIndeterminado(checkbIndeterminado.isSelected());

            return obra;

        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(rootPane, "No fue posible registrar la obra de arte, ya que uno de los" + "\n"
                    + "los valores registrados no son válidos.                     ",
                    "Aviso", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Campovacio n) {
            JOptionPane.showMessageDialog(rootPane, "No fue posible registrar la obra de arte, ya que uno de los" + "\n"
                    + "Campos obligatorios no completados o seleccionados.",
                    "Aviso", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Datosrepetido n) {
            JOptionPane.showMessageDialog(rootPane, "No fue posible registrar la obra de arte, ya que" + "\n"
                    + "El registro de artista ya se ha registrado a nombre de " + "\n"
                    + "de otro artista.", "Aviso", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    public void setPosicao() {
        Dimension d = jTelaPrincipal.getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }
    private void fecha_janela() {
        this.dispose();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelCadastro = new javax.swing.JPanel();
        labelImagem1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jBSelectImagem = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        txtTituloObra = new javax.swing.JTextField();
        txtTempoPeriodoProducao = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNomeArtista = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cbProcedencia = new javax.swing.JComboBox<>();
        txtOrigemProcedencia = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        checkbIndeterminado = new javax.swing.JCheckBox();
        jBSalvarCadastro = new javax.swing.JButton();
        jBCancelarCadastro = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        formatTxtAno = new javax.swing.JFormattedTextField();
        txtResArtista = new javax.swing.JFormattedTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setMinimumSize(new java.awt.Dimension(57, 34));
        setNormalBounds(new java.awt.Rectangle(0, 0, 57, 0));

        labelImagem1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Atribuir Imagen");

        jBSelectImagem.setText("Seleccione imagen");
        jBSelectImagem.setToolTipText("Haga click para buscar la imagen");
        jBSelectImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSelectImagemActionPerformed(evt);
            }
        });

        jLabel1.setText("Titulo de la Obra");

        jLabel4.setText("Periodo de Produccion");

        jLabel5.setText("Año de produccion");

        jLabel6.setText("Categoría");

        jLabel7.setText("Nombre del Artista");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("Información del Artista");

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Álbum", "Acuarela", "collage", "Dibujo", "Escultura", "Fotografía", "Grabado", "Impresión", "Instalación", "Libro", "Múltiple", "Objeto", "WorkDesinc", "Realizar", "Pintura", "Alivio", "Tapiz", "Vídeo" }));
        cbCategoria.setToolTipText("Seleccione la Categoría en la que encaja el Trabajo.");

        txtTituloObra.setToolTipText("Digite el titulo de la obra");

        txtTempoPeriodoProducao.setToolTipText("Digite el periodo de la produccion");

        jLabel9.setText("Registro del Artista");

        txtNomeArtista.setToolTipText("Digite el nombre del artista");

        jLabel10.setText("Origen de procedencia");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("Información de procedencia");

        jLabel12.setText("Tipo de procedencia");

        cbProcedencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Adquisición", "Préstamo", "Donación", "Botín", "Legado", "Prima" }));
        cbProcedencia.setToolTipText("Seleccione el tipo de procedencia");

        txtOrigemProcedencia.setToolTipText("Digite el origen de procedencia");

        jCheckBox1.setText("Imagen no disponible");
        jCheckBox1.setToolTipText("Haga click si no tiene una imagen");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        checkbIndeterminado.setText("Indeterminado");
        checkbIndeterminado.setToolTipText("Seleccione si se desconoce el periodo de la produccion");
        checkbIndeterminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkbIndeterminadoActionPerformed(evt);
            }
        });

        jBSalvarCadastro.setText("Guardar");
        jBSalvarCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalvarCadastroActionPerformed(evt);
            }
        });

        jBCancelarCadastro.setText("Cancelar");
        jBCancelarCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarCadastroActionPerformed(evt);
            }
        });

        jLabel2.setText("* Todos los campos deben ser completados o seleccionados.");

        jLabel13.setText(" * La caída de tu Arte se generará automáticamente al guardar.");

        formatTxtAno.setFormatterFactory(setFormatoAno());
        formatTxtAno.setToolTipText("Digite el año de produccion de la obra");

        txtResArtista.setFormatterFactory(setFormatoRegistro());
        txtResArtista.setToolTipText("Ingrese el registro profesional del artista.");
        txtResArtista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtResArtistaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCadastroLayout = new javax.swing.GroupLayout(jPanelCadastro);
        jPanelCadastro.setLayout(jPanelCadastroLayout);
        jPanelCadastroLayout.setHorizontalGroup(
            jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadastroLayout.createSequentialGroup()
                        .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOrigemProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtResArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomeArtista, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel13)
                    .addGroup(jPanelCadastroLayout.createSequentialGroup()
                        .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(43, 43, 43)
                        .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelCadastroLayout.createSequentialGroup()
                                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTempoPeriodoProducao)
                                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkbIndeterminado))
                            .addComponent(txtTituloObra, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(formatTxtAno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel10)
                    .addGroup(jPanelCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBSalvarCadastro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBCancelarCadastro))
                    .addGroup(jPanelCadastroLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCadastroLayout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelCadastroLayout.createSequentialGroup()
                            .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jBSelectImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(labelImagem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelCadastroLayout.createSequentialGroup()
                            .addGap(195, 195, 195)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelCadastroLayout.setVerticalGroup(
            jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadastroLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCadastroLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBSelectImagem)
                        .addGap(0, 84, Short.MAX_VALUE))
                    .addComponent(labelImagem1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTituloObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtTempoPeriodoProducao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkbIndeterminado))
                .addGap(18, 18, 18)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(formatTxtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomeArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(23, 23, 23)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtResArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(10, 10, 10)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtOrigemProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cbProcedencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelCadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jBSalvarCadastro)
                    .addComponent(jBCancelarCadastro))
                .addGap(10, 10, 10)
                .addComponent(jLabel13)
                .addContainerGap())
        );

        jBSelectImagem.getAccessibleContext().setAccessibleDescription("");
        txtTituloObra.getAccessibleContext().setAccessibleDescription("");
        txtTempoPeriodoProducao.getAccessibleContext().setAccessibleDescription("");
        txtNomeArtista.getAccessibleContext().setAccessibleName("");
        txtNomeArtista.getAccessibleContext().setAccessibleDescription("");
        txtOrigemProcedencia.getAccessibleContext().setAccessibleName("");
        txtOrigemProcedencia.getAccessibleContext().setAccessibleDescription("");
        jLabel13.getAccessibleContext().setAccessibleName(" * El archivo de tu  Arte se generará automáticamente al guardar.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelCadastro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            img = ManipulacionDeImagen.setImagenDimension("src\\imagens\\not_available.jpg", 160, 160);
            imagemAUX = ManipulacionDeImagen.getImgBytes(img);
            labelImagem1.setIcon(new ImageIcon(img));
            jBSelectImagem.setEnabled(false);
        } else {
            jBSelectImagem.setEnabled(true);
            labelImagem1.setIcon(null);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jBSalvarCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalvarCadastroActionPerformed
        if (painelEditor == false) {
            Arte verifica = retorna_arte();
            if (null != verifica) {
                Arte a = verifica;
                if (!existeNaListaTombo(a.getTombo())) {
                    p.getCad().add(a);
                    p.setupGravar();
                    p.addRecords(cad);
                    p.cleanupGravar();

                    JOptionPane.showMessageDialog(rootPane, "Arte registrado con éxito.\n"
                            + "Tombo generado:" + a.getTombo());

                    limpa_campos();
                    PaneldePregunta painelPer = new PaneldePregunta();
                    painelPer.setTitle("Registrar obra de arte");
                    jTelaPrincipal.add(painelPer);
                    painelPer.setPosicao();
                    fecha_janela();
                    painelPer.setVisible(true);
                    PaneldeBusqueda.atualizacao_Instantanea();
                 
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Archivo de arte ya registrado.", "Aviso", JOptionPane.ERROR_MESSAGE);
                }
            }

        }

        if (painelEditor == true) {
            try {
            if (teste_dos_campos()) {
                throw new Campovacio();
            }

            if (registro_repetido(txtResArtista.getText(), txtNomeArtista.getText())) {
                throw new Datosrepetido();
            }
                        
               for (Arte e : cad) {
                if (t == e.getTombo()) {
                    String txtPeriodo = txtTempoPeriodoProducao.getText();
                    e.setTitulo(txtTituloObra.getText());
                    e.setAño(Integer.parseInt(formatTxtAno.getText()));
                    if (checkbIndeterminado.isSelected()) {
                        e.setPeriodoProducido("Indeterminado");
                    } else {
                        e.setPeriodoProducido(txtTempoPeriodoProducao.getText());
                    }
                    e.setCategoria(String.valueOf(cbCategoria.getSelectedItem()));
                    e.setNombre(txtNomeArtista.getText());
                    e.setRegistro(txtResArtista.getText());
                    e.setProcedencia(String.valueOf(cbProcedencia.getSelectedItem()));
                    e.setOrigen(txtOrigemProcedencia.getText());
                    e.setImagen(imagemAUX);
                    e.setIndiceCategoria(cbCategoria.getSelectedIndex());
                    e.setIndiceProcedencia(cbProcedencia.getSelectedIndex());
                    e.setValorIndeterminado(checkbIndeterminado.isSelected());
                    p.setupGravar();
                    p.addRecords(cad);
                    p.cleanupGravar();
                    
                    
                    JOptionPane.showMessageDialog(rootPane, "Producto editado correctamente.");
                    limpa_campos();
                    PaneldeBusqueda.atualizacao_Instantanea();
                    fecha_janela();
                     }
            }
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(rootPane, "No fue posible registrar la obra de arte, ya que uno de los" + "\n"
                    + "los valores registrados no son válidos.                     ",
                    "Aviso", JOptionPane.ERROR_MESSAGE);
            
            } catch (Campovacio n) {
                JOptionPane.showMessageDialog(rootPane, "No fue posible registrar la obra de arte, ya que uno de los" + "\n"
                    + "Campos obligatorios no completados o seleccionados.",
                    "Aviso", JOptionPane.ERROR_MESSAGE);
            
            } catch (Datosrepetido n) {
                JOptionPane.showMessageDialog(rootPane, "No fue posible registrar la obra de arte, ya que" + "\n"
                    + "El registro de artista ya se ha registrado a nombre de " + "\n"
                    + "de otro artista.", "Aviso", JOptionPane.ERROR_MESSAGE);
            
            }
            
        }
    }//GEN-LAST:event_jBSalvarCadastroActionPerformed

    private void jBCancelarCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarCadastroActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBCancelarCadastroActionPerformed

    private void jBSelectImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSelectImagemActionPerformed
         if (jCheckBox1.isSelected() == false) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Imagem", "jpg", "png");
            jBusca.setFileFilter(filter);
            if (jBusca.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File imagem = jBusca.getSelectedFile();
                try {
                    img = ManipulacionDeImagen.setImagenDimension(imagem.getAbsolutePath(), 160, 160);
                    imagemAUX = ManipulacionDeImagen.getImgBytes(img);
                    labelImagem1.setIcon(new ImageIcon(img));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, "Arquivo Inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado ningún archivo.");
            }
        }
    }//GEN-LAST:event_jBSelectImagemActionPerformed

    private void checkbIndeterminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkbIndeterminadoActionPerformed
          if (checkbIndeterminado.isSelected()) {
            txtTempoPeriodoProducao.setEnabled(false);
        } else {
            txtTempoPeriodoProducao.setEnabled(true);
        }
    }//GEN-LAST:event_checkbIndeterminadoActionPerformed

    private void txtResArtistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtResArtistaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtResArtistaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JComboBox<String> cbProcedencia;
    private javax.swing.JCheckBox checkbIndeterminado;
    private javax.swing.JFormattedTextField formatTxtAno;
    private javax.swing.JButton jBCancelarCadastro;
    private javax.swing.JButton jBSalvarCadastro;
    private javax.swing.JButton jBSelectImagem;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanelCadastro;
    private javax.swing.JLabel labelImagem1;
    private javax.swing.JTextField txtNomeArtista;
    private javax.swing.JTextField txtOrigemProcedencia;
    private javax.swing.JFormattedTextField txtResArtista;
    private javax.swing.JTextField txtTempoPeriodoProducao;
    private javax.swing.JTextField txtTituloObra;
    // End of variables declaration//GEN-END:variables
private static Persistencia p = new Persistencia();
    private LinkedList<Arte> cad = new LinkedList<Arte>();
    private static boolean painelEditor = false;
    private static int t = 0;
    JFileChooser jBusca = new JFileChooser();
    private BufferedImage img;
    private BufferedImage imagemVazia = ManipulacionDeImagen.setImagenDimension("src\\imagens\\Not_available.jpg", 160, 160);
    private byte[] imagemAUX, vazioAUX = ManipulacionDeImagen.getImgBytes(imagemVazia);
    //Fim dos Atribudos;
}
