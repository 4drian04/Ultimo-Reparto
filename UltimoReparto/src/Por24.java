
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
public class Por24 extends Comodin{
    private static final int numero=24;
    public MouseAdapter mouseListenerMisComodines;
    public MouseAdapter mouseListenerMesa;
    private ArrayList<JLabel> labelsMesaJugador;
    private ArrayList<JLabel> labelsMesaMaquina;
    public Por24(String nombre, String descripcion, String nombreImagen, Mesa mesa) {
		super(nombre, descripcion, nombreImagen, mesa);
                labelsMesaJugador=new ArrayList<JLabel>();
                labelsMesaMaquina=new ArrayList<JLabel>();
    }
    
    public void ponerComodin(boolean isJugador, JLabel label){
        JLabel labelMesa;
        try{
            if(isJugador){
                labelMesa=mesa.comodinesMesaJugadorLabels[buscarEspacioMesa(isJugador)];
            }else{
                labelMesa=mesa.comodinesMaquina[buscarEspacioMesa(isJugador)];
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
            
            if(isJugador){
                label.removeMouseListener(mouseListenerMisComodines);
                label.setIcon(null);
            }

            decrementarCantidad(isJugador);

            if(isJugador){
                this.mesa.comodinesMesaJugador.addFirst(this);
                labelsMesaJugador.addFirst(labelMesa);
            }else{
                JOptionPane.showMessageDialog(mesa.JDialogJuego, "La máquina ha usado '" + nombre + "'", "USO DE COMODÍN", JOptionPane.INFORMATION_MESSAGE);
                this.mesa.comodinesMesaMaquina.addFirst(this);
                labelsMesaMaquina.addFirst(labelMesa);
            }

            this.mesa.textoObjetivoJugador.setText(String.valueOf(numero));
            this.mesa.textoObjetivoMaquina.setText(String.valueOf(numero));
            this.mesa.maquina.setNumeroObjetivo(numero);
            this.mesa.objetivos.add(numero);
        }catch(UltimoRepartoException e){
            JOptionPane.showMessageDialog(mesa.JDialogJuego, e.getMessage(), "COMODIN NO DISPONIBLE", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void quitarComodin(boolean isJugador){
        JLabel labelMesa;
        
        int ultimoObjetivo=21;
        
        if(this.mesa.objetivos.size()>1){
            this.mesa.objetivos.removeLast();
            ultimoObjetivo=this.mesa.objetivos.getLast();
        }
        
        this.mesa.textoObjetivoJugador.setText(String.valueOf(ultimoObjetivo));
        this.mesa.textoObjetivoMaquina.setText(String.valueOf(ultimoObjetivo));
        this.mesa.maquina.setNumeroObjetivo(ultimoObjetivo);
        
        if(!isJugador){
            this.mesa.comodinesMesaJugador.remove(this);
            labelMesa=labelsMesaJugador.removeFirst();
        }else{
            labelMesa=labelsMesaMaquina.removeFirst();
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
