/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Cliente;
import logica.Empresa;
import logica.Mascota;
import logica.Turno;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Ayrto
 */
public class ControladoraPersistencia {
    
    MascotaJpaController mascotaJPA = new MascotaJpaController();
    ClienteJpaController clienteJPA = new ClienteJpaController();
    TurnoJpaController turnoJPA = new TurnoJpaController();
    EmpresaJpaController empresaJPA = new EmpresaJpaController();

    public void guardarCliente(Cliente cliente) {
        clienteJPA.create(cliente);
    }

    public void guardarMascota(Mascota mascota) {
        mascotaJPA.create(mascota);
    }

    public List<Cliente> getClientes() {
        return clienteJPA.findClienteEntities();
    }

    public Cliente getCliente(int id) {
        return clienteJPA.findCliente(id);
    }

    public List<Mascota> getMascotasByCliente(int idCliente) {
        return mascotaJPA.findMascotasByClienteId(idCliente);
    }

    public Mascota getMascotaByNameAndCliente(String nombreMascota, int idCliente) {
        return mascotaJPA.findMascotaByNameAndClienteId(nombreMascota, idCliente);
    }

    public void guardarTurno(Turno turno) {
        turnoJPA.create(turno);
    }

    public List<Turno> getTurnosByFecha(LocalDate fecha) {
        return turnoJPA.findTurnoByFecha(fecha);
    }

    public void eliminarTurno(int idTurno) {
        try {
            turnoJPA.destroy(idTurno);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Turno findTurnoByFechaHora(LocalDateTime fechaHora) {
        return turnoJPA.findTurnoByFechaHora(fechaHora);
    }

    public List<Cliente> getClientesByBuscado(String buscado) {
        return clienteJPA.findClientesByBuscado(buscado);
    }

    public void eliminarCliente(int idCliente) {
        try {
            clienteJPA.destroy(idCliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Mascota> getMascotasByBuscado(String buscado) {
        return mascotaJPA.getMascotasByBuscado(buscado);
    }

    public void eliminarMascota(int idMascota) {
        try {
            mascotaJPA.destroy(idMascota);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente getClientByNombre(String clienteTmp) {
        return clienteJPA.findClienteByNombre(clienteTmp);
    }

    public Mascota getMascota(int idMascota) {
        return mascotaJPA.findMascota(idMascota);
    }

    public void modificarMascota(Mascota mascotaEditar) {
        try {
            mascotaJPA.edit(mascotaEditar);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificarCliente(Cliente clienteEditar) {
        try {
            clienteJPA.edit(clienteEditar);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Turno findTurno(int idTurno) {
        return turnoJPA.findTurno(idTurno);
    }
    
    public Empresa getEmpresa(){
        return empresaJPA.getEmpresa();
    }

    public void crearEmpresa(Empresa empresa) {
        empresaJPA.create(empresa);
    }

    public void modificarEmpresa(Empresa empresaEditar) {
        try {
            empresaJPA.edit(empresaEditar);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Mascota> getMascotas() {
        return mascotaJPA.findMascotaEntities();
    }

    public List<Turno> getTurnos() {
        return turnoJPA.findTurnoEntities();
    }

    

}
