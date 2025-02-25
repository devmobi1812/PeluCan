
package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Cliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String nombre;
    private String celular;
    
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private LinkedList<Mascota> mascotas;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private LinkedList<Turno> turnos;
    

    public Cliente() {
    }

    public Cliente(int id, String nombre, String celular, LinkedList<Turno> turnos, LinkedList<Mascota> mascotas) {
        this.id = id;
        this.nombre = nombre;
        this.celular = celular;
        this.turnos= turnos;
        this.mascotas= mascotas;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public LinkedList<Mascota> getMascotas() {
        return mascotas;
    }

   public void setMascotas(LinkedList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public LinkedList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(LinkedList<Turno> turnos) {
        this.turnos = turnos;
    }

    @Override
    public boolean equals(Object cliente) {
        if (this == cliente) 
            return true;
        if (cliente == null || getClass() != cliente.getClass())
            return false;
        Cliente convertido = (Cliente) cliente;
        return Objects.equals(this.getNombre(), convertido.getNombre());
    }

    
}
