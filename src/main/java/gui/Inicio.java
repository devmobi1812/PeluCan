/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import logica.Cliente;
import logica.ClienteController;
import logica.Empresa;
import logica.EmpresaController;
import logica.Mascota;
import logica.MascotaController;
import logica.Turno;
import logica.TurnoController;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.ImageIcon;

/**
 *
 * @author Ayrto
 */
public class Inicio extends javax.swing.JFrame {

    ClienteController clienteController = null;
    MascotaController mascotaController = null;
    TurnoController turnoController = null;
    EmpresaController empresaController = null;
    
    Mascota mascotaEditar=null;
    Cliente clienteEditar=null;
    Turno turnoEditar=null;
    Empresa empresaEditar=null;
    public Inicio() {
        try {
            clienteController = new ClienteController();
            mascotaController = new MascotaController();
            turnoController = new TurnoController();
            empresaController = new EmpresaController();
            
            UIManager.setLookAndFeel(new FlatLightLaf());
            initComponents();
            setIconImage(getIconImage());
            datosIniciales();
            agregarListeners();
            setComboBox();
            
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        cargarPantalla(this.panelInicial);
    }
    
    //ICONO DEL JFRAME
    @Override
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/logo.png"));
        return retValue;
    }

    private void setComboBox(){
        try{
            AutoCompleteDecorator.decorate(this.cmbClientesTurno);
            AutoCompleteDecorator.decorate(this.cmbClientesTurnoEditar);
            AutoCompleteDecorator.decorate(this.cmbDuenioMascota);
            AutoCompleteDecorator.decorate(this.cmbDuenioMascotaEditar);
            AutoCompleteDecorator.decorate(this.cmbMascotaTurno);
            AutoCompleteDecorator.decorate(this.cmbMascotaTurnoEditar);
            AutoCompleteDecorator.decorate(this.cmbSexoMascota);
            AutoCompleteDecorator.decorate(this.cmbSexoMascotaEditar); 
        }catch(Exception e){
            System.out.println("Error al cargar los combobox para poder buscar en los mismos");
        }
        
    }
    private void setMascotaEditar(Mascota mascota){
        this.mascotaEditar = mascota;
    }
    
    private Mascota getMascotaEditar(){
        return this.mascotaEditar;
    }
    
    private void setClienteEditar(Cliente cliente){
        this.clienteEditar=cliente;
    }
    
    private Cliente getClienteEditar(){
        return this.clienteEditar;
    }
    
    private void setTurnoEditar(Turno turno){
        this.turnoEditar = turno;
    }
    
    private Turno getTurnoEditar(){
        return this.turnoEditar;
    }
    
    private void setEmpresaEditar(Empresa empresa){
        this.empresaEditar=empresa;
    }
    
    private Empresa getEmpresaEditar(){
        return this.empresaEditar;
    }
    
    private void datosIniciales(){
        try{
            String cantidadTurnosHoy = String.valueOf(turnoController.getTurnos(LocalDate.now()).size());
            String cantidadTurnosMan = String.valueOf(turnoController.getTurnos(LocalDate.now().plusDays(1)).size());
            String cantidadClientes = String.valueOf(clienteController.getClientes().size());
            String cantidadMascotas = String.valueOf(mascotaController.getMascotas().size());
            String cantidadTurnosHistory = String.valueOf(turnoController.getTurnos().size());
            
            
            txtDiaHoy.setText(String.valueOf(LocalDate.now()));
            txtCantTurnosHoy.setText(cantidadTurnosHoy);
            txtCantTurnosMan.setText(cantidadTurnosMan);
            txtCantClientes.setText(cantidadClientes);
            txtCantMascotas.setText(cantidadMascotas);
            txtCantTurnosHistory.setText(cantidadTurnosHistory);
        }catch(Exception e){
            System.out.println("Error al cargar los datos del panel inicial en el front: "+e);
        }
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    private void agregarListeners() {
        //  PARA CARGAR LAS MASCOTAS SEGUN EL CLIENTE EN CREAR TURNO
        cmbClientesTurno.addActionListener((ActionEvent e) -> {
            try{
                String cliente = (String) cmbClientesTurno.getSelectedItem();
                int idCliente = cmbClientesTurno.getSelectedIndex();
                //  SI HAY UN SOLO ITEM, Y EL MISMO ES EL POR DEFAULT, DEBE CARGAR LOS CLIENTES DE LA BD
                if(cmbMascotaTurno.getItemCount() == 1 && cmbClientesTurno.getItemAt(0).equals("-")){
                    cargarMascotas(cmbMascotaTurno, cliente);
                }else{
                    cmbMascotaTurno.removeAllItems();
                    cmbMascotaTurno.addItem("-");
                    cargarMascotas(cmbMascotaTurno, cliente);
                }
            }catch(Exception ee){
                System.out.println("Error al cargar las mascotas del cliente seleccionado en el front: "+ee);
            }
        });
        
        //  PARA CARGAR LAS MASCOTAS SEGUN EL CLIENTE EN EDITAR TURNO
        cmbClientesTurnoEditar.addActionListener((ActionEvent e) -> {
            try{
                String clienteNombre = (String) cmbClientesTurnoEditar.getSelectedItem();
                if(cmbMascotaTurnoEditar.getItemCount() == 1 && cmbMascotaTurnoEditar.getItemAt(0).equals("-")){
                    cargarMascotas(cmbMascotaTurnoEditar, clienteNombre);
                }else{
                    cmbMascotaTurnoEditar.removeAllItems();
                    cmbMascotaTurnoEditar.addItem("-");
                    cargarMascotas(cmbMascotaTurnoEditar, clienteNombre);
                }
            }catch(Exception ee){
                System.out.println("Error al cargar las mascotas del cliente seleccionado a editar en el front: "+ee);
            }
        });
        
        
        //  PARA CARGAR LAS TURNOS SEGUN LA FECHA SELECCIONADA EN VER TURNOS
        dateTurnosTodos.addPropertyChangeListener("date", (PropertyChangeEvent evt) -> {
            try {
                Date fecha = dateTurnosTodos.getDate();
                if (fecha != null) {
                    LocalDate fechaSeleccionada = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    cargarTablaTurnos(fechaSeleccionada);
                }
            } catch (Exception e) {
                System.out.println("Error al cargar los turnos del día seleccionado en el front: " + e);
            }
        });
        
        
        
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        btnInicio = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnCrearTurno = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnVerTurnos = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnCrearCliente = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnVerClientes = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnCrearMascota = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnVerMascotas = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnConfiguracion = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        panelCrearMascota = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNombreMascota = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtColorMascota = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtRazaMascota = new javax.swing.JTextField();
        btnGuardarMascota = new javax.swing.JButton();
        btnCancelarMascota = new javax.swing.JButton();
        btnLimpiarMascota = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cmbDuenioMascota = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        dateNacimientoMascota = new com.toedter.calendar.JDateChooser();
        cmbSexoMascota = new javax.swing.JComboBox<>();
        panelCrearCliente = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtApellidoCliente = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCelularCliente = new javax.swing.JTextField();
        btnGuardarCliente = new javax.swing.JButton();
        btnCancelarCliente = new javax.swing.JButton();
        btnLimpiarCliente = new javax.swing.JButton();
        panelInicial = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtDiaHoy = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        txtCantTurnosHoy = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        txtCantTurnosMan = new javax.swing.JTextField();
        txtCantClientes = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        txtCantMascotas = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        txtCantTurnosHistory = new javax.swing.JTextField();
        panelCrearTurno = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btnGuardarTurno = new javax.swing.JButton();
        btnCancelarTurno = new javax.swing.JButton();
        btnLimpiarTurno = new javax.swing.JButton();
        cmbClientesTurno = new javax.swing.JComboBox<>();
        btnCrearClienteInexistente = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cmbMascotaTurno = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        btnCrearMascotaInexistente = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        dateFechaTurno = new com.toedter.calendar.JDateChooser();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacionesTurno = new javax.swing.JTextArea();
        jLabel28 = new javax.swing.JLabel();
        timeTurno = new com.github.lgooddatepicker.components.TimePicker();
        panelVerTurnos = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        btnEliminarTurnos = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTurnos = new javax.swing.JTable();
        btnCrearTurnos = new javax.swing.JButton();
        btnEditarTurnos = new javax.swing.JButton();
        dateTurnosTodos = new com.toedter.calendar.JDateChooser();
        btnWhatsapp = new javax.swing.JButton();
        btnDiaActual = new javax.swing.JButton();
        panelVerClientes = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        btnEliminarClientes = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableClientes = new javax.swing.JTable();
        btnCrearClientes = new javax.swing.JButton();
        btnEditarClientes = new javax.swing.JButton();
        btnWhatsapp1 = new javax.swing.JButton();
        btnResetBuscador = new javax.swing.JButton();
        txtBuscador = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        panelVerMascotas = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        btnEliminarMascota = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableMascotas = new javax.swing.JTable();
        btnCrearMascotas = new javax.swing.JButton();
        btnEditarMascota = new javax.swing.JButton();
        btnResetBuscadorMascota = new javax.swing.JButton();
        txtBuscadorMascotas = new javax.swing.JTextField();
        btnBuscarMascota = new javax.swing.JButton();
        panelEditarMascota = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtNombeMascotaEditar = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        txtColorMascotaEditar = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtRazaMascotaEditar = new javax.swing.JTextField();
        btnGuardarMascotaEditar = new javax.swing.JButton();
        btnDescartarMascotaEditar = new javax.swing.JButton();
        btnLimpiarMascotaEditar = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cmbDuenioMascotaEditar = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        dateNacimientoMascotaEditar = new com.toedter.calendar.JDateChooser();
        cmbSexoMascotaEditar = new javax.swing.JComboBox<>();
        panelEditarCliente = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtNombreClienteEditar = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtCelularClienteEditar = new javax.swing.JTextField();
        btnGuardarClienteEditar = new javax.swing.JButton();
        btnDescartarClienteEditar = new javax.swing.JButton();
        btnLimpiarClienteEditar = new javax.swing.JButton();
        panelEditarTurno = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        btnGuardarTurno1 = new javax.swing.JButton();
        btnCancelarTurno1 = new javax.swing.JButton();
        btnLimpiarTurno1 = new javax.swing.JButton();
        cmbClientesTurnoEditar = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        cmbMascotaTurnoEditar = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        dateFechaTurnoEditar = new com.toedter.calendar.JDateChooser();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtObservacionesTurnoEditar = new javax.swing.JTextArea();
        jLabel52 = new javax.swing.JLabel();
        timeTurnoEditar = new com.github.lgooddatepicker.components.TimePicker();
        panelConfiguracion = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        txtNombreConfiguracion = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        txtCelularConfiguracion = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txtDireccionConfiguracion = new javax.swing.JTextField();
        btnGuardarConfiguracion = new javax.swing.JButton();
        btnCancelarConfiguracion = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        txtCuitConfiguracion = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtWhatsappConfiguracion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PeluCan versión 1.0");
        setIconImages(null);
        setMaximumSize(new java.awt.Dimension(835, 632));
        setMinimumSize(new java.awt.Dimension(835, 632));
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        menu.setBackground(new java.awt.Color(111, 11, 190));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo100w.png"))); // NOI18N
        logo.setText("PeluCan");
        logo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        logo.setForeground(new java.awt.Color(232, 232, 232));

        btnInicio.setBackground(new java.awt.Color(96, 13, 163));
        btnInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInicioMouseClicked(evt);
            }
        });

        jLabel3.setText("Inicio");
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(232, 232, 232));
        jLabel3.setToolTipText("");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout btnInicioLayout = new javax.swing.GroupLayout(btnInicio);
        btnInicio.setLayout(btnInicioLayout);
        btnInicioLayout.setHorizontalGroup(
            btnInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnInicioLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnInicioLayout.setVerticalGroup(
            btnInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        btnCrearTurno.setBackground(new java.awt.Color(96, 13, 163));
        btnCrearTurno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrearTurno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearTurnoMouseClicked(evt);
            }
        });

        jLabel2.setText("Crear turno");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout btnCrearTurnoLayout = new javax.swing.GroupLayout(btnCrearTurno);
        btnCrearTurno.setLayout(btnCrearTurnoLayout);
        btnCrearTurnoLayout.setHorizontalGroup(
            btnCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCrearTurnoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnCrearTurnoLayout.setVerticalGroup(
            btnCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        btnVerTurnos.setBackground(new java.awt.Color(96, 13, 163));
        btnVerTurnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerTurnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerTurnosMouseClicked(evt);
            }
        });

        jLabel4.setText("Ver turnos");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout btnVerTurnosLayout = new javax.swing.GroupLayout(btnVerTurnos);
        btnVerTurnos.setLayout(btnVerTurnosLayout);
        btnVerTurnosLayout.setHorizontalGroup(
            btnVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnVerTurnosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnVerTurnosLayout.setVerticalGroup(
            btnVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        btnCrearCliente.setBackground(new java.awt.Color(96, 13, 163));
        btnCrearCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrearCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearClienteMouseClicked(evt);
            }
        });

        jLabel5.setText("Crear cliente");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout btnCrearClienteLayout = new javax.swing.GroupLayout(btnCrearCliente);
        btnCrearCliente.setLayout(btnCrearClienteLayout);
        btnCrearClienteLayout.setHorizontalGroup(
            btnCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCrearClienteLayout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnCrearClienteLayout.setVerticalGroup(
            btnCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        btnVerClientes.setBackground(new java.awt.Color(96, 13, 163));
        btnVerClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerClientesMouseClicked(evt);
            }
        });

        jLabel6.setText("Ver clientes");
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout btnVerClientesLayout = new javax.swing.GroupLayout(btnVerClientes);
        btnVerClientes.setLayout(btnVerClientesLayout);
        btnVerClientesLayout.setHorizontalGroup(
            btnVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnVerClientesLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnVerClientesLayout.setVerticalGroup(
            btnVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        btnCrearMascota.setBackground(new java.awt.Color(96, 13, 163));
        btnCrearMascota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrearMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearMascotaMouseClicked(evt);
            }
        });

        jLabel7.setText("Crear mascota");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout btnCrearMascotaLayout = new javax.swing.GroupLayout(btnCrearMascota);
        btnCrearMascota.setLayout(btnCrearMascotaLayout);
        btnCrearMascotaLayout.setHorizontalGroup(
            btnCrearMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnCrearMascotaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnCrearMascotaLayout.setVerticalGroup(
            btnCrearMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        btnVerMascotas.setBackground(new java.awt.Color(96, 13, 163));
        btnVerMascotas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerMascotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnVerMascotasMouseClicked(evt);
            }
        });

        jLabel8.setText("Ver mascotas");
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout btnVerMascotasLayout = new javax.swing.GroupLayout(btnVerMascotas);
        btnVerMascotas.setLayout(btnVerMascotasLayout);
        btnVerMascotasLayout.setHorizontalGroup(
            btnVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnVerMascotasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnVerMascotasLayout.setVerticalGroup(
            btnVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        btnConfiguracion.setBackground(new java.awt.Color(96, 13, 163));
        btnConfiguracion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConfiguracionMouseClicked(evt);
            }
        });

        jLabel9.setText("Configuración");
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout btnConfiguracionLayout = new javax.swing.GroupLayout(btnConfiguracion);
        btnConfiguracion.setLayout(btnConfiguracionLayout);
        btnConfiguracionLayout.setHorizontalGroup(
            btnConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnConfiguracionLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnConfiguracionLayout.setVerticalGroup(
            btnConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCrearTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnVerTurnos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCrearCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnVerClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCrearMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnVerMascotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCrearMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerMascotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel.setBackground(new java.awt.Color(232, 232, 232));
        panel.setPreferredSize(new java.awt.Dimension(0, 0));

        panelCrearMascota.setBackground(new java.awt.Color(232, 232, 232));

        jLabel1.setText("Crear mascota");
        jLabel1.setBackground(new java.awt.Color(37, 37, 37));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(37, 37, 37));

        jLabel10.setText("Nombre");
        jLabel10.setBackground(new java.awt.Color(37, 37, 37));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(37, 37, 37));

        jLabel11.setText("Color");
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(37, 37, 37));
        jLabel11.setToolTipText("");

        jLabel12.setText("Raza");
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(37, 37, 37));

        btnGuardarMascota.setText("Guardar");
        btnGuardarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMascotaMouseClicked(evt);
            }
        });

        btnCancelarMascota.setText("Cancelar");
        btnCancelarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMascotaMouseClicked(evt);
            }
        });

        btnLimpiarMascota.setText("Limpiar");
        btnLimpiarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMascotaMouseClicked(evt);
            }
        });

        jLabel17.setText("Sexo");
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(37, 37, 37));

        jLabel18.setText("Dueño");
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(37, 37, 37));

        cmbDuenioMascota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        jLabel27.setText("Fecha de nacimiento");
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(37, 37, 37));

        cmbSexoMascota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Hembra", "Macho" }));

        javax.swing.GroupLayout panelCrearMascotaLayout = new javax.swing.GroupLayout(panelCrearMascota);
        panelCrearMascota.setLayout(panelCrearMascotaLayout);
        panelCrearMascotaLayout.setHorizontalGroup(
            panelCrearMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrearMascotaLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelCrearMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCrearMascotaLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCrearMascotaLayout.createSequentialGroup()
                        .addGroup(panelCrearMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbSexoMascota, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelCrearMascotaLayout.createSequentialGroup()
                                .addComponent(btnLimpiarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbDuenioMascota, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateNacimientoMascota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRazaMascota, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtColorMascota, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreMascota, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(36, Short.MAX_VALUE))))
        );
        panelCrearMascotaLayout.setVerticalGroup(
            panelCrearMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrearMascotaLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtColorMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazaMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSexoMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDuenioMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateNacimientoMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelCrearMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        panelCrearCliente.setBackground(new java.awt.Color(232, 232, 232));

        jLabel13.setText("Crear cliente");
        jLabel13.setBackground(new java.awt.Color(37, 37, 37));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 37, 37));

        jLabel14.setText("Nombre");
        jLabel14.setBackground(new java.awt.Color(37, 37, 37));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(37, 37, 37));

        jLabel15.setText("Apellido");
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(37, 37, 37));
        jLabel15.setToolTipText("");

        jLabel16.setText("Celular");
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(37, 37, 37));

        btnGuardarCliente.setText("Guardar");
        btnGuardarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarClienteMouseClicked(evt);
            }
        });

        btnCancelarCliente.setText("Cancelar");
        btnCancelarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarClienteMouseClicked(evt);
            }
        });

        btnLimpiarCliente.setText("Limpiar");
        btnLimpiarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarClienteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelCrearClienteLayout = new javax.swing.GroupLayout(panelCrearCliente);
        panelCrearCliente.setLayout(panelCrearClienteLayout);
        panelCrearClienteLayout.setHorizontalGroup(
            panelCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrearClienteLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCrearClienteLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelCrearClienteLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCrearClienteLayout.createSequentialGroup()
                        .addGroup(panelCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCelularCliente, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtApellidoCliente, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCrearClienteLayout.createSequentialGroup()
                                .addComponent(btnLimpiarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                                .addComponent(btnCancelarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))))
        );
        panelCrearClienteLayout.setVerticalGroup(
            panelCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrearClienteLayout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCelularCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelCrearClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnCancelarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 286, Short.MAX_VALUE))
        );

        panelInicial.setBackground(new java.awt.Color(232, 232, 232));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo250.png"))); // NOI18N
        jLabel19.setBackground(new java.awt.Color(37, 37, 37));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(37, 37, 37));

        txtDiaHoy.setEditable(false);
        txtDiaHoy.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtDiaHoy.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDiaHoy.setBackground(new java.awt.Color(232, 232, 232));
        txtDiaHoy.setBorder(null);
        txtDiaHoy.setForeground(new java.awt.Color(37, 37, 37));
        txtDiaHoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaHoyActionPerformed(evt);
            }
        });

        jLabel58.setText("Cantidad de turnos para hoy");
        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(37, 37, 37));

        txtCantTurnosHoy.setEditable(false);
        txtCantTurnosHoy.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtCantTurnosHoy.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantTurnosHoy.setBackground(new java.awt.Color(232, 232, 232));
        txtCantTurnosHoy.setBorder(null);
        txtCantTurnosHoy.setForeground(new java.awt.Color(37, 37, 37));

        jLabel59.setText("Cantidad de turnos para mañana");
        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(37, 37, 37));

        txtCantTurnosMan.setEditable(false);
        txtCantTurnosMan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtCantTurnosMan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantTurnosMan.setBackground(new java.awt.Color(232, 232, 232));
        txtCantTurnosMan.setBorder(null);
        txtCantTurnosMan.setForeground(new java.awt.Color(37, 37, 37));

        txtCantClientes.setEditable(false);
        txtCantClientes.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtCantClientes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantClientes.setBackground(new java.awt.Color(232, 232, 232));
        txtCantClientes.setBorder(null);
        txtCantClientes.setForeground(new java.awt.Color(37, 37, 37));

        jLabel60.setText("Cantidad de clientes registrados");
        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(37, 37, 37));

        txtCantMascotas.setEditable(false);
        txtCantMascotas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtCantMascotas.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantMascotas.setBackground(new java.awt.Color(232, 232, 232));
        txtCantMascotas.setBorder(null);
        txtCantMascotas.setForeground(new java.awt.Color(37, 37, 37));

        jLabel61.setText("Cantidad de mascotas registradas");
        jLabel61.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(37, 37, 37));

        jLabel62.setText("Cantidad de turnos historicos");
        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(37, 37, 37));

        txtCantTurnosHistory.setEditable(false);
        txtCantTurnosHistory.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txtCantTurnosHistory.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCantTurnosHistory.setBackground(new java.awt.Color(232, 232, 232));
        txtCantTurnosHistory.setBorder(null);
        txtCantTurnosHistory.setForeground(new java.awt.Color(37, 37, 37));

        javax.swing.GroupLayout panelInicialLayout = new javax.swing.GroupLayout(panelInicial);
        panelInicial.setLayout(panelInicialLayout);
        panelInicialLayout.setHorizontalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelInicialLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60)
                    .addComponent(jLabel58)
                    .addComponent(jLabel61)
                    .addComponent(jLabel62))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCantClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(txtCantTurnosHistory, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCantMascotas, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCantTurnosHoy)
                    .addComponent(txtCantTurnosMan, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(txtDiaHoy, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelInicialLayout.setVerticalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicialLayout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(txtCantTurnosHoy, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(txtCantTurnosMan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel60)
                    .addComponent(txtCantClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61)
                    .addComponent(txtCantMascotas, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addComponent(txtCantTurnosHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        panelCrearTurno.setBackground(new java.awt.Color(232, 232, 232));
        panelCrearTurno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                panelCrearTurnoFocusLost(evt);
            }
        });

        jLabel20.setText("Crear turno");
        jLabel20.setBackground(new java.awt.Color(37, 37, 37));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(37, 37, 37));

        jLabel21.setText("Cliente");
        jLabel21.setBackground(new java.awt.Color(37, 37, 37));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(37, 37, 37));

        btnGuardarTurno.setText("Guardar");
        btnGuardarTurno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarTurnoMouseClicked(evt);
            }
        });

        btnCancelarTurno.setText("Cancelar");
        btnCancelarTurno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarTurnoMouseClicked(evt);
            }
        });

        btnLimpiarTurno.setText("Limpiar");
        btnLimpiarTurno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarTurnoMouseClicked(evt);
            }
        });

        cmbClientesTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        cmbClientesTurno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbClientesTurnoFocusGained(evt);
            }
        });

        btnCrearClienteInexistente.setText("Crear");
        btnCrearClienteInexistente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearClienteInexistenteMouseClicked(evt);
            }
        });

        jLabel22.setText("¿No existe el cliente?");
        jLabel22.setForeground(new java.awt.Color(37, 37, 37));

        jLabel23.setText("Mascota");
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(37, 37, 37));

        cmbMascotaTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        cmbMascotaTurno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbMascotaTurnoFocusGained(evt);
            }
        });

        jLabel24.setText("¿No existe la mascota?");
        jLabel24.setForeground(new java.awt.Color(37, 37, 37));

        btnCrearMascotaInexistente.setText("Crear");
        btnCrearMascotaInexistente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearMascotaInexistenteMouseClicked(evt);
            }
        });

        jLabel25.setText("Fecha");
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(37, 37, 37));

        jLabel26.setText("Observaciones");
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(37, 37, 37));

        txtObservacionesTurno.setColumns(20);
        txtObservacionesTurno.setRows(5);
        jScrollPane1.setViewportView(txtObservacionesTurno);

        jLabel28.setText("Horario");
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(37, 37, 37));

        javax.swing.GroupLayout panelCrearTurnoLayout = new javax.swing.GroupLayout(panelCrearTurno);
        panelCrearTurno.setLayout(panelCrearTurnoLayout);
        panelCrearTurnoLayout.setHorizontalGroup(
            panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCrearTurnoLayout.createSequentialGroup()
                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCrearTurnoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCrearTurnoLayout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCrearMascotaInexistente))
                            .addGroup(panelCrearTurnoLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCrearClienteInexistente))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCrearTurnoLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbMascotaTurno, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbClientesTurno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelCrearTurnoLayout.createSequentialGroup()
                                .addComponent(btnLimpiarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                                .addComponent(btnCancelarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCrearTurnoLayout.createSequentialGroup()
                                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(panelCrearTurnoLayout.createSequentialGroup()
                                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateFechaTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel25))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(timeTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(36, 36, 36))
        );
        panelCrearTurnoLayout.setVerticalGroup(
            panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCrearTurnoLayout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbClientesTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearClienteInexistente)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMascotaTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearMascotaInexistente)
                    .addComponent(jLabel24))
                .addGap(11, 11, 11)
                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timeTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFechaTurno, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelCrearTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiarTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        panelVerTurnos.setBackground(new java.awt.Color(232, 232, 232));
        panelVerTurnos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                panelVerTurnosFocusLost(evt);
            }
        });

        jLabel29.setText("Todos los turnos");
        jLabel29.setBackground(new java.awt.Color(37, 37, 37));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(37, 37, 37));

        jLabel30.setText("Fecha del turno");
        jLabel30.setBackground(new java.awt.Color(37, 37, 37));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(37, 37, 37));

        btnEliminarTurnos.setText("Eliminar");
        btnEliminarTurnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarTurnosMouseClicked(evt);
            }
        });

        tableTurnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tableTurnos);

        btnCrearTurnos.setText("Crear");
        btnCrearTurnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearTurnosMouseClicked(evt);
            }
        });

        btnEditarTurnos.setText("Editar");
        btnEditarTurnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarTurnosMouseClicked(evt);
            }
        });

        btnWhatsapp.setText("Enviar WhatsApp");
        btnWhatsapp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWhatsappMouseClicked(evt);
            }
        });

        btnDiaActual.setText("🔁");
        btnDiaActual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDiaActualMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelVerTurnosLayout = new javax.swing.GroupLayout(panelVerTurnos);
        panelVerTurnos.setLayout(panelVerTurnosLayout);
        panelVerTurnosLayout.setHorizontalGroup(
            panelVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVerTurnosLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelVerTurnosLayout.createSequentialGroup()
                        .addGroup(panelVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelVerTurnosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelVerTurnosLayout.createSequentialGroup()
                                .addComponent(btnCrearTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnWhatsapp, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelVerTurnosLayout.createSequentialGroup()
                                .addComponent(dateTurnosTodos, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDiaActual, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(36, 36, 36))
        );
        panelVerTurnosLayout.setVerticalGroup(
            panelVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVerTurnosLayout.createSequentialGroup()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDiaActual, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(dateTurnosTodos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelVerTurnosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCrearTurnos, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnEditarTurnos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnWhatsapp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarTurnos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );

        panelVerClientes.setBackground(new java.awt.Color(232, 232, 232));
        panelVerClientes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                panelVerClientesFocusLost(evt);
            }
        });

        jLabel31.setText("Todos los clientes");
        jLabel31.setBackground(new java.awt.Color(37, 37, 37));
        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(37, 37, 37));

        jLabel32.setText("Buscador");
        jLabel32.setBackground(new java.awt.Color(37, 37, 37));
        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(37, 37, 37));

        btnEliminarClientes.setText("Eliminar");
        btnEliminarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarClientesMouseClicked(evt);
            }
        });

        tableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tableClientes);

        btnCrearClientes.setText("Crear");
        btnCrearClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearClientesMouseClicked(evt);
            }
        });

        btnEditarClientes.setText("Editar");
        btnEditarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarClientesMouseClicked(evt);
            }
        });

        btnWhatsapp1.setText("Ver detalles");

        btnResetBuscador.setText("🔁");
        btnResetBuscador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetBuscadorMouseClicked(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelVerClientesLayout = new javax.swing.GroupLayout(panelVerClientes);
        panelVerClientes.setLayout(panelVerClientesLayout);
        panelVerClientesLayout.setHorizontalGroup(
            panelVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVerClientesLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addGroup(panelVerClientesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCrearClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnEditarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnWhatsapp1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelVerClientesLayout.createSequentialGroup()
                        .addGroup(panelVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBuscador, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36))
        );
        panelVerClientesLayout.setVerticalGroup(
            panelVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVerClientesLayout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnResetBuscador, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(txtBuscador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelVerClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCrearClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnEditarClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnWhatsapp1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );

        panelVerMascotas.setBackground(new java.awt.Color(232, 232, 232));
        panelVerMascotas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                panelVerMascotasFocusLost(evt);
            }
        });

        jLabel33.setText("Todas las mascotas");
        jLabel33.setBackground(new java.awt.Color(37, 37, 37));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(37, 37, 37));

        jLabel34.setText("Buscador");
        jLabel34.setBackground(new java.awt.Color(37, 37, 37));
        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(37, 37, 37));

        btnEliminarMascota.setText("Eliminar");
        btnEliminarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMascotaMouseClicked(evt);
            }
        });

        tableMascotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tableMascotas);

        btnCrearMascotas.setText("Crear");
        btnCrearMascotas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCrearMascotasMouseClicked(evt);
            }
        });

        btnEditarMascota.setText("Editar");
        btnEditarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarMascotaMouseClicked(evt);
            }
        });

        btnResetBuscadorMascota.setText("🔁");
        btnResetBuscadorMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnResetBuscadorMascotaMouseClicked(evt);
            }
        });

        btnBuscarMascota.setText("Buscar");
        btnBuscarMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMascotaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelVerMascotasLayout = new javax.swing.GroupLayout(panelVerMascotas);
        panelVerMascotas.setLayout(panelVerMascotasLayout);
        panelVerMascotasLayout.setHorizontalGroup(
            panelVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerMascotasLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVerMascotasLayout.createSequentialGroup()
                        .addComponent(btnCrearMascotas, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelVerMascotasLayout.createSequentialGroup()
                        .addGroup(panelVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane5)
                            .addGroup(panelVerMascotasLayout.createSequentialGroup()
                                .addGroup(panelVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBuscadorMascotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResetBuscadorMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36))))
        );
        panelVerMascotasLayout.setVerticalGroup(
            panelVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelVerMascotasLayout.createSequentialGroup()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscarMascota, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnResetBuscadorMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtBuscadorMascotas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelVerMascotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCrearMascotas, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnEditarMascota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminarMascota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );

        panelEditarMascota.setBackground(new java.awt.Color(232, 232, 232));

        jLabel35.setText("Editar mascota");
        jLabel35.setBackground(new java.awt.Color(37, 37, 37));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(37, 37, 37));

        jLabel36.setText("Nombre");
        jLabel36.setBackground(new java.awt.Color(37, 37, 37));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(37, 37, 37));

        jLabel37.setText("Color");
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(37, 37, 37));
        jLabel37.setToolTipText("");

        jLabel38.setText("Raza");
        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(37, 37, 37));

        btnGuardarMascotaEditar.setText("Guardar cambios");
        btnGuardarMascotaEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMascotaEditarMouseClicked(evt);
            }
        });

        btnDescartarMascotaEditar.setText("Descartar");
        btnDescartarMascotaEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDescartarMascotaEditarMouseClicked(evt);
            }
        });

        btnLimpiarMascotaEditar.setText("Limpiar");
        btnLimpiarMascotaEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMascotaEditarMouseClicked(evt);
            }
        });

        jLabel39.setText("Sexo");
        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(37, 37, 37));

        jLabel40.setText("Dueño");
        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(37, 37, 37));

        cmbDuenioMascotaEditar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));

        jLabel41.setText("Fecha de nacimiento");
        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(37, 37, 37));

        cmbSexoMascotaEditar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-", "Hembra", "Macho" }));

        javax.swing.GroupLayout panelEditarMascotaLayout = new javax.swing.GroupLayout(panelEditarMascota);
        panelEditarMascota.setLayout(panelEditarMascotaLayout);
        panelEditarMascotaLayout.setHorizontalGroup(
            panelEditarMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarMascotaLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelEditarMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditarMascotaLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarMascotaLayout.createSequentialGroup()
                        .addGroup(panelEditarMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbSexoMascotaEditar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtRazaMascotaEditar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtColorMascotaEditar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombeMascotaEditar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditarMascotaLayout.createSequentialGroup()
                                .addComponent(btnLimpiarMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                                .addComponent(btnDescartarMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cmbDuenioMascotaEditar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateNacimientoMascotaEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditarMascotaLayout.createSequentialGroup()
                                .addGroup(panelEditarMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(36, 36, 36))))
        );
        panelEditarMascotaLayout.setVerticalGroup(
            panelEditarMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarMascotaLayout.createSequentialGroup()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombeMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtColorMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRazaMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSexoMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbDuenioMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateNacimientoMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelEditarMascotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarMascotaEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDescartarMascotaEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiarMascotaEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        panelEditarCliente.setBackground(new java.awt.Color(232, 232, 232));

        jLabel42.setText("Editar cliente");
        jLabel42.setBackground(new java.awt.Color(37, 37, 37));
        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(37, 37, 37));

        jLabel43.setText("Nombre completo");
        jLabel43.setBackground(new java.awt.Color(37, 37, 37));
        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(37, 37, 37));

        jLabel45.setText("Celular");
        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(37, 37, 37));

        btnGuardarClienteEditar.setText("Guardar cambios");
        btnGuardarClienteEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarClienteEditarMouseClicked(evt);
            }
        });

        btnDescartarClienteEditar.setText("Descartar");
        btnDescartarClienteEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDescartarClienteEditarMouseClicked(evt);
            }
        });

        btnLimpiarClienteEditar.setText("Limpiar");
        btnLimpiarClienteEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarClienteEditarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelEditarClienteLayout = new javax.swing.GroupLayout(panelEditarCliente);
        panelEditarCliente.setLayout(panelEditarClienteLayout);
        panelEditarClienteLayout.setHorizontalGroup(
            panelEditarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarClienteLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelEditarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditarClienteLayout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarClienteLayout.createSequentialGroup()
                        .addGroup(panelEditarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCelularClienteEditar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEditarClienteLayout.createSequentialGroup()
                                .addComponent(btnLimpiarClienteEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                                .addComponent(btnDescartarClienteEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarClienteEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNombreClienteEditar, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36))))
        );
        panelEditarClienteLayout.setVerticalGroup(
            panelEditarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarClienteLayout.createSequentialGroup()
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreClienteEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCelularClienteEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelEditarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarClienteEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDescartarClienteEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiarClienteEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 355, Short.MAX_VALUE))
        );

        panelEditarTurno.setBackground(new java.awt.Color(232, 232, 232));
        panelEditarTurno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                panelEditarTurnoFocusLost(evt);
            }
        });

        jLabel44.setText("Editar turno");
        jLabel44.setBackground(new java.awt.Color(37, 37, 37));
        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(37, 37, 37));

        jLabel46.setText("Cliente");
        jLabel46.setBackground(new java.awt.Color(37, 37, 37));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(37, 37, 37));

        btnGuardarTurno1.setText("Guardar cambios");
        btnGuardarTurno1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarTurno1MouseClicked(evt);
            }
        });

        btnCancelarTurno1.setText("Descartar");
        btnCancelarTurno1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarTurno1MouseClicked(evt);
            }
        });

        btnLimpiarTurno1.setText("Limpiar");
        btnLimpiarTurno1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarTurno1MouseClicked(evt);
            }
        });

        cmbClientesTurnoEditar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        cmbClientesTurnoEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbClientesTurnoEditarFocusGained(evt);
            }
        });

        jLabel48.setText("Mascota");
        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(37, 37, 37));

        cmbMascotaTurnoEditar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        cmbMascotaTurnoEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbMascotaTurnoEditarFocusGained(evt);
            }
        });

        jLabel50.setText("Fecha");
        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(37, 37, 37));

        jLabel51.setText("Observaciones");
        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(37, 37, 37));

        txtObservacionesTurnoEditar.setColumns(20);
        txtObservacionesTurnoEditar.setRows(5);
        jScrollPane2.setViewportView(txtObservacionesTurnoEditar);

        jLabel52.setText("Horario");
        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(37, 37, 37));

        javax.swing.GroupLayout panelEditarTurnoLayout = new javax.swing.GroupLayout(panelEditarTurno);
        panelEditarTurno.setLayout(panelEditarTurnoLayout);
        panelEditarTurnoLayout.setHorizontalGroup(
            panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarTurnoLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditarTurnoLayout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelEditarTurnoLayout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditarTurnoLayout.createSequentialGroup()
                        .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbMascotaTurnoEditar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditarTurnoLayout.createSequentialGroup()
                                .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateFechaTurnoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel50))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelEditarTurnoLayout.createSequentialGroup()
                                        .addComponent(jLabel52)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 214, Short.MAX_VALUE))
                                    .addComponent(timeTurnoEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbClientesTurnoEditar, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditarTurnoLayout.createSequentialGroup()
                                .addComponent(btnLimpiarTurno1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelarTurno1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarTurno1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelEditarTurnoLayout.createSequentialGroup()
                                .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(36, 36, 36))))
        );
        panelEditarTurnoLayout.setVerticalGroup(
            panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditarTurnoLayout.createSequentialGroup()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbClientesTurnoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbMascotaTurnoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timeTurnoEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFechaTurnoEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel51)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelEditarTurnoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnGuardarTurno1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelarTurno1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpiarTurno1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        panelConfiguracion.setBackground(new java.awt.Color(232, 232, 232));

        jLabel47.setText("Configuración");
        jLabel47.setBackground(new java.awt.Color(37, 37, 37));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(37, 37, 37));

        jLabel49.setText("Nombre empresa");
        jLabel49.setBackground(new java.awt.Color(37, 37, 37));
        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(37, 37, 37));

        jLabel53.setText("Celular");
        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(37, 37, 37));
        jLabel53.setToolTipText("");

        jLabel54.setText("Dirección");
        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(37, 37, 37));

        btnGuardarConfiguracion.setText("Guardar cambios");
        btnGuardarConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarConfiguracionMouseClicked(evt);
            }
        });

        btnCancelarConfiguracion.setText("Descartar");
        btnCancelarConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarConfiguracionMouseClicked(evt);
            }
        });

        jLabel55.setText("CUIT");
        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(37, 37, 37));

        jLabel56.setText("Mensajes de WhatsApp");
        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(37, 37, 37));

        txtWhatsappConfiguracion.setColumns(20);
        txtWhatsappConfiguracion.setLineWrap(true);
        txtWhatsappConfiguracion.setRows(5);
        txtWhatsappConfiguracion.setWrapStyleWord(true);
        jScrollPane6.setViewportView(txtWhatsappConfiguracion);

        javax.swing.GroupLayout panelConfiguracionLayout = new javax.swing.GroupLayout(panelConfiguracion);
        panelConfiguracion.setLayout(panelConfiguracionLayout);
        panelConfiguracionLayout.setHorizontalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelConfiguracionLayout.createSequentialGroup()
                        .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(552, 552, 552))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelConfiguracionLayout.createSequentialGroup()
                        .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCuitConfiguracion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccionConfiguracion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelularConfiguracion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreConfiguracion, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                                .addGap(297, 297, 297)
                                .addComponent(btnCancelarConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnGuardarConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConfiguracionLayout.createSequentialGroup()
                                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConfiguracionLayout.createSequentialGroup()
                                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(162, 162, 162))
                                    .addComponent(jLabel47, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConfiguracionLayout.createSequentialGroup()
                                        .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(249, 249, 249))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConfiguracionLayout.createSequentialGroup()
                                        .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(217, 217, 217))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelConfiguracionLayout.createSequentialGroup()
                                        .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(125, 125, 125)))
                                .addGap(283, 283, 283)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelConfiguracionLayout.setVerticalGroup(
            panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConfiguracionLayout.createSequentialGroup()
                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCelularConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDireccionConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCuitConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelConfiguracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGuardarConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btnCancelarConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCrearMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCrearCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCrearTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelVerTurnos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelVerClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelVerMascotas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEditarMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEditarTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCrearMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCrearCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCrearTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelVerTurnos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelVerClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelVerMascotas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEditarMascota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEditarTurno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        
    }//GEN-LAST:event_jPanel4MouseClicked

    private void btnInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInicioMouseClicked
        panel.removeAll();
        panel.add(this.panelInicial);
        panel.repaint();
    }//GEN-LAST:event_btnInicioMouseClicked

    private void btnCrearClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearClienteMouseClicked
        cargarPantalla(this.panelCrearCliente);
    }//GEN-LAST:event_btnCrearClienteMouseClicked

    private void btnCrearMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMascotaMouseClicked
        cargarPantalla(this.panelCrearMascota);
        cargarClientes(cmbDuenioMascota);
        //
    }//GEN-LAST:event_btnCrearMascotaMouseClicked
    
    private void cargarClientes(JComboBox combo){
        if(combo.getItemCount()>0){
            combo.removeAllItems();
            combo.addItem("-");
            combo.setSelectedIndex(0);
        }
        List<Cliente> clientes = clienteController.getClientes();
        for(Cliente cliente : clientes){
            combo.addItem(cliente.getNombre());
        }
    }
    
    
    private void btnCrearTurnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearTurnoMouseClicked
        cargarPantalla(this.panelCrearTurno);
        cargarClientes(cmbClientesTurno);
    }//GEN-LAST:event_btnCrearTurnoMouseClicked

    
    private void cargarMascotas(JComboBox combo, String clienteTmp){
        List<Mascota> mascotas = mascotaController.getMascotasByCliente(clienteTmp);
        for(Mascota mascota : mascotas){
            combo.addItem(mascota.getNombre());
        }
    }
    private void btnLimpiarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarClienteMouseClicked
        try{
            txtNombreCliente.setText("");
            txtApellidoCliente.setText("");
            txtCelularCliente.setText("");
        }catch(Exception e){
            System.out.println("Error al limpiar los datos del cliente en el front: "+e);
        }
        
    }//GEN-LAST:event_btnLimpiarClienteMouseClicked

    private void btnCancelarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarClienteMouseClicked
        cargarPantalla(this.panelInicial);
    }//GEN-LAST:event_btnCancelarClienteMouseClicked

    private void btnGuardarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarClienteMouseClicked
        try{
            if(!txtNombreCliente.getText().equals("")&&!txtApellidoCliente.getText().equals("")&&!txtCelularCliente.getText().equals("")){
                String nombre = txtNombreCliente.getText();
                String apellido = txtApellidoCliente.getText();
                String celular = txtCelularCliente.getText();
                String nombreCompleto = nombre+" "+apellido;
                if(clienteController.guardar(nombreCompleto,celular)){
                    JOptionPane.showMessageDialog(rootPane, "Cliente guardado con exito.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                    btnLimpiarClienteMouseClicked(null);
                    cargarPantalla(this.panelInicial);
                }else{
                    JOptionPane.showMessageDialog(rootPane, "El cliente a crear ya se encuentra registrado en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
                    
                }
                
            }else{
                JOptionPane.showMessageDialog(rootPane, "Existen campos vacios y es imposible guardar al cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            System.out.println("Error al guardar los datos del cliente en el front: "+e);
        }
        
    }//GEN-LAST:event_btnGuardarClienteMouseClicked

    private void btnLimpiarMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMascotaMouseClicked
        try{
            txtNombreMascota.setText("");
            txtColorMascota.setText("");
            txtRazaMascota.setText("");
            cmbSexoMascota.setSelectedIndex(0);
            cmbDuenioMascota.setSelectedIndex(0);
            dateNacimientoMascota.setDate(null);
        }catch(Exception e){
            System.out.println("Error al limpiar los datos de la mascota en el front: "+e);
        }
    }//GEN-LAST:event_btnLimpiarMascotaMouseClicked

    private void btnCancelarMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMascotaMouseClicked
        cargarPantalla(panelInicial);
        btnLimpiarMascotaMouseClicked(null);
    }//GEN-LAST:event_btnCancelarMascotaMouseClicked

    private void btnGuardarMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMascotaMouseClicked
        try{
            String nombre = txtNombreMascota.getText();
            String color = txtColorMascota.getText();
            String raza = txtRazaMascota.getText();
            String sexo = (String) cmbSexoMascota.getSelectedItem();
            String duenio = (String) cmbDuenioMascota.getSelectedItem();
            Date fecha = dateNacimientoMascota.getDate();
            LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            if(mascotaController.guardar(nombre, color, raza, sexo, duenio, fechaNacimiento)){
                JOptionPane.showMessageDialog(rootPane, "Mascota guardado con exito.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                btnLimpiarMascotaMouseClicked(null);
                cargarPantalla(this.panelInicial);
            }else{
                JOptionPane.showMessageDialog(rootPane, "La mascota a crear ya existe para este cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Para crear la mascota debe llenar los campos requeridos.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al guardar los datos de la mascota en el front: "+e);
        }
    }//GEN-LAST:event_btnGuardarMascotaMouseClicked

    private void btnCrearClienteInexistenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearClienteInexistenteMouseClicked
        btnLimpiarTurnoMouseClicked(null);
        cargarPantalla(this.panelCrearCliente);
    }//GEN-LAST:event_btnCrearClienteInexistenteMouseClicked

    private void btnCrearMascotaInexistenteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMascotaInexistenteMouseClicked
        btnLimpiarTurnoMouseClicked(null);
        cargarPantalla(this.panelCrearMascota);
    }//GEN-LAST:event_btnCrearMascotaInexistenteMouseClicked

    private void btnLimpiarTurnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarTurnoMouseClicked
        try{
            cmbClientesTurno.removeAllItems();
            cmbClientesTurno.addItem("-");
            cargarClientes(cmbClientesTurno);
            
            cmbMascotaTurno.removeAllItems();
            cmbMascotaTurno.addItem("-");
            
            dateFechaTurno.setDate(null);
            timeTurno.setTime(LocalTime.MIN);
            txtObservacionesTurno.setText("");
        }catch(Exception e){
            System.out.println("Error al limpiar los datos del turno en el front: "+e);
        }
    }//GEN-LAST:event_btnLimpiarTurnoMouseClicked

    private void btnCancelarTurnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarTurnoMouseClicked
        btnLimpiarTurnoMouseClicked(null);
        cargarPantalla(this.panelInicial);
    }//GEN-LAST:event_btnCancelarTurnoMouseClicked

    private void cmbMascotaTurnoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbMascotaTurnoFocusGained
        /*
        try{
            int idCliente = cmbClientesTurno.getSelectedIndex();
            //  SI HAY UN SOLO ITEM, Y EL MISMO ES EL POR DEFAULT, DEBE CARGAR LOS CLIENTES DE LA BD
            if(cmbMascotaTurno.getItemCount() == 1 && cmbClientesTurno.getItemAt(0).equals("-")){
                cargarMascotas(cmbMascotaTurno, idCliente);
            }else{
                cmbMascotaTurno.removeAllItems();
                cmbMascotaTurno.addItem("-");
                cargarMascotas(cmbMascotaTurno, idCliente);
            }
        }catch(Exception e){
            System.out.println("Error al cargar las mascotas del cliente seleccionado en el front: "+e);
        }
        */
                
    }//GEN-LAST:event_cmbMascotaTurnoFocusGained

    private void cmbClientesTurnoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbClientesTurnoFocusGained
        cmbMascotaTurnoFocusGained(null);
        /*
        int idCliente = cmbClientesTurno.getSelectedIndex();
        if(cmbClientesTurno != null){
            cmbClientesTurno.removeAllItems();
        }
*/
    }//GEN-LAST:event_cmbClientesTurnoFocusGained

    private void panelCrearTurnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panelCrearTurnoFocusLost
        btnLimpiarTurnoMouseClicked(null);
    }//GEN-LAST:event_panelCrearTurnoFocusLost

    private void btnGuardarTurnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarTurnoMouseClicked
        try{
            String cliente = (String) cmbClientesTurno.getSelectedItem();
            String mascota = (String) cmbMascotaTurno.getSelectedItem();
            Date fecha = dateFechaTurno.getDate();
            LocalDate fechaTurno = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime hora = timeTurno.getTime();
            //  CONVERTIR A LOCALDATETIME DESDE UNA FECHA Y UNA HORA
            LocalDateTime fechaHora = fechaTurno.atTime(hora);
            
            String observaciones = txtObservacionesTurno.getText();
            if(observaciones.equals("")){
                observaciones="Sin observaciones";
            }
            
            Turno turnoExistente = turnoController.findTurnoByFechaHora(fechaHora);
            if (turnoExistente != null) {
                JOptionPane.showMessageDialog(rootPane, "El turno ya está ocupado en el horario seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        
            if(!fechaTurno.isBefore(LocalDate.now())){
                turnoController.guardar(cliente, mascota, fechaHora, observaciones);
                JOptionPane.showMessageDialog(rootPane, "Turno creado con exito.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                btnLimpiarTurnoMouseClicked(null);
                cargarPantalla(this.panelInicial);
            }else{
                JOptionPane.showMessageDialog(rootPane, "El turno no puede ser registrado para un dia anterior a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
            }
                    
            
            
            
        }catch(Exception e){
            System.out.println("Error al guardar el turno en el front: "+e);
        }
    }//GEN-LAST:event_btnGuardarTurnoMouseClicked

    private void btnEliminarTurnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarTurnosMouseClicked
        try{
           // si la tabla tiene filas y si hay uno seleccionado
            if(tableTurnos.getRowCount()>0 && tableTurnos.getSelectedRow()!=-1){
                int idTurno = Integer.parseInt(String.valueOf(tableTurnos.getValueAt(tableTurnos.getSelectedRow(),0)));
                
                int valor = JOptionPane.showConfirmDialog(rootPane, "¿Deseas eliminar el turno?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
                if(valor==0){
                    turnoController.eliminarTurno(idTurno);
                    JOptionPane.showMessageDialog(rootPane, "Turno eliminado correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                    
                }
                //volver a cargar la tabla
                setDiaActual();
            }else{
                JOptionPane.showMessageDialog(rootPane, "No hay datos o no hay datos seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            System.out.println("Error al eliminar un turno en el front: "+e);
        }
    }//GEN-LAST:event_btnEliminarTurnosMouseClicked

    private void panelVerTurnosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panelVerTurnosFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_panelVerTurnosFocusLost

    private void btnVerTurnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerTurnosMouseClicked
        cargarPantalla(this.panelVerTurnos);
        setDiaActual();
    }//GEN-LAST:event_btnVerTurnosMouseClicked

    private void btnDiaActualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDiaActualMouseClicked
        setDiaActual();
    }//GEN-LAST:event_btnDiaActualMouseClicked

    private void btnCrearTurnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearTurnosMouseClicked
        cargarPantalla(this.panelCrearTurno);
    }//GEN-LAST:event_btnCrearTurnosMouseClicked

    private void btnEliminarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarClientesMouseClicked
        try{
           // si la tabla tiene filas y si hay uno seleccionado
            if(tableClientes.getRowCount()>0 && tableClientes.getSelectedRow()!=-1){
                int idCliente = Integer.parseInt(String.valueOf(tableClientes.getValueAt(tableClientes.getSelectedRow(),0)));
                
                int valor = JOptionPane.showConfirmDialog(rootPane, "¿Deseas eliminar el cliente, sus mascotas y sus turnos asociados?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
                if(valor==0){
                    clienteController.eliminarCliente(idCliente);
                    JOptionPane.showMessageDialog(rootPane, "Cliente eliminado junto a sus asociados de forma correcta", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                    
                }
                //volver a cargar la tabla
                cargarTablaClientes("");
            }else{
                JOptionPane.showMessageDialog(rootPane, "No hay datos o no hay datos seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            System.out.println("Error al eliminar un turno en el front: "+e);
        }
    }//GEN-LAST:event_btnEliminarClientesMouseClicked

    private void btnCrearClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearClientesMouseClicked
        btnCrearClienteMouseClicked(null);
    }//GEN-LAST:event_btnCrearClientesMouseClicked

    private void btnResetBuscadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetBuscadorMouseClicked
        txtBuscador.setText("");
        cargarTablaClientes("");
    }//GEN-LAST:event_btnResetBuscadorMouseClicked

    private void panelVerClientesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panelVerClientesFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_panelVerClientesFocusLost

    private void btnVerClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerClientesMouseClicked
        cargarPantalla(this.panelVerClientes);
        cargarTablaClientes("");
    }//GEN-LAST:event_btnVerClientesMouseClicked

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        String buscar = txtBuscador.getText();
        cargarTablaClientes(buscar);
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnEliminarMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMascotaMouseClicked
        try{
           // si la tabla tiene filas y si hay uno seleccionado
            if(tableMascotas.getRowCount()>0 && tableMascotas.getSelectedRow()!=-1){
                int idMascota = Integer.parseInt(String.valueOf(tableMascotas.getValueAt(tableMascotas.getSelectedRow(),0)));
                
                int valor = JOptionPane.showConfirmDialog(rootPane, "¿Deseas eliminar la mascota y sus turnos asociados?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
                if(valor==0){
                    mascotaController.eliminarMascota(idMascota);
                    JOptionPane.showMessageDialog(rootPane, "Mascota eliminada junto a sus turnos asociados de forma correcta", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                    
                }
                //volver a cargar la tabla
                cargarTablaMascotas("");
            }else{
                JOptionPane.showMessageDialog(rootPane, "No hay datos o no hay datos seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            System.out.println("Error al eliminar un turno en el front: "+e);
        }
    }//GEN-LAST:event_btnEliminarMascotaMouseClicked

    private void btnCrearMascotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearMascotasMouseClicked
        btnCrearMascotaMouseClicked(null);
    }//GEN-LAST:event_btnCrearMascotasMouseClicked

    private void btnResetBuscadorMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnResetBuscadorMascotaMouseClicked
        cargarTablaMascotas("");
    }//GEN-LAST:event_btnResetBuscadorMascotaMouseClicked

    private void btnBuscarMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMascotaMouseClicked
        String buscar = txtBuscadorMascotas.getText();
        cargarTablaMascotas(buscar);
    }//GEN-LAST:event_btnBuscarMascotaMouseClicked

    private void panelVerMascotasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panelVerMascotasFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_panelVerMascotasFocusLost

    private void btnVerMascotasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerMascotasMouseClicked
        cargarPantalla(this.panelVerMascotas);
        cargarTablaMascotas("");
    }//GEN-LAST:event_btnVerMascotasMouseClicked

    private void btnGuardarMascotaEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMascotaEditarMouseClicked
        try{
            String nombre = txtNombeMascotaEditar.getText();
            String color= txtColorMascotaEditar.getText();
            String raza=txtRazaMascotaEditar.getText();
            String sexo = (String) cmbSexoMascotaEditar.getSelectedItem();
            String nombreCliente = (String) cmbDuenioMascotaEditar.getSelectedItem();
            
            Date fecha = dateNacimientoMascotaEditar.getDate();
            LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            mascotaController.guardarCambios(this.getMascotaEditar(), nombre, color, raza, sexo, nombreCliente, fechaNacimiento);
            JOptionPane.showMessageDialog(rootPane, "Mascota modificada correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            
            btnVerMascotasMouseClicked(null);
        }catch(Exception e){
            System.out.println("Error al guardar los cambios de la mascota en el front: "+e);
        }
    }//GEN-LAST:event_btnGuardarMascotaEditarMouseClicked

    private void btnDescartarMascotaEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescartarMascotaEditarMouseClicked
        try{
            int valor = JOptionPane.showConfirmDialog(rootPane, "¿Desea descartar los nuevos cambios?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
            if(valor==0){
                btnVerMascotasMouseClicked(null);
            }
        }catch(Exception e){
            System.out.println("Error al descartar los cambios de la mascota: "+e);
        }
    }//GEN-LAST:event_btnDescartarMascotaEditarMouseClicked

    private void btnLimpiarMascotaEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMascotaEditarMouseClicked
        try{
            txtNombeMascotaEditar.setText("");
            txtColorMascotaEditar.setText("");
            txtRazaMascotaEditar.setText("");
            cmbSexoMascotaEditar.setSelectedIndex(0);
            cmbDuenioMascotaEditar.setSelectedIndex(0);
            dateNacimientoMascotaEditar.setDate(null);
        }catch(Exception e){
            System.out.println("Error al limpiar los datos de la mascota a editar en el front: "+e);
        }
    }//GEN-LAST:event_btnLimpiarMascotaEditarMouseClicked

    private void btnEditarMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMascotaMouseClicked
        try{
             if(tableMascotas.getRowCount()>0 && tableMascotas.getSelectedRow()!=-1){
                int idMascota = Integer.parseInt(String.valueOf(tableMascotas.getValueAt(tableMascotas.getSelectedRow(),0)));
                Mascota mascota = mascotaController.getMascota(idMascota);
                
                this.setMascotaEditar(mascota);
                cargarPantalla(this.panelEditarMascota);
                
                txtNombeMascotaEditar.setText(mascota.getNombre());
                txtColorMascotaEditar.setText(mascota.getColor());
                txtRazaMascotaEditar.setText(mascota.getRaza());
                
                if(mascota.getSexo().equals("Hembra")){
                    cmbSexoMascotaEditar.setSelectedIndex(1);
                }else if(mascota.getSexo().equals("Macho")){
                    cmbSexoMascotaEditar.setSelectedIndex(2);
                }
                
                cargarClientes(cmbDuenioMascotaEditar);
                List<Cliente> clientes = clienteController.getClientes();
                for(Cliente cliente : clientes){
                    if(mascota.getCliente().getNombre().equals(cliente.getNombre())){
                        cmbDuenioMascotaEditar.setSelectedItem((Object) mascota.getCliente().getNombre());
                    }
                }
                
                dateNacimientoMascotaEditar.setDate(Date.from(mascota.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
             }else{
                 JOptionPane.showMessageDialog(rootPane, "No hay datos o no hay datos seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
             }
            
        }catch(Exception e){
            System.out.println("Error al querer ejecutar la accion de edicion en la mascota desde el front: "+e);
        }
    }//GEN-LAST:event_btnEditarMascotaMouseClicked

    private void btnGuardarClienteEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarClienteEditarMouseClicked
        try{
            String nombre = txtNombreClienteEditar.getText();
            String celular= txtCelularClienteEditar.getText();
            
            clienteController.guardarCambios(this.getClienteEditar(), nombre, celular);
            JOptionPane.showMessageDialog(rootPane, "Mascota modificada correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
            
            btnVerClientesMouseClicked(null);
        }catch(Exception e){
            System.out.println("Error al guardar los cambios de la mascota en el front: "+e);
        }
    }//GEN-LAST:event_btnGuardarClienteEditarMouseClicked

    private void btnDescartarClienteEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDescartarClienteEditarMouseClicked
        try{
            int valor = JOptionPane.showConfirmDialog(rootPane, "¿Desea descartar los nuevos cambios?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
            if(valor==0){
                btnVerClientesMouseClicked(null);
            }
        }catch(Exception e){
            System.out.println("Error al descartar los cambios de la mascota: "+e);
        }
    }//GEN-LAST:event_btnDescartarClienteEditarMouseClicked

    private void btnLimpiarClienteEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarClienteEditarMouseClicked
        try{
            txtNombreClienteEditar.setText("");
            txtCelularClienteEditar.setText("");
        }catch(Exception e){
            System.out.println("Error al limpiar los datos de la mascota a editar en el front: "+e);
        }
    }//GEN-LAST:event_btnLimpiarClienteEditarMouseClicked

    private void btnEditarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarClientesMouseClicked
        try{
            if(tableClientes.getRowCount()>0 && tableClientes.getSelectedRow()!=-1){
                int idCliente = Integer.parseInt(String.valueOf(tableClientes.getValueAt(tableClientes.getSelectedRow(),0)));
                Cliente cliente = clienteController.getCliente(idCliente);
                this.setClienteEditar(cliente);
                
                cargarPantalla(this.panelEditarCliente);
                txtNombreClienteEditar.setText(cliente.getNombre());
                txtCelularClienteEditar.setText(cliente.getCelular());
                
                
            }else{
                 JOptionPane.showMessageDialog(rootPane, "No hay datos o no hay datos seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            System.out.println("Error al querer ejecutar la accion de edicion en el cliente desde el front: "+e);
        }
    }//GEN-LAST:event_btnEditarClientesMouseClicked

    private void btnGuardarTurno1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarTurno1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarTurno1MouseClicked

    private void btnCancelarTurno1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarTurno1MouseClicked
        try{
            int valor = JOptionPane.showConfirmDialog(rootPane, "¿Desea descartar los nuevos cambios?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
            if(valor==0){
                btnVerTurnosMouseClicked(null);
            }
        }catch(Exception e){
            System.out.println("Error al descartar los cambios: "+e);
        }
    }//GEN-LAST:event_btnCancelarTurno1MouseClicked

    private void descartarCambios(){
        
    }
    private void btnLimpiarTurno1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarTurno1MouseClicked
        try{
            cmbClientesTurnoEditar.removeAllItems();
            cmbClientesTurnoEditar.addItem("-");
            cargarClientes(cmbClientesTurnoEditar);
            
            cmbMascotaTurnoEditar.removeAllItems();
            cmbMascotaTurnoEditar.addItem("-");
            
            dateFechaTurnoEditar.setDate(null);
            timeTurnoEditar.setTime(LocalTime.MIN);
            txtObservacionesTurnoEditar.setText("");
        }catch(Exception e){
            System.out.println("Error al limpiar los datos del turno a editar en el front: "+e);
        }
    }//GEN-LAST:event_btnLimpiarTurno1MouseClicked

    private void cmbClientesTurnoEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbClientesTurnoEditarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbClientesTurnoEditarFocusGained

    private void cmbMascotaTurnoEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbMascotaTurnoEditarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMascotaTurnoEditarFocusGained

    private void panelEditarTurnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_panelEditarTurnoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEditarTurnoFocusLost

    private void btnEditarTurnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarTurnosMouseClicked
        try{
            if(tableTurnos.getRowCount()>0 && tableTurnos.getSelectedRow()!=-1){
                int idTurno = Integer.parseInt(String.valueOf(tableTurnos.getValueAt(tableTurnos.getSelectedRow(),0)));
                Turno turno = turnoController.getTurno(idTurno);
                this.setTurnoEditar(turno);
                
                cargarPantalla(this.panelEditarTurno);
                cargarClientes(cmbClientesTurnoEditar);
                
                List<Cliente> clientes = clienteController.getClientes();
                for(Cliente cliente : clientes){
                    if(turno.getCliente().getNombre().equals(cliente.getNombre())){
                        cmbClientesTurnoEditar.setSelectedItem((Object) turno.getCliente().getNombre());
                    }
                }
                
                String clienteNombre = turno.getCliente().getNombre();
                //  SI HAY UN SOLO ITEM, Y EL MISMO ES EL POR DEFAULT, DEBE CARGAR LOS CLIENTES DE LA BD
                if(cmbMascotaTurnoEditar.getItemCount() == 1 && cmbMascotaTurnoEditar.getItemAt(0).equals("-")){
                    cargarMascotas(cmbMascotaTurnoEditar, clienteNombre);
                }else{
                    cmbMascotaTurnoEditar.removeAllItems();
                    cmbMascotaTurnoEditar.addItem("-");
                    cargarMascotas(cmbMascotaTurnoEditar, clienteNombre);
                }
                
                List<Mascota> mascotas = mascotaController.getMascotasByCliente(clienteNombre);
                for(Mascota mascota : mascotas){
                    if(turno.getMascota().getNombre().equals(mascota.getNombre())){
                        cmbMascotaTurnoEditar.setSelectedItem((Object) turno.getMascota().getNombre());
                    }
                }
                
                dateFechaTurnoEditar.setDate(Date.from(turno.getFechaHora().toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                timeTurnoEditar.setTime(turno.getFechaHora().toLocalTime());
                txtObservacionesTurnoEditar.setText(turno.getObservaciones());
                /*
                cmbMascotaTurnoEditar;
                dateFechaTurnoEditar;
                timeTurnoEditar;
                txtObservacionesTurnoEditar;
                */
                
                
            }else{
                 JOptionPane.showMessageDialog(rootPane, "No hay datos o no hay datos seleccionados", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
        }catch(Exception e){
            System.out.println("Error al querer ejecutar la accion de edicion en el turno desde el front: "+e);
        }
    }//GEN-LAST:event_btnEditarTurnosMouseClicked

    private void btnGuardarConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarConfiguracionMouseClicked
        try{
            String nombre= txtNombreConfiguracion.getText();
            String celular= txtCelularConfiguracion.getText();
            String direccion= txtDireccionConfiguracion.getText();
            String cuit= txtCuitConfiguracion.getText();
            String wsp= txtWhatsappConfiguracion.getText();
            
            if(empresaController.guardarCambios(this.getEmpresaEditar(), nombre, celular, direccion, cuit, wsp)){
                JOptionPane.showMessageDialog(rootPane, "Los cambios se han guardado con exito.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
                btnInicioMouseClicked(null);
            }else{
                JOptionPane.showMessageDialog(rootPane, "Los cambios no se han guardado por algun motivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(Exception e){
            System.out.println("Error al guardar los cambios de la empresa en el front: "+e);
        }
    }//GEN-LAST:event_btnGuardarConfiguracionMouseClicked

    private void btnCancelarConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarConfiguracionMouseClicked
        try{
            int valor = JOptionPane.showConfirmDialog(rootPane, "¿Desea descartar los nuevos cambios?", "Pregunta", WIDTH, JOptionPane.QUESTION_MESSAGE);
            if(valor==0){
                btnInicioMouseClicked(null);
            }
        }catch(Exception e){
            System.out.println("Error al descartar los cambios: "+e);
        }
        
    }//GEN-LAST:event_btnCancelarConfiguracionMouseClicked

    private void btnConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConfiguracionMouseClicked
        try{
            Empresa empresa = empresaController.getEmpresa();
            this.setEmpresaEditar(empresa);
            
            txtNombreConfiguracion.setText(empresa.getNombre());
            txtCelularConfiguracion.setText(empresa.getCelular());
            txtDireccionConfiguracion.setText(empresa.getDireccion());
            txtCuitConfiguracion.setText(empresa.getCuit());
            txtWhatsappConfiguracion.setText(empresa.getTxtWhatsapp());
            cargarPantalla(this.panelConfiguracion);
        }catch(Exception e){
            System.out.println("Error al cargar la pantalla configuración");
        }
        
    }//GEN-LAST:event_btnConfiguracionMouseClicked

    private void btnWhatsappMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWhatsappMouseClicked
        try{
            if(tableTurnos.getRowCount()>0 && tableTurnos.getSelectedRow()!=-1){
                int idTurno = Integer.parseInt(String.valueOf(tableTurnos.getValueAt(tableTurnos.getSelectedRow(),0)));
                Turno turno = turnoController.getTurno(idTurno);
                Empresa empresa = empresaController.getEmpresa();
                this.setEmpresaEditar(empresa);
                this.setTurnoEditar(turno);
                Whatsapp wsp = new Whatsapp(this.getTurnoEditar(), this.getEmpresaEditar());
                wsp.setVisible(true);
                wsp.setLocationRelativeTo(null);
               
            }
        }catch(Exception e){
            System.out.println("");
        }
    }//GEN-LAST:event_btnWhatsappMouseClicked

    private void txtDiaHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaHoyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaHoyActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        panel.removeAll();
        panel.add(this.panelInicial);
        panel.repaint();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void setDiaActual(){
        LocalDate fecha = LocalDate.now();
        cargarTablaTurnos(fecha);
        //  ESTO CONVIERTE DE LOCALDATE A DATE
        dateTurnosTodos.setDate(Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
    private void cargarPantalla(JPanel cargar){
        panel.removeAll();
        panel.add(cargar);
        panel.repaint();
    }
    
    private void cargarTablaMascotas(String buscado){
        DefaultTableModel modelo = new DefaultTableModel(){
            //filas y columnas no editables
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        String titulos[] = {"ID", "Nombre", "Sexo","Edad", "Dueño"};
        modelo.setColumnIdentifiers(titulos);
        
        List<Mascota> mascotas = mascotaController.getMascotasByBuscado(buscado);
        
        if(mascotas!=null){
            for(Mascota mascota : mascotas){
                Object[] objeto ={mascota.getId(), mascota.getNombre(), mascota.getSexo(), 
                    /*LO SIGUIENTE CALCULA LA EDAD->*/ String.valueOf(Period.between(mascota.getFechaNacimiento(), LocalDate.now()).getYears()), 
                    mascota.getCliente().getNombre()};
                modelo.addRow(objeto);
            }
        }
        tableMascotas.setModel(modelo);
        
    }
    
    private void cargarTablaClientes(String buscado){
        DefaultTableModel modelo = new DefaultTableModel(){
            //filas y columnas no editables
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        String titulos[] = {"ID", "Cliente", "Celular","Cant. de mascotas", "Cant. de turnos"};
        modelo.setColumnIdentifiers(titulos);
        
        List<Cliente> clientes = clienteController.getClientesByBuscado(buscado);
        
        if(clientes!=null){
            for(Cliente cliente : clientes){
                Object[] objeto = {cliente.getId(), cliente.getNombre(), cliente.getCelular(), String.valueOf(cliente.getMascotas().size()), String.valueOf(cliente.getTurnos().size())};
                modelo.addRow(objeto);
            }
        }
        
        tableClientes.setModel(modelo);
    }
    private void cargarTablaTurnos(LocalDate fecha){
        DefaultTableModel modelo = new DefaultTableModel(){
            //filas y columnas no editables
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        String titulos[] = {"ID", "Cliente", "Celular","Mascota", "Observaciones", "Fecha", "Hora"};
        modelo.setColumnIdentifiers(titulos);
        
        List<Turno> turnos = turnoController.getTurnos(fecha);
        
        if(turnos!=null){
            for(Turno turno : turnos){
                Object[] objeto = {turno.getId(), 
                    turno.getCliente().getNombre(), turno.getCliente().getCelular(), 
                    turno.getMascota().getNombre(), 
                    turno.getObservaciones(), 
                    turno.getFechaHora().toLocalDate(), turno.getFechaHora().toLocalTime()};
                modelo.addRow(objeto);
            }
        }
        
        tableTurnos.setModel(modelo);
        
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnBuscarMascota;
    private javax.swing.JButton btnCancelarCliente;
    private javax.swing.JButton btnCancelarConfiguracion;
    private javax.swing.JButton btnCancelarMascota;
    private javax.swing.JButton btnCancelarTurno;
    private javax.swing.JButton btnCancelarTurno1;
    private javax.swing.JPanel btnConfiguracion;
    private javax.swing.JPanel btnCrearCliente;
    private javax.swing.JButton btnCrearClienteInexistente;
    private javax.swing.JButton btnCrearClientes;
    private javax.swing.JPanel btnCrearMascota;
    private javax.swing.JButton btnCrearMascotaInexistente;
    private javax.swing.JButton btnCrearMascotas;
    private javax.swing.JPanel btnCrearTurno;
    private javax.swing.JButton btnCrearTurnos;
    private javax.swing.JButton btnDescartarClienteEditar;
    private javax.swing.JButton btnDescartarMascotaEditar;
    private javax.swing.JButton btnDiaActual;
    private javax.swing.JButton btnEditarClientes;
    private javax.swing.JButton btnEditarMascota;
    private javax.swing.JButton btnEditarTurnos;
    private javax.swing.JButton btnEliminarClientes;
    private javax.swing.JButton btnEliminarMascota;
    private javax.swing.JButton btnEliminarTurnos;
    private javax.swing.JButton btnGuardarCliente;
    private javax.swing.JButton btnGuardarClienteEditar;
    private javax.swing.JButton btnGuardarConfiguracion;
    private javax.swing.JButton btnGuardarMascota;
    private javax.swing.JButton btnGuardarMascotaEditar;
    private javax.swing.JButton btnGuardarTurno;
    private javax.swing.JButton btnGuardarTurno1;
    private javax.swing.JPanel btnInicio;
    private javax.swing.JButton btnLimpiarCliente;
    private javax.swing.JButton btnLimpiarClienteEditar;
    private javax.swing.JButton btnLimpiarMascota;
    private javax.swing.JButton btnLimpiarMascotaEditar;
    private javax.swing.JButton btnLimpiarTurno;
    private javax.swing.JButton btnLimpiarTurno1;
    private javax.swing.JButton btnResetBuscador;
    private javax.swing.JButton btnResetBuscadorMascota;
    private javax.swing.JPanel btnVerClientes;
    private javax.swing.JPanel btnVerMascotas;
    private javax.swing.JPanel btnVerTurnos;
    private javax.swing.JButton btnWhatsapp;
    private javax.swing.JButton btnWhatsapp1;
    private javax.swing.JComboBox<String> cmbClientesTurno;
    private javax.swing.JComboBox<String> cmbClientesTurnoEditar;
    private javax.swing.JComboBox<String> cmbDuenioMascota;
    private javax.swing.JComboBox<String> cmbDuenioMascotaEditar;
    private javax.swing.JComboBox<String> cmbMascotaTurno;
    private javax.swing.JComboBox<String> cmbMascotaTurnoEditar;
    private javax.swing.JComboBox<String> cmbSexoMascota;
    private javax.swing.JComboBox<String> cmbSexoMascotaEditar;
    private com.toedter.calendar.JDateChooser dateFechaTurno;
    private com.toedter.calendar.JDateChooser dateFechaTurnoEditar;
    private com.toedter.calendar.JDateChooser dateNacimientoMascota;
    private com.toedter.calendar.JDateChooser dateNacimientoMascotaEditar;
    private com.toedter.calendar.JDateChooser dateTurnosTodos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel logo;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelConfiguracion;
    private javax.swing.JPanel panelCrearCliente;
    private javax.swing.JPanel panelCrearMascota;
    private javax.swing.JPanel panelCrearTurno;
    private javax.swing.JPanel panelEditarCliente;
    private javax.swing.JPanel panelEditarMascota;
    private javax.swing.JPanel panelEditarTurno;
    private javax.swing.JPanel panelInicial;
    private javax.swing.JPanel panelVerClientes;
    private javax.swing.JPanel panelVerMascotas;
    private javax.swing.JPanel panelVerTurnos;
    private javax.swing.JTable tableClientes;
    private javax.swing.JTable tableMascotas;
    private javax.swing.JTable tableTurnos;
    private com.github.lgooddatepicker.components.TimePicker timeTurno;
    private com.github.lgooddatepicker.components.TimePicker timeTurnoEditar;
    private javax.swing.JTextField txtApellidoCliente;
    private javax.swing.JTextField txtBuscador;
    private javax.swing.JTextField txtBuscadorMascotas;
    private javax.swing.JTextField txtCantClientes;
    private javax.swing.JTextField txtCantMascotas;
    private javax.swing.JTextField txtCantTurnosHistory;
    private javax.swing.JTextField txtCantTurnosHoy;
    private javax.swing.JTextField txtCantTurnosMan;
    private javax.swing.JTextField txtCelularCliente;
    private javax.swing.JTextField txtCelularClienteEditar;
    private javax.swing.JTextField txtCelularConfiguracion;
    private javax.swing.JTextField txtColorMascota;
    private javax.swing.JTextField txtColorMascotaEditar;
    private javax.swing.JTextField txtCuitConfiguracion;
    private javax.swing.JTextField txtDiaHoy;
    private javax.swing.JTextField txtDireccionConfiguracion;
    private javax.swing.JTextField txtNombeMascotaEditar;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreClienteEditar;
    private javax.swing.JTextField txtNombreConfiguracion;
    private javax.swing.JTextField txtNombreMascota;
    private javax.swing.JTextArea txtObservacionesTurno;
    private javax.swing.JTextArea txtObservacionesTurnoEditar;
    private javax.swing.JTextField txtRazaMascota;
    private javax.swing.JTextField txtRazaMascotaEditar;
    private javax.swing.JTextArea txtWhatsappConfiguracion;
    // End of variables declaration//GEN-END:variables
}
