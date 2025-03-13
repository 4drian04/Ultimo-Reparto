
import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author David
 */
public class Comodin {
    public int cantidad;
    public String nombre;
    public String descripcion;
    public String nombreImagen="No establecido";
    public Mesa mesa;

    public Comodin(String nombre, String descripcion, String nombreImagen, Mesa mesa) {
            this.cantidad = 0;
            this.nombre=nombre;
            this.descripcion = descripcion;
            this.nombreImagen=nombreImagen;
            this.mesa=mesa;
    }
    
    public Comodin(String nombre, String descripcion, Mesa mesa) {
            this.cantidad = 0;
            this.nombre=nombre;
            this.descripcion = descripcion;
            this.mesa=mesa;
    }

    public String getNombre() {
        return nombre;
    }
    
    public int getCantidad() {
            return cantidad;
    }
    public String getDescripcion() {
            return descripcion;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Comodin{" + "cantidad=" + cantidad + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

    /**
     * Incrementa la cantidad de una habilidad (este casao se da cuando se compre la habilidad en la tienda).
     */
    public void incrementarCantidad() {
            this.cantidad++;
    }

    /**
     * Decrementa la cantidad de una habilidad. Esto se da cuando el usuario o la máquina utilicen una habilidad.
     * @throws UltimoRepartoException Esto se lanza cuando se quiere utilizar una habildiad no teniendo cantidad para usarla.
     */
    public void decrementarCantidad(boolean isJugador) throws UltimoRepartoException {
            if(this.cantidad==0) {
                    throw new UltimoRepartoException("No tienes suficiente cantidad para usar este comodín." + cantidad);
            }else{
                this.cantidad--;
                System.out.println(nombre + ": " + cantidad);
                if(isJugador){
                    mesa.haRealizadoAccionJugador=true;
                }else{
                    mesa.haRealizadoAccionMaquina=true;
                }
            }
    }
    
    public void asignarComodin(){
        incrementarCantidad();
    }
    
    public void asignarComodin(JLabel label){
        incrementarCantidad();
    }
    
    public int buscarEspacioMesa(boolean isJugador)throws UltimoRepartoException{
        boolean encontrado=false;
        int indice=-1;
        
        if(isJugador){
            for (int i = 0; i < mesa.comodinesMesaJugadorLabels.length && !encontrado; i++) {
                if(mesa.comodinesMesaJugadorLabels[i].getIcon()==null){
                    encontrado=true;
                    indice=i;
                }
            }
            
            if(indice==-1){
                throw new UltimoRepartoException("No tienes más espacios disponibles para colocar el comodín.");
            }
        }else{
            for (int i = 0; i < mesa.comodinesMaquina.length && !encontrado; i++) {
                if(mesa.comodinesMaquina[i].getIcon()==null){
                    encontrado=true;
                    indice=i;
                }
            }
            
            if(indice==-1){
                throw new UltimoRepartoException("No tienes más espacios disponibles para colocar la carta.");
            }
        }
        
        return indice;
    }
}
