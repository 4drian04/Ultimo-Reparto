
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class Destruccion extends Comodin{
    public MouseAdapter mouseListener;
    public Destruccion(String nombre, String descripcion, Mesa mesa) {
		super(nombre, descripcion, mesa);
    }
    
    public void usarComodin(boolean isJugador, JLabel label){ 
        try{
            if(isJugador){
               label.removeMouseListener(mouseListener);
               label.setIcon(null);
            }
            
            decrementarCantidad(isJugador);
            
            if(isJugador){
                if(mesa.comodinesMesaMaquina.size()>0){
                    Comodin comodin=this.mesa.comodinesMesaMaquina.removeFirst();
                    quitarComodin(comodin, isJugador);
                }else{
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "El rival no tiene ningún comodín en la mesa", "USO INDEBIDO", JOptionPane.INFORMATION_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado '" + nombre + "'", "USO DE COMODÍN", JOptionPane.INFORMATION_MESSAGE);
                if(mesa.comodinesMesaJugador.size()>0){
                    Comodin comodin=this.mesa.comodinesMesaJugador.removeFirst();
                    quitarComodin(comodin, isJugador);
                }else{
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "El jugador no tiene ningún comodín en la mesa", "USO INDEBIDO", JOptionPane.INFORMATION_MESSAGE);//Esto nunca va a saltar
                }
            }
        }catch(UltimoRepartoException e){
            JOptionPane.showMessageDialog(mesa.JDialogJuego, e.getMessage(), "COMODIN NO DISPONIBLE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    public void quitarComodin(Comodin comodin, boolean isJugador) throws UltimoRepartoException{
        
        if(comodin instanceof Escudo){
            ((Escudo) comodin).quitarComodin(isJugador);
        }else{
            if(comodin instanceof Por24){
                ((Por24) comodin).quitarComodin(isJugador);
            }else{
                if(comodin instanceof Por27){
                    ((Por27) comodin).quitarComodin(isJugador);
                }else{
                    if(comodin instanceof UnoMas){
                        ((UnoMas) comodin).quitarComodin(isJugador);
                    }else{
                        throw new UltimoRepartoException("No se puede retirar este comodín, ya que, no permanece en la mesa.");
                    }
                }
            }
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
