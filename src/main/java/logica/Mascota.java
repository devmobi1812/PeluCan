
package logica;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Mascota implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int id;
    private String nombre;
    private String color;
    private String raza;
    private String sexo;
    private LocalDate fechaNacimiento;
    
    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "mascota", fetch = FetchType.LAZY)
    private LinkedList<Turno> turnos;
    
    public Mascota() {
    }

    public Mascota(int id, String nombre, String color, String raza, String sexo, LocalDate fechaNacimiento, Cliente cliente, LinkedList<Turno> turnos) {
        this.id = id;
        this.nombre = nombre;
        this.color = color;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.cliente = cliente;
        this.turnos= turnos;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LinkedList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(LinkedList<Turno> turnos) {
        this.turnos = turnos;
    }
    
    @Override
    public boolean equals(Object mascota){
        if(this == mascota){
            return true;
        }
        if (mascota == null || getClass() != mascota.getClass()){
            return false;
        }  
        Mascota convertido = (Mascota) mascota;
        return Objects.equals(this.getNombre(), convertido.getNombre())
               && Objects.equals(this.getCliente().getId(), convertido.getCliente().getId());
    }

    

    
    
    
    
}
