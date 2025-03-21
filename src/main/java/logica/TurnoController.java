
package logica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import persistencia.ControladoraPersistencia;


public class TurnoController {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    public void guardar(String clienteTmp, String nombreMascota, LocalDateTime fechaHora, String observaciones) {
        Turno turno = new Turno();
        Cliente cliente = controlPersis.getClientByNombre(clienteTmp);
        Mascota mascota = controlPersis.getMascotaByNameAndCliente(nombreMascota, cliente.getId());
        turno.setCliente(cliente);
        turno.setMascota(mascota);
        turno.setFechaHora(fechaHora);
        turno.setObservaciones(observaciones);
        
        controlPersis.guardarTurno(turno);
    }

    public boolean modificar(Turno turno, String clienteNombre, String nombreMascota, LocalDateTime fechaHora, String observaciones){
         try{
            Cliente cliente = controlPersis.getClientByNombre(clienteNombre);
            Mascota mascota = controlPersis.getMascotaByNameAndCliente(nombreMascota, cliente.getId());
            
            turno.setCliente(cliente);
            turno.setMascota(mascota);
            turno.setFechaHora(fechaHora);
            turno.setObservaciones(observaciones);
            
            controlPersis.modificarTurno(turno);
            return true;
        }catch(Exception e){
            System.out.println("Error al modificar el turno en la logica: "+e);
            return false;
        }
    }
    public List<Turno> getTurnos(LocalDate fecha) {
        return controlPersis.getTurnosByFecha(fecha);
    }

    public void eliminarTurno(int idTurno) {
        controlPersis.eliminarTurno(idTurno);
    }

    public Turno findTurnoByFechaHora(LocalDateTime fechaHora) {
        return controlPersis.findTurnoByFechaHora(fechaHora);
    }

    public Turno getTurno(int idTurno) {
        return controlPersis.findTurno(idTurno);
    }

    public List<Turno> getTurnos() {
        return controlPersis.getTurnos();
    }

    public List<Turno> getTurnosHistoricos(int idCliente) {
        return controlPersis.getTurnosHistoricos(idCliente);
    }

    public Turno findTurnoByFechaHora(Turno turno, LocalDateTime fechaHora) {
        try{
            Turno encontrado = controlPersis.findTurnoByFechaHora(fechaHora);
            if(turno.getId()==encontrado.getId()){
                return null;
            }
            return encontrado;
        }catch(Exception e){
            System.out.println("Error al encontrar turno por fecha y hora: "+e);
            return null;
        }
    }

   

    
    
}
