
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
 * @author David y Adrián
 */
public class TurnoPerfecto extends Comodin{
    public MouseAdapter mouseListener;
    public TurnoPerfecto(String nombre, String descripcion, Mesa mesa) {
		super(nombre, descripcion, mesa);
    }
    
    
    public void usarComodin(boolean isJugador, JLabel label){
        int numeroPerfecto;
        boolean encontrado=false;
        try{
            if(isJugador){
               label.removeMouseListener(mouseListener);
               label.setIcon(null);
            }
            decrementarCantidad(isJugador);
            
            if(isJugador){
                numeroPerfecto=Integer.parseInt(mesa.textoObjetivoJugador.getText())-Integer.parseInt(mesa.conteoJugador.getText());

                numeroPerfecto=numeroPerfecto>11?11:numeroPerfecto;

                for (int i = numeroPerfecto; i > 0 && !encontrado; i--) {
                    if(mesa.mazo.contains(i)){
                        encontrado=true;
                        numeroPerfecto=i;
                    }
                }

                if(numeroPerfecto==0){
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "No se ha encontrado ningún número perfecto disponible.", "CARTAS INSUFICIENTES", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    mesa.introducirCarta(true, numeroPerfecto);
                }
            }else{
                JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado '" + nombre + "'", "USO DE COMODÍN", JOptionPane.INFORMATION_MESSAGE);
                numeroPerfecto=Integer.parseInt(mesa.textoObjetivoMaquina.getText())-Integer.parseInt(mesa.conteoMaquina.getText());

                numeroPerfecto=numeroPerfecto>11?11:numeroPerfecto;

                for (int i = numeroPerfecto; i > 0 && !encontrado; i--) {
                    if(mesa.mazo.contains(i)){
                        encontrado=true;
                        numeroPerfecto=i;
                    }
                }

                if(numeroPerfecto==0){
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, !isJugador?"La máquina ha usado el comodín '" + nombre + "' sin éxito.":"No se ha encontrado ningún número perfecto disponible.", "CARTA NO DISPONIBLE", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    mesa.introducirCarta(false, numeroPerfecto);
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
