
package logica;

import java.time.LocalDate;
import java.util.List;
import persistencia.ControladoraPersistencia;

public class MascotaController {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    public boolean guardar(String nombre, String color, String raza, String sexo, String clienteTmp, LocalDate fechaNacimiento) {
        try{
            Mascota mascota = new Mascota();
            mascota.setNombre(nombre);
            mascota.setColor(color);
            mascota.setRaza(raza);
            mascota.setSexo(sexo);
            mascota.setFechaNacimiento(fechaNacimiento);
            
            Cliente cliente = controlPersis.getClientByNombre(clienteTmp);
            if(cliente!=null){
                mascota.setCliente(cliente);
                Mascota existe = controlPersis.getMascotaByNameAndCliente(nombre, cliente.getId());
                if(existe ==null || !existe.equals(mascota)){
                    controlPersis.guardarMascota(mascota);
                    return true;
                }
            }else{
                System.out.println("Cliente inexistente al querer guardar la mascota en la logica");
            }
            
        }catch(Exception e){
            System.out.println("Error al guardar la mascota en el controlador logico: "+e);
        }
        return false;
    }

    public List<Mascota> getMascotasByCliente(String clienteTmp) {
        Cliente cliente = controlPersis.getClientByNombre(clienteTmp);
        return controlPersis.getMascotasByCliente(cliente.getId());
    }

    public List<Mascota> getMascotasByBuscado(String buscado) {
        return controlPersis.getMascotasByBuscado(buscado);
    }

    public void eliminarMascota(int idMascota) {
        controlPersis.eliminarMascota(idMascota);
    }

    public Mascota getMascota(int idMascota) {
        return controlPersis.getMascota(idMascota);
    }

    public void guardarCambios(Mascota mascotaEditar, String nombre, String color, String raza, String sexo, String nombreCliente, LocalDate fechaNacimiento) {
        try{
            mascotaEditar.setNombre(nombre);
            mascotaEditar.setColor(color);
            mascotaEditar.setRaza(raza);
            mascotaEditar.setSexo(sexo);
            mascotaEditar.setFechaNacimiento(fechaNacimiento);
            
            Cliente cliente = controlPersis.getClientByNombre(nombreCliente);
            mascotaEditar.setCliente(cliente);
            
            controlPersis.modificarMascota(mascotaEditar);
        }catch (Exception e){
            System.out.println("Error al querer guardar los cambios en la logica: "+e);
        }
    }

    public List<Mascota> getMascotas() {
        return controlPersis.getMascotas();
    }

    
}
