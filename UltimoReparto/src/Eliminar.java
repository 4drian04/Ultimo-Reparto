
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
public class Eliminar extends Comodin{
    public MouseAdapter mouseListener;
   public Eliminar(String nombre, String descripcion, Mesa mesa) {
		super(nombre, descripcion, mesa);
    }
    
    public void usarComodin(boolean isJugador, JLabel label){
        int indice;
        try{
            if(isJugador){
               label.removeMouseListener(mouseListener);
               label.setIcon(null);
            }
            decrementarCantidad(isJugador);
        
            if(isJugador){
                if(mesa.maquina.getMano().size()==1){
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "No se puede eliminar la carta oculta.", "USO INDEBIDO", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Integer carta = mesa.maquina.getMano().removeLast();
                    
                    indice=mesa.buscarPosicionMostrarCarta(false);
                    indice=indice==-1?mesa.cartasMaquina.length:indice;
                    mesa.cartasMaquina[indice-1].setIcon(null);
                    
                    mesa.mazo.add(carta);
                    mesa.conteoMaquina.setText(String.valueOf(Integer.parseInt(mesa.conteoMaquina.getText()) - carta));
                }
            }else{
                JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado '" + nombre + "'", "USO DE COMODÍN", JOptionPane.INFORMATION_MESSAGE);
                if(mesa.jugador.getMano().size()==1){
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "No se puede eliminar la carta oculta.", "USO INDEBIDO", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    Integer carta = mesa.jugador.getMano().removeLast();
                    indice=mesa.buscarPosicionMostrarCarta(true);
                    indice=indice==-1?mesa.cartasJugador.length:indice;
                    mesa.cartasJugador[indice-1].setIcon(null);
                    
                    mesa.mazo.add(carta);
                    mesa.conteoJugador.setText(String.valueOf(Integer.parseInt(mesa.conteoJugador.getText()) - carta));
                }
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
