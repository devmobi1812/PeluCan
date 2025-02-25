
package logica;

import java.util.List;
import persistencia.ControladoraPersistencia;


public class ClienteController {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    public boolean guardar(String nombreCompleto, String celular) {
        try{
            Cliente cliente = new Cliente();
            cliente.setNombre(nombreCompleto);
            cliente.setCelular(celular);
            
            Cliente existe = controlPersis.getClientByNombre(nombreCompleto);
            if(existe ==null || !existe.equals(cliente)){
                controlPersis.guardarCliente(cliente);
                return true;
            }
            
        }catch(Exception e){
            System.out.println("Error al guardar al cliente en la logica: "+e);
        }
        return false;
    }

    public List<Cliente> getClientes() {
        return controlPersis.getClientes();
    }
    
    public Cliente getCliente(int id){
        return controlPersis.getCliente(id);
    }
    
    public List<Cliente> getClientesByBuscado(String buscado){
        return controlPersis.getClientesByBuscado(buscado);
    }

    public void eliminarCliente(int idCliente) {
        controlPersis.eliminarCliente(idCliente);
    }

    public void guardarCambios(Cliente clienteEditar, String nombre, String celular) {
        clienteEditar.setNombre(nombre);
        clienteEditar.setCelular(celular);
        controlPersis.modificarCliente(clienteEditar);
    }

    
    
    
}
