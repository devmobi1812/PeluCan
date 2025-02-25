

package logica;

import gui.Inicio;
import gui.Bienvenido;
import persistencia.ControladoraPersistencia;

public class PeluCan {

    public static void main(String[] args) {
        try{
            EmpresaController empresaController = new EmpresaController();
            Empresa empresa = empresaController.getEmpresa();
            if(empresa==null){
                Bienvenido bienvenido = new Bienvenido();
                bienvenido.setVisible(true);
                bienvenido.setLocationRelativeTo(null);
                ControladoraPersistencia controlPersis = new ControladoraPersistencia();
            }else{
                Inicio home = new Inicio();
                home.setVisible(true);
                home.setLocationRelativeTo(null);
                ControladoraPersistencia controlPersis = new ControladoraPersistencia();
                
            }
            
        }catch(Exception e){
            System.out.println("Error al ejecutar la aplicacion: "+e);
        }
    }
}
