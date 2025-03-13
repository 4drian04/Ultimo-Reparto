
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author David
 */
public class Player {
    public static final int VIDA_MAXIMA = 10;
    private int vida;
    private int apuesta;
    private HashMap<String,Comodin> comodines;
    private ArrayList<Integer> mano;
    
    public Player(){
        this.vida = VIDA_MAXIMA;
        this.comodines = new HashMap<String,Comodin>();
        this.mano = new ArrayList<Integer>();
        this.apuesta=1;
    }

    public int getVida() {
        return vida;
    }

    public int getApuesta() {
        return apuesta;
    }

    public int acabarTurno(boolean haGanado) {
        if(!haGanado){
            vida-=apuesta;
        }
        apuesta = 1;
        
        return vida;
    }
    
    public void subirApuesta(){
        apuesta++;
    }
    
    public void bajarApuesta(){
        apuesta--;
    }

    public HashMap<String, Comodin> getComodines() {
        return comodines;
    }

    public ArrayList<Integer> getMano() {
        return mano;
    }
    
    public void reiniciarApuesta(){
        apuesta=1;
    }
    
    public int getPuntosJugador(){
        int puntos=0;
        for (Integer carta : mano) {
            puntos+=carta;
        }
        return puntos;
    }
}
