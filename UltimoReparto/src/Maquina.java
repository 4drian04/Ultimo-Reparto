
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author David
 */
public class Maquina extends Player{
    private Player jugador;
    private Dificultad dificultad;
    private int numeroObjetivo=21;
    
    public Maquina(Dificultad dificultad, Player jugador) {
        super();
        this.dificultad=dificultad;
        this.jugador=jugador;
    }
    
    public boolean turno(){
        boolean pedir=false;
        int puntos=0;
        double cartaMaximaNecesitar, cantidadCartasNecesitar=0, porcentaje;
        ArrayList<Integer> cartasJugador=new ArrayList<Integer>(jugador.getMano());
        ArrayList<Integer> cartasDisponibles=new ArrayList<Integer>();
        
        usarComodines();
        puntos=calcularMisPuntos();
        cartasJugador.removeFirst();
        
        
        cartaMaximaNecesitar=numeroObjetivo-puntos;
        cartaMaximaNecesitar=cartaMaximaNecesitar>11?11:cartaMaximaNecesitar;
        for (int i = 1; i <= 11; i++) {
            if(!getMano().contains(i) && !cartasJugador.contains(i)){
                cartasDisponibles.add(i);
                if(i<=cartaMaximaNecesitar){
                    cantidadCartasNecesitar++;
                }
            }
        }
        
        porcentaje=(cantidadCartasNecesitar/cartasDisponibles.size())*100;
        
        if(porcentaje>=dificultad.getPorcentajeArriesgo()){
          pedir=true;  
        }
        
        return pedir;
    }

    public int getNumeroObjetivo() {
        return numeroObjetivo;
    }
   
    public String getDificultad() {
        return dificultad.toString();
    }

    public void setNumeroObjetivo(int numeroObjetivo) {
        this.numeroObjetivo = numeroObjetivo;
    }
    
    public void usarComodines(){
        int puntosJugador;
        int misPuntos;
        Escudo escudo;
        Destruccion destruccion;
        Devolucion devolucion;
        Eliminar eliminar;
        Intercambio intercambio;
        Numero2 num2;
        Numero3 num3;
        Numero4 num4;
        Numero5 num5;
        Numero6 num6;
        Numero7 num7;
        Por24 por24;
        Por27 por27;
        TurnoPerfecto turnoPerfecto;
        UnoMas unoMas;
        
        escudo=(Escudo)this.getComodines().get("Escudo");
        destruccion=(Destruccion)this.getComodines().get("Destruccion");
        devolucion=(Devolucion)this.getComodines().get("Devolucion");
        eliminar=(Eliminar)this.getComodines().get("Eliminar");
        intercambio=(Intercambio)this.getComodines().get("Intercambio");
        num2=(Numero2)this.getComodines().get("Carta numero 2");
        num3=(Numero3)this.getComodines().get("Carta numero 3");
        num4=(Numero4)this.getComodines().get("Carta numero 4");
        num5=(Numero5)this.getComodines().get("Carta numero 5");
        num6=(Numero6)this.getComodines().get("Carta numero 6");
        num7=(Numero7)this.getComodines().get("Carta numero 7");
        por24=(Por24)this.getComodines().get("A por 24");
        por27=(Por27)this.getComodines().get("A por 27");
        turnoPerfecto=(TurnoPerfecto)this.getComodines().get("Turno perfecto");
        unoMas=(UnoMas)this.getComodines().get("Uno mas");
        
        while(destruccion.getCantidad()>0 && (jugador.getApuesta()==0 || getApuesta()>1)){
            destruccion.usarComodin(false, null);
        }
        
        while(escudo.getCantidad()>0 && getApuesta()>0){
            escudo.ponerComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        puntosJugador=calcularPuntosJugador();
        if((intercambio.getCantidad()>0 && jugador.getMano().size()>1) && (misPuntos-getMano().getLast()+jugador.getMano().getLast()>puntosJugador-jugador.getMano().getLast()+getMano().getLast())){
            intercambio.usarComodin(false, null);
        }
        
        puntosJugador=calcularPuntosJugador();
        while(eliminar.getCantidad()>0 && jugador.getMano().size()>1 && numeroObjetivo-puntosJugador>=10 && jugador.getPuntosJugador()<=numeroObjetivo){
            eliminar.usarComodin(false, null);
            puntosJugador=calcularPuntosJugador();
        }
        
        while(unoMas.getCantidad()>0 && jugador.getApuesta()<jugador.getVida()){
            unoMas.ponerComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(por27.getCantidad()>0 && misPuntos>24){
            por27.ponerComodin(false, null);
        }
        
        if(por24.getCantidad()>0 && misPuntos>21 && numeroObjetivo==21){
            por24.ponerComodin(false, null);
        }
        
        if(devolucion.getCantidad()>0 && misPuntos>numeroObjetivo && numeroObjetivo!=27){
            devolucion.usarComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(num7.getCantidad()>0 && !getMano().contains(7) && misPuntos<=numeroObjetivo-7 && !revisarCartasVisible(7)){
            num7.usarComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(num6.getCantidad()>0 && !getMano().contains(6) && misPuntos<=numeroObjetivo-6 && !revisarCartasVisible(6)){
            num6.usarComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(num5.getCantidad()>0 && !getMano().contains(5) && misPuntos<=numeroObjetivo-5 && !revisarCartasVisible(5)){
            num5.usarComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(num4.getCantidad()>0 && !getMano().contains(4) && misPuntos<=numeroObjetivo-4 && !revisarCartasVisible(4)){
            num4.usarComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(num3.getCantidad()>0 && !getMano().contains(3) && misPuntos<=numeroObjetivo-3 && !revisarCartasVisible(3)){
            num3.usarComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(num2.getCantidad()>0 && !getMano().contains(2) && misPuntos<=numeroObjetivo-2 && !revisarCartasVisible(2)){
            num2.usarComodin(false, null);
        }
        
        misPuntos=calcularMisPuntos();
        if(turnoPerfecto.getCantidad()>0 && ((misPuntos>=numeroObjetivo-11 && misPuntos<=numeroObjetivo-8) || misPuntos==numeroObjetivo-1)){
            turnoPerfecto.usarComodin(false, null);
        }
    }
    
    private int calcularPuntosJugador(){
        int puntos=0;
        
        for (int i = 1; i < jugador.getMano().size(); i++) {
            puntos+=jugador.getMano().get(i);
        }
        
        return puntos;
    }
    
    public int calcularMisPuntos(){
        int puntos=0;
        
        for (Integer carta : getMano()) {
            puntos+=carta;
        }
        
        return puntos;
    }
    private boolean revisarCartasVisible(int numero){
        boolean esta = false;
        for(int i = 1;i<jugador.getMano().size() && !esta;i++){
            if(jugador.getMano().get(i) == numero){
                esta=true;
            }
        }
        return esta;
    }
    
}
