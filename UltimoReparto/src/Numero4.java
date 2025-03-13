
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author David
 */
public class Numero4 extends Comodin{
    private static final int numero=4;
    public MouseAdapter mouseListener;
    public Numero4(String nombre, String descripcion, Mesa mesa) {
		super(nombre, descripcion, mesa);
    }
    
    public void usarComodin(boolean isJugador, JLabel label){ 
        try{
            if(isJugador){
               label.removeMouseListener(mouseListener);
               label.setIcon(null);
            }
            decrementarCantidad(isJugador);
        
            if(this.mesa.mazo.contains(numero)){
                this.mesa.mazo.remove(numero);
                if(isJugador){
                    this.mesa.jugador.getMano().add(numero);
                    this.mesa.introducirCarta(true, numero);
                }else{
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado '" + nombre + "'", "USO DE COMODÍN", JOptionPane.INFORMATION_MESSAGE);
                    this.mesa.maquina.getMano().add(numero);
                    this.mesa.introducirCarta(false, numero);
                }
            }else{
                JOptionPane.showMessageDialog(mesa.JDialogJuego, !isJugador?"La máquina ha usado el comodín '" + nombre + "' sin éxito.":"La carta número " + numero + " no está en el mazo.", "CARTA NO DISPONIBLE", JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(UltimoRepartoException e){
            JOptionPane.showMessageDialog(mesa.JDialogJuego, e.getMessage(), "COMODIN NO DISPONIBLE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    @Override
    public void asignarComodin(JLabel label) {
        super.asignarComodin();
        
        // Agregar un MouseListener al JLabel
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usarComodin(true, label);
            }
        };
        
        label.addMouseListener(mouseListener);
    }
}
