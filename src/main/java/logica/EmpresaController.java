
package logica;


import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import javax.swing.JOptionPane;
import persistencia.ControladoraPersistencia;


public class EmpresaController {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    
    public Empresa getEmpresa(){
        return controlPersis.getEmpresa();
    }

    public void crearEmpresa(String nombre, String celular) {
        try{
            Empresa empresa = new Empresa();
            empresa.setNombre(nombre);
            empresa.setCelular(celular);
            
            controlPersis.crearEmpresa(empresa);
        }catch(Exception e){
            System.out.println("Error al crear la empresa en la logica: "+e);
        }
    }

    public boolean guardarCambios(Empresa empresaEditar, String nombre, String celular, String direccion, String cuit, String wsp) {
        try{
            empresaEditar.setNombre(nombre);
            empresaEditar.setCelular(celular);
            empresaEditar.setCuit(cuit);
            empresaEditar.setDireccion(direccion);
            empresaEditar.setTxtWhatsapp(wsp);
            
            controlPersis.modificarEmpresa(empresaEditar);
            return true;
        }catch(Exception e){
            System.out.println("Error al actualizar la empresa en la logica: "+e);
        }
        return false;
    }

    public boolean enviarWhatsApp(String celular, String texto) {
        try{
            String areaPais ="549"; //  CODIGO DE AREA DE ARGENTINA
            String mensaje = URLEncoder.encode(texto, "UTF-8");
            String link = "https://api.whatsapp.com/send?phone=+"+areaPais+celular+"&text="+mensaje;
            URI url = new URI(link);
            if (Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(url);
                return true;
            }else{
                System.out.println("El sistema no admite la apertura automática de enlaces en el navegador.");
                return false;
            }
        }catch(Exception e){
            System.out.println("Error al crear el link de WhatsApp y enviarlo en la logica: "+e);
        }
        return false;
    }
}
