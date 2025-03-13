
import javax.sound.sampled.Clip;
import javax.swing.JDialog;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adria
 */
public class PantallaCarga extends Thread{
    
    private Clip audioGanador;
    private JDialog pantallaCarga;
    private JDialog jDialogFinRonda;

    public PantallaCarga(Clip audioGanador, JDialog pantallaCarga, JDialog jDialogFinRonda) {
        this.audioGanador = audioGanador;
        this.pantallaCarga=pantallaCarga;
        this.jDialogFinRonda = jDialogFinRonda;
    }

    @Override
    public void run() {
        audioGanador.setFramePosition(0);
        audioGanador.start();
        try{
            sleep(4000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        audioGanador.stop();
        pantallaCarga.setVisible(false);
        jDialogFinRonda.setVisible(true);
    }
    
    
}
