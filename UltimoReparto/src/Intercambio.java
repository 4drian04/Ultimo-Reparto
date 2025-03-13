
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
public class Intercambio extends Comodin{
    public MouseAdapter mouseListener;
    public Intercambio(String nombre, String descripcion, Mesa mesa) {
		super(nombre, descripcion, mesa);
    }
    
    public void usarComodin(boolean isJugador, JLabel label){ 
        try{
            if(isJugador){
               label.removeMouseListener(mouseListener);
               label.setIcon(null);
            }else{
                JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado '" + nombre + "'", "USO DE COMODÍN", JOptionPane.INFORMATION_MESSAGE);
            }
            decrementarCantidad(isJugador);
            if(mesa.jugador.getMano().size()==1 || mesa.maquina.getMano().size()==1){
                JOptionPane.showMessageDialog(mesa.JDialogJuego, !isJugador?"La máquina ha usado el comodín '" + nombre + "' sin éxito.":"No se puede intercambiar la carta oculta.", "USO INDEBIDO", JOptionPane.INFORMATION_MESSAGE);
            }else{
                try{
                    int cartaJugador=this.mesa.jugador.getMano().removeLast();
                    int cartaMaquina=this.mesa.maquina.getMano().removeLast();

                    mesa.conteoJugador.setText(String.valueOf(Integer.parseInt(mesa.conteoJugador.getText()) - cartaJugador));
                    mesa.conteoMaquina.setText(String.valueOf(Integer.parseInt(mesa.conteoMaquina.getText()) - cartaMaquina));

                    int indice;

                    indice=mesa.buscarPosicionMostrarCarta(true);
                    indice=indice==-1?mesa.cartasJugador.length:indice;

                    mesa.cartasJugador[indice-1].setIcon(null);


                    indice=mesa.buscarPosicionMostrarCarta(false);
                    indice=indice==-1?mesa.cartasJugador.length:indice;

                    mesa.cartasMaquina[indice-1].setIcon(null);

                    this.mesa.introducirCarta(true, cartaMaquina);
                    this.mesa.introducirCarta(false, cartaJugador);
                }catch(UltimoRepartoException e){
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, e.getMessage(), "MESA LLENA", JOptionPane.INFORMATION_MESSAGE);
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
