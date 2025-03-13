
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
public class Escudo extends Comodin{
    public MouseAdapter mouseListenerMisComodines;
    public MouseAdapter mouseListenerMesa;
    private ArrayList<JLabel> labelsMesaJugador;
    private ArrayList<JLabel> labelsMesaMaquina;
    public Escudo(String nombre, String descripcion, String nombreImagen, Mesa mesa) {
		super(nombre, descripcion, nombreImagen, mesa);
                labelsMesaJugador=new ArrayList<JLabel>();
                labelsMesaMaquina=new ArrayList<JLabel>();
    }
    
    public void ponerComodin(boolean isJugador, JLabel label){
        boolean esCorrecto=true;
        JLabel labelMesa;
        try{
            if(isJugador){
                labelMesa=mesa.comodinesMesaJugadorLabels[buscarEspacioMesa(isJugador)];
            }else{
                labelMesa=mesa.comodinesMaquina[buscarEspacioMesa(isJugador)];
            }
            
            
            if(isJugador){
                if(mesa.jugador.getApuesta()>0){
                    decrementarCantidad(isJugador);
                    this.mesa.jugador.bajarApuesta();
                    this.mesa.textoapuestaJugador.setText(String.valueOf(Integer.parseInt(getMesa().textoapuestaJugador.getText()) - 1));
                    this.mesa.comodinesMesaJugador.addFirst(this);
                    
                }else{
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "No se puede bajar más la apuesta.", "USO INDEBIDO", JOptionPane.INFORMATION_MESSAGE);
                    esCorrecto=false;
                }
            }else{
                if(mesa.maquina.getApuesta()>0){
                    decrementarCantidad(isJugador);
                    this.mesa.maquina.bajarApuesta();
                    this.mesa.textoapuestaMaquina.setText(String.valueOf(Integer.parseInt(getMesa().textoapuestaMaquina.getText()) - 1));
                    this.mesa.comodinesMesaMaquina.addFirst(this);
                }else{
                    JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado '" + nombre + "' sin éxito.", "USO INDEBIDO", JOptionPane.INFORMATION_MESSAGE);
                    esCorrecto=false;
                }
            }
            
            if(esCorrecto){
                if(isJugador){
                    label.removeMouseListener(mouseListenerMisComodines);
                    label.setIcon(null);
                }
                
                // Agregar un MouseListener al JLabel
                mouseListenerMesa = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JOptionPane.showMessageDialog(mesa.JDialogJuego, descripcion, nombre, JOptionPane.INFORMATION_MESSAGE);
                    }
                };

                labelMesa.addMouseListener(mouseListenerMesa);

                labelMesa.setIcon(new ImageIcon(getClass().getResource("img/comodines/"+nombreImagen)));
            }
            
            if(isJugador){
                this.mesa.comodinesMesaJugador.addFirst(this);
                labelsMesaJugador.addFirst(labelMesa);
            }else{
                JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado " + nombre, "USO DE COMODÍN", JOptionPane.INFORMATION_MESSAGE);
                this.mesa.comodinesMesaMaquina.addFirst(this);
                labelsMesaMaquina.addFirst(labelMesa);
            }
        }catch(UltimoRepartoException e){
            JOptionPane.showMessageDialog(mesa.JDialogJuego, e.getMessage(), "COMODIN NO DISPONIBLE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void quitarComodin(boolean isJugador){
        JLabel labelMesa;
        
        if(!isJugador){//Quien usa el comodín
            labelMesa=labelsMesaJugador.removeFirst();
            this.mesa.jugador.subirApuesta();
            this.mesa.textoapuestaJugador.setText(String.valueOf(Integer.parseInt(getMesa().textoapuestaJugador.getText()) + 1));
            this.mesa.comodinesMesaJugador.remove(this);
        }else{
            labelMesa=labelsMesaMaquina.removeFirst();
            this.mesa.maquina.subirApuesta();
            this.mesa.textoapuestaMaquina.setText(String.valueOf(Integer.parseInt(getMesa().textoapuestaMaquina.getText()) + 1));
            this.mesa.comodinesMesaMaquina.remove(this);
        }
        labelMesa.removeMouseListener(mouseListenerMesa);
        labelMesa.setIcon(null);
    }
    
    @Override
    public void asignarComodin(JLabel label) {
        super.asignarComodin();
        
        // Agregar un MouseListener al JLabel
        mouseListenerMisComodines = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ponerComodin(true, label);
            }
        };
        
        label.addMouseListener(mouseListenerMisComodines);
    }
    
}
