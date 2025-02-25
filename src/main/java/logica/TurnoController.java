
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

   

    
    
}
