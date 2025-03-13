
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author David y Adrian
 */
public class Mesa {
    public boolean haRealizadoAccionJugador;
    public boolean haRealizadoAccionMaquina;
    public static JLabel[] cartasJugador;
    public static JLabel[] cartasMaquina;
    public static JLabel[] comodinesMesaJugadorLabels;
    public static JLabel[] comodinesMaquina;
    public static JLabel[] comodinesJugadorMisComodines;
    public static JLabel conteoMaquina;
    public static JLabel conteoJugador;
    public static JLabel valorCartaOculta;
    public static JLabel textoapuestaMaquina;
    public static JLabel textoapuestaJugador;
    public static JLabel textoObjetivoJugador;
    public static JLabel textoObjetivoMaquina;
    public ArrayList<String> bolsaComodines;
    public static HashSet<Integer> mazo;
    public Connection con;
    public static Player jugador;
    public static Maquina maquina;
    public static ArrayList<Integer> objetivos;
    public static ArrayList<Comodin> comodinesMesaMaquina;//ESTE ARRAY ESTÁ AL REVÉS. PA QUITAR DEL PRINCIPIO.
    public static ArrayList<Comodin> comodinesMesaJugador;//ESTE ARRAY ESTÁ AL REVÉS. PA QUITAR DEL PRINCIPIO.
    public static JDialog JDialogJuego;
    public HashMap<String, String> imagenesComodines;
    private int numeroRonda;
    private JDialog labelPantallaCarga;
    public Mesa(JDialog labelPantallaCarga ,Player jugador, Maquina maquina, JLabel[] cartasJugador, JLabel[] cartasMaquina, JLabel[] comodinesJugador, JLabel[] comodinesMaquina, JLabel conteoMaquina, JLabel conteoJugador, JLabel valorCartaOculta, JLabel textoapuestaJugador, JLabel textoapuestaMaquina, JLabel textoObjetivoJugador, JLabel textoObjetivoMaquina, JLabel[] comodinesJugadorMisComodines, JDialog juego, Connection con) throws UltimoRepartoException{
        this.jugador=jugador;
        this.maquina=maquina;
        this.cartasJugador = cartasJugador;
        this.cartasMaquina = cartasMaquina;
        this.comodinesMesaJugadorLabels = comodinesJugador;
        this.comodinesMaquina = comodinesMaquina;
        this.comodinesJugadorMisComodines = comodinesJugadorMisComodines;
        this.conteoMaquina=conteoMaquina;
        this.conteoJugador=conteoJugador;
        this.valorCartaOculta=valorCartaOculta;
        this.textoapuestaMaquina=textoapuestaMaquina;
        this.textoapuestaJugador=textoapuestaJugador;
        this.textoObjetivoJugador=textoObjetivoJugador;
        this.textoObjetivoMaquina=textoObjetivoMaquina;
        this.con=con;
        numeroRonda=0;
        objetivos=new ArrayList<Integer>();
        objetivos.add(21);
        mazo=new HashSet<Integer>();
        bolsaComodines=new ArrayList<String>();
        comodinesMesaJugador=new ArrayList<Comodin>();
        comodinesMesaMaquina=new ArrayList<Comodin>();
        this.JDialogJuego=juego;
        this.imagenesComodines=new HashMap<String, String>();
        this.haRealizadoAccionJugador=false;
        this.haRealizadoAccionMaquina=false;
        this.labelPantallaCarga = labelPantallaCarga;
        reestablecerMesa();
        
    }
    
    public void inicializarContenedoresComodines() throws UltimoRepartoException{
        String[][] comodinesAuxiliar=new String[15][3];
        try{
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("SELECT * FROM comodin");
            int i=0;
            while(rs.next()){
                comodinesAuxiliar[i][0]=rs.getString(2);
                comodinesAuxiliar[i][1]=rs.getString(3);
                comodinesAuxiliar[i][2]=rs.getString(4);
                imagenesComodines.put(rs.getString(2), rs.getString(4));
                i++;
            }
        }catch(SQLException e){
            throw new UltimoRepartoException("No se han podido cargar las habilidades.");
        }
        
        bolsaComodines.add(comodinesAuxiliar[0][0]);
        bolsaComodines.add(comodinesAuxiliar[1][0]);
        bolsaComodines.add(comodinesAuxiliar[2][0]);
        bolsaComodines.add(comodinesAuxiliar[3][0]);
        bolsaComodines.add(comodinesAuxiliar[4][0]);
        bolsaComodines.add(comodinesAuxiliar[5][0]);
        bolsaComodines.add(comodinesAuxiliar[6][0]);
        bolsaComodines.add(comodinesAuxiliar[7][0]);
        bolsaComodines.add(comodinesAuxiliar[8][0]);
        bolsaComodines.add(comodinesAuxiliar[9][0]);
        bolsaComodines.add(comodinesAuxiliar[10][0]);
        bolsaComodines.add(comodinesAuxiliar[11][0]);
        bolsaComodines.add(comodinesAuxiliar[12][0]);
        bolsaComodines.add(comodinesAuxiliar[13][0]);
        bolsaComodines.add(comodinesAuxiliar[14][0]);
        
        inicializarComodinesPlayers(comodinesAuxiliar);
    }
    
    public void rellenarMano(){
        mazo.clear();
        for (int i = 1; i <= 11; i++) {
            mazo.add(i);
        }
    }
    
    public int cogerCarta(){
        Random random=new Random();
        int carta;
        
        do{
            carta=random.nextInt(11)+1;
        }while(!mazo.contains(carta) && mazo.size()>0);
        
        return carta;
    }
    
    public int repartirCarta(Player player) throws UltimoRepartoException{
        int carta;
        
        carta=cogerCarta();
        
        if(player instanceof Maquina){
            introducirCarta(false, carta);
        }else{
            introducirCarta(true, carta);
        }
        
        return carta;
    }
    
    public void introducirCarta(boolean isJugador, int carta) throws UltimoRepartoException{
        int indice;
        
        if(isJugador){
            indice=buscarPosicionMostrarCarta(isJugador);
            
            cartasJugador[indice].setIcon(new ImageIcon(getClass().getResource("img/cartas/carta"+carta+".png")));
            conteoJugador.setText(String.valueOf(Integer.parseInt(conteoJugador.getText())+carta));
            jugador.getMano().add(carta);
            mazo.remove(carta);
        }else{
            indice=buscarPosicionMostrarCarta(isJugador);
            
            cartasMaquina[indice].setIcon(new ImageIcon(getClass().getResource("img/cartas/carta"+carta+".png")));
            conteoMaquina.setText(String.valueOf(Integer.parseInt(conteoMaquina.getText())+carta));
            maquina.getMano().add(carta);
            mazo.remove(carta);
        }
    }
    
    public int buscarPosicionMostrarCarta(boolean isJugador) throws UltimoRepartoException{
        boolean encontrado=false;
        int indice=-1;
        
        if(isJugador){
            for (int i = 0; i < cartasJugador.length && !encontrado; i++) {
                if(cartasJugador[i].getIcon()==null){
                    encontrado=true;
                    indice=i;
                }
            }
            
            if(indice==-1){
                throw new UltimoRepartoException("No tienes más espacios disponibles para colocar la carta.");
            }
        }else{
            for (int i = 0; i < cartasMaquina.length && !encontrado; i++) {
                if(cartasMaquina[i].getIcon()==null){
                    encontrado=true;
                    indice=i;
                }
            }
            
            if(indice==-1){
                throw new UltimoRepartoException("No tienes más espacios disponibles para colocar la carta.");
            }
        }
        
        return indice;
    }

    public JLabel[] getCartasJugador() {
        return cartasJugador;
    }

    public JLabel[] getCartasMaquina() {
        return cartasMaquina;
    }

    public HashSet<Integer> getMazo() {
        return mazo;
    }

    public Player getJugador() {
        return jugador;
    }

    public Maquina getMaquina() {
        return maquina;
    }
    
    
    
    private void inicializarComodinesPlayers(String[][] comodinesAuxiliar){
        jugador.getComodines().put(comodinesAuxiliar[0][0], new UnoMas(comodinesAuxiliar[0][0], comodinesAuxiliar[0][1], comodinesAuxiliar[0][2], this));
        jugador.getComodines().put(comodinesAuxiliar[1][0], new TurnoPerfecto(comodinesAuxiliar[1][0], comodinesAuxiliar[1][1], this));
        jugador.getComodines().put(comodinesAuxiliar[2][0], new Devolucion(comodinesAuxiliar[2][0], comodinesAuxiliar[2][1], this));
        jugador.getComodines().put(comodinesAuxiliar[3][0], new Eliminar(comodinesAuxiliar[3][0], comodinesAuxiliar[3][1], this));
        jugador.getComodines().put(comodinesAuxiliar[4][0], new Escudo(comodinesAuxiliar[4][0], comodinesAuxiliar[4][1], comodinesAuxiliar[4][2], this));
        jugador.getComodines().put(comodinesAuxiliar[5][0], new Destruccion(comodinesAuxiliar[5][0], comodinesAuxiliar[5][1], this));
        jugador.getComodines().put(comodinesAuxiliar[6][0], new Intercambio(comodinesAuxiliar[6][0], comodinesAuxiliar[6][1], this));
        jugador.getComodines().put(comodinesAuxiliar[7][0], new Numero2(comodinesAuxiliar[7][0], comodinesAuxiliar[7][1], this));
        jugador.getComodines().put(comodinesAuxiliar[8][0], new Numero3(comodinesAuxiliar[8][0], comodinesAuxiliar[8][1], this));
        jugador.getComodines().put(comodinesAuxiliar[9][0], new Numero4(comodinesAuxiliar[9][0], comodinesAuxiliar[9][1], this));
        jugador.getComodines().put(comodinesAuxiliar[10][0], new Numero5(comodinesAuxiliar[10][0], comodinesAuxiliar[10][1], this));
        jugador.getComodines().put(comodinesAuxiliar[11][0], new Numero6(comodinesAuxiliar[11][0], comodinesAuxiliar[11][1], this));
        jugador.getComodines().put(comodinesAuxiliar[12][0], new Numero7(comodinesAuxiliar[12][0], comodinesAuxiliar[12][1], this));
        jugador.getComodines().put(comodinesAuxiliar[13][0], new Por24(comodinesAuxiliar[13][0], comodinesAuxiliar[13][1], comodinesAuxiliar[13][2], this));
        jugador.getComodines().put(comodinesAuxiliar[14][0], new Por27(comodinesAuxiliar[14][0], comodinesAuxiliar[14][1], comodinesAuxiliar[14][2], this));
        
        maquina.getComodines().put(comodinesAuxiliar[0][0], new UnoMas(comodinesAuxiliar[0][0], comodinesAuxiliar[0][1], comodinesAuxiliar[0][2], this));
        maquina.getComodines().put(comodinesAuxiliar[1][0], new TurnoPerfecto(comodinesAuxiliar[1][0], comodinesAuxiliar[1][1], this));
        maquina.getComodines().put(comodinesAuxiliar[2][0], new Devolucion(comodinesAuxiliar[2][0], comodinesAuxiliar[2][1], this));
        maquina.getComodines().put(comodinesAuxiliar[3][0], new Eliminar(comodinesAuxiliar[3][0], comodinesAuxiliar[3][1], this));
        maquina.getComodines().put(comodinesAuxiliar[4][0], new Escudo(comodinesAuxiliar[4][0], comodinesAuxiliar[4][1], comodinesAuxiliar[4][2], this));
        maquina.getComodines().put(comodinesAuxiliar[5][0], new Destruccion(comodinesAuxiliar[5][0], comodinesAuxiliar[5][1], this));
        maquina.getComodines().put(comodinesAuxiliar[6][0], new Intercambio(comodinesAuxiliar[6][0], comodinesAuxiliar[6][1], this));
        maquina.getComodines().put(comodinesAuxiliar[7][0], new Numero2(comodinesAuxiliar[7][0], comodinesAuxiliar[7][1], this));
        maquina.getComodines().put(comodinesAuxiliar[8][0], new Numero3(comodinesAuxiliar[8][0], comodinesAuxiliar[8][1], this));
        maquina.getComodines().put(comodinesAuxiliar[9][0], new Numero4(comodinesAuxiliar[9][0], comodinesAuxiliar[9][1], this));
        maquina.getComodines().put(comodinesAuxiliar[10][0], new Numero5(comodinesAuxiliar[10][0], comodinesAuxiliar[10][1], this));
        maquina.getComodines().put(comodinesAuxiliar[11][0], new Numero6(comodinesAuxiliar[11][0], comodinesAuxiliar[11][1], this));
        maquina.getComodines().put(comodinesAuxiliar[12][0], new Numero7(comodinesAuxiliar[12][0], comodinesAuxiliar[12][1], this));
        maquina.getComodines().put(comodinesAuxiliar[13][0], new Por24(comodinesAuxiliar[13][0], comodinesAuxiliar[13][1],  comodinesAuxiliar[13][2],this));
        maquina.getComodines().put(comodinesAuxiliar[14][0], new Por27(comodinesAuxiliar[14][0], comodinesAuxiliar[14][1],  comodinesAuxiliar[14][2],this));
    }
    
    private void inicializarMazo(){
        for (int i = 1; i < 12; i++) {
            mazo.add(i);
        }
    }
    
    private void vaciarIconos(){
        for (JLabel carta : cartasJugador) {
            carta.setIcon(null);
        }
        
         for (JLabel carta : cartasMaquina) {
            carta.setIcon(null);
        }
         
         for (JLabel comodin : comodinesMesaJugadorLabels) {
            comodin.setIcon(null);
        }
         
         for (JLabel comodin : comodinesMaquina) {
            comodin.setIcon(null);
        }
         
         if(numeroRonda==1){
             for (JLabel comodin : comodinesJugadorMisComodines) {
                comodin.setIcon(null);
            }
         }
         
    }
    
    
    public String darComodin(){
        Random random=new Random();
        String comodin;
        int posicion;
        
        posicion=random.nextInt(15);
        
        comodin=bolsaComodines.get(posicion);
        
        return comodin;
    }
    
    public String repartirComodin(Player player) throws UltimoRepartoException{
        String comodin;
        
        comodin=darComodin();
        
        if(player instanceof Maquina){
            asignarComodin(false, comodin);
        }else{
            asignarComodin(true, comodin);
        }
        
        return comodin;
    }
    
    public void asignarComodin(boolean isJugador, String comodin)throws UltimoRepartoException{
        boolean encontrado=false;
        int indice=-1;
        
        if(isJugador){
            for (int i = 0; i < comodinesJugadorMisComodines.length && !encontrado; i++) {
                if(comodinesJugadorMisComodines[i].getIcon()==null){
                    encontrado=true;
                    indice=i;
                }
            }
            
            if(indice==-1){
                throw new UltimoRepartoException("No tienes más espacios disponibles para colocar el comodín.");
            }
            
            jugador.getComodines().get(comodin).asignarComodin(comodinesJugadorMisComodines[indice]);
            
            if(numeroRonda>1){
                JOptionPane.showMessageDialog(JDialogJuego, "Has recibido '" + comodin + "'", "GANASTE UN COMODÍN", JOptionPane.INFORMATION_MESSAGE);
            }
            
            comodin=imagenesComodines.get(comodin);
            comodinesJugadorMisComodines[indice].setIcon(new ImageIcon(getClass().getResource("img/comodines/"+comodin)));
        }else{
            maquina.getComodines().get(comodin).asignarComodin();
        }
    }
    
    public void reestablecerMesa()throws UltimoRepartoException{
        Random random=new Random();
        numeroRonda++;
        jugador.getMano().clear();
        maquina.getMano().clear();
        inicializarMazo();
        labelPantallaCarga.setVisible(false);
        if(numeroRonda==1){
            
            for (JLabel jlabel : comodinesJugadorMisComodines) {
                if(jlabel.getMouseListeners().length>0){
                    jlabel.removeMouseListener(jlabel.getMouseListeners()[0]);
                }
            }
            jugador.getComodines().clear();
            maquina.getComodines().clear();
            inicializarContenedoresComodines();
        }
        
        vaciarIconos();
        conteoMaquina.setText("0");
        conteoJugador.setText("0");
        textoapuestaJugador.setText("1");
        textoapuestaMaquina.setText("1");
        maquina.setNumeroObjetivo(21);
        textoObjetivoJugador.setText("21");
        textoObjetivoMaquina.setText("21");
        objetivos.clear();
        comodinesMesaMaquina.clear();
        comodinesMesaJugador.clear();
        
        int valorCartaOcultaNumero;
        valorCartaOcultaNumero=repartirCarta(jugador);
        valorCartaOculta.setText(String.valueOf(valorCartaOcultaNumero));
        repartirCarta(jugador);
        
        int cartaOcultaMaquina=cogerCarta();
        maquina.getMano().add(cartaOcultaMaquina);
        mazo.remove(cartaOcultaMaquina);
        cartasMaquina[0].setIcon(new ImageIcon(getClass().getResource("img/cartas/oculta.png")));
        repartirCarta(maquina);
        
        cartasJugador[0].setIcon(new ImageIcon(getClass().getResource("img/cartas/oculta.png")));
        
        if(numeroRonda==1){
            for (int i = 0; i < 3; i++) {
                repartirComodin(jugador);
            }

            for (int i = 0; i < 3; i++) {
                repartirComodin(maquina);
            }
            jugador.reiniciarApuesta();
        }else{
            if(jugador.getVida()>0 && maquina.getVida()>0){
                if(random.nextBoolean()){
                    repartirComodin(jugador);
                }
                if(random.nextBoolean()){
                    repartirComodin(maquina);
                    JOptionPane.showMessageDialog(JDialogJuego, "La máquina obtuvo un comodín.", "MAQUINA AFORTUNADA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
    }
}
