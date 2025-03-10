/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import logica.Empresa;
import logica.EmpresaController;
import logica.Turno;

/**
 *
 * @author Ayrto
 */
public class Whatsapp extends javax.swing.JFrame {

    private EmpresaController empresaController = null;
    
    private Turno turno;
    private Empresa empresa;
    public Whatsapp(Turno turno, Empresa empresa) {
        empresaController = new EmpresaController();
        this.turno=turno;
        this.empresa=empresa;
        initComponents();
        setIconImage(getIconImage());
        txtCelular.setText(this.getTurno().getCliente().getCelular());
        txtTexto.setText("¡Hola "+this.getTurno().getCliente().getNombre()+"!"+this.getEmpresa().getTxtWhatsapp());
    }
    
    //ICONO DEL JFRAME
    @Override
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/logo.png"));
        return retValue;
    }
    private Turno getTurno(){
        return this.turno;
    }
    private Empresa getEmpresa(){
        return this.empresa;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTexto = new javax.swing.JTextArea();
        btnWhatsapp = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mensaje de WhatsApp");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(232, 232, 232));

        jLabel29.setBackground(new java.awt.Color(37, 37, 37));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(37, 37, 37));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Mensaje de WhatsApp");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(37, 37, 37));
        jLabel1.setText("Celular (solo numeros con codigo de area)");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(37, 37, 37));
        jLabel2.setText("Texto");

        txtTexto.setColumns(20);
        txtTexto.setLineWrap(true);
        txtTexto.setRows(5);
        txtTexto.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtTexto);

        btnWhatsapp.setText("Enviar");
        btnWhatsapp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWhatsappMouseClicked(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnWhatsapp, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1)
                        .addComponent(txtCelular)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnWhatsapp, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnWhatsappMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWhatsappMouseClicked
        try{
            String celular = txtCelular.getText();
            String texto = txtTexto.getText();
            if(empresaController.enviarWhatsApp(celular, texto)){
                JOptionPane.showMessageDialog(rootPane, "Abriendo WhatsApp...", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                
            }else{
                 JOptionPane.showMessageDialog(rootPane, "No se pudo enviar el WhatsApp al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.dispose();
        }catch(Exception e){
            System.out.println("Error al querer enviar el mensaje de WhatsApp en el front: "+e);
        }
    }//GEN-LAST:event_btnWhatsappMouseClicked

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        try{
            int valor = JOptionPane.showConfirmDialog(rootPane, "¿Desea cancelar el envio del WhatsApp?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
            if(valor==0){
                this.dispose();
            }
        }catch(Exception e){
            System.out.println("Error al descartar los cambios: "+e);
        }
    }//GEN-LAST:event_btnCancelarMouseClicked

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnWhatsapp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextArea txtTexto;
    // End of variables declaration//GEN-END:variables
}
