
package logica;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Empresa implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String nombre;
    private String direccion;
    private String celular;
    
    @Column(columnDefinition = "LONGTEXT")
    private String txtWhatsapp;
    private String cuit;

    public Empresa() {
    }

    public Empresa(String nombre, String direccion, String celular, String txtWhatsapp, String cuit) {
       
        this.nombre = nombre;
        this.direccion = direccion;
        this.celular = celular;
        this.txtWhatsapp = txtWhatsapp;
        this.cuit = cuit;
    }
    public Empresa(int id, String nombre, String direccion, String celular, String txtWhatsapp, String cuit) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.celular = celular;
        this.txtWhatsapp = txtWhatsapp;
        this.cuit = cuit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTxtWhatsapp() {
        return txtWhatsapp;
    }

    public void setTxtWhatsapp(String txtWhatsapp) {
        this.txtWhatsapp = txtWhatsapp;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
    
    
    
    
}
