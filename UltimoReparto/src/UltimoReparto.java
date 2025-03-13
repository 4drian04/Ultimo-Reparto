
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author David y Adrián
 */
public class UltimoReparto extends javax.swing.JFrame {

    /**
     * Creates new form UltimoReparto
     */
    private int ganador;
    private int idJugador;
    private int idMaquina;
    public String nombreJugador;
    public String usuarioJugador;
    public String nombreMaquina;
    public String nombrePartida;
    public int puntosJugador1;
    public int puntosJugador2;
    public boolean jugador1Terminado;
    public boolean jugador2Terminado;
    private static Connection con;
    public JLabel[] cartasJugador=new JLabel[9];
    public JLabel[] cartasMaquina=new JLabel[9];
    public JLabel[] comodinesJugador=new JLabel[11];
    public JLabel[] comodinesMaquina=new JLabel[11];
    public JLabel[] comodinesJugadorMisComodines=new JLabel[12];
    public Mesa mesa;
    public Player jugador;
    public Maquina maquina;
    private int idPartida;
    private JLabel[] imagenesInfoComodines = new JLabel[15];
    private JLabel[] nombreImagenesInfoComodines = new JLabel[15];
    public static PartidaTableModel modeloPartida;
    public static ComodinTableModel modeloComodin;
    Clip clipMusicaFondo;
    FloatControl volumeControlMusicaFondo;
    Clip clipAnunciarGanador;
    FloatControl volumeControlAnunciarGanador;
    boolean musicaParada;
    public UltimoReparto() {
        initComponents();
        implementarSonido();
        this.setLocationRelativeTo(null);
        inicializarArrays();
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ultimoreparto", "root", "");
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
        rellenarImagenComodinesInfo();
        
        jDialogFinRonda.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                onWindowClosing();
            }
        });
    }
    /**
     * Inicializa todos los arrays que se usan en el programa
     */
    public void inicializarArrays(){
        inicializarArrayCartasJugador();
        inicializarArrayCartasMaquina();
        inicializarArrayComodinesJugador();
        inicializarArrayComodinesMaquina();
        inicializarArrayComodinesJugadorMisComodines();
        inicializarDatosInfoComodines();
    }
    /**
     * Inicializa el array de las cartas del jugador de la mesa
     */
    public void inicializarArrayCartasJugador(){
        cartasJugador[0]=cartaJugador1;
        cartasJugador[1]=cartaJugador2;
        cartasJugador[2]=cartaJugador3;
        cartasJugador[3]=cartaJugador4;
        cartasJugador[4]=cartaJugador5;
        cartasJugador[5]=cartaJugador6;
        cartasJugador[6]=cartaJugador7;
        cartasJugador[7]=cartaJugador8;
        cartasJugador[8]=cartaJugador9;
    }
    /**
     * Inicializa el array de las cartas de la máquina
     */
    public void inicializarArrayCartasMaquina(){
        cartasMaquina[0]=cartaMaquina1;
        cartasMaquina[1]=cartaMaquina2;
        cartasMaquina[2]=cartaMaquina3;
        cartasMaquina[3]=cartaMaquina4;
        cartasMaquina[4]=cartaMaquina5;
        cartasMaquina[5]=cartaMaquina6;
        cartasMaquina[6]=cartaMaquina7;
        cartasMaquina[7]=cartaMaquina8;
        cartasMaquina[8]=cartaMaquina9;
    }
    /**
     * Inicializa el array de comodines del jugador en la mesa 
     */
    public void inicializarArrayComodinesJugador(){
        comodinesJugador[0]=comodinMesaJugador1;
        comodinesJugador[1]=comodinMesaJugador2;
        comodinesJugador[2]=comodinMesaJugador3;
        comodinesJugador[3]=comodinMesaJugador4;
        comodinesJugador[4]=comodinMesaJugador5;
        comodinesJugador[5]=comodinMesaJugador6;
        comodinesJugador[6]=comodinMesaJugador7;
        comodinesJugador[7]=comodinMesaJugador8;
        comodinesJugador[8]=comodinMesaJugador9;
        comodinesJugador[9]=comodinMesaJugador10;
        comodinesJugador[10]=comodinMesaJugador11;
    }
    /**
     * Inicializa el array de comodines de la máquina recogiendo los valores de la vista
     */
     public void inicializarArrayComodinesMaquina(){
        comodinesMaquina[0]=comodinMaquina1;
        comodinesMaquina[1]=comodinMaquina2;
        comodinesMaquina[2]=comodinMaquina3;
        comodinesMaquina[3]=comodinMaquina4;
        comodinesMaquina[4]=comodinMaquina5;
        comodinesMaquina[5]=comodinMaquina6;
        comodinesMaquina[6]=comodinMaquina7;
        comodinesMaquina[7]=comodinMaquina8;
        comodinesMaquina[8]=comodinMaquina9;
        comodinesMaquina[9]=comodinMaquina10;
        comodinesMaquina[10]=comodinMaquina11;
    }
     /**
      * Inicializa el array de comodines personales del jugador recogiendo los valores de la vista
      */
     public void inicializarArrayComodinesJugadorMisComodines(){
        comodinesJugadorMisComodines[0]=comodinJugador1;
        comodinesJugadorMisComodines[1]=comodinJugador2;
        comodinesJugadorMisComodines[2]=comodinJugador3;
        comodinesJugadorMisComodines[3]=comodinJugador4;
        comodinesJugadorMisComodines[4]=comodinJugador5;
        comodinesJugadorMisComodines[5]=comodinJugador6;
        comodinesJugadorMisComodines[6]=comodinJugador7;
        comodinesJugadorMisComodines[7]=comodinJugador8;
        comodinesJugadorMisComodines[8]=comodinJugador9;
        comodinesJugadorMisComodines[9]=comodinJugador10;
        comodinesJugadorMisComodines[10]=comodinJugador11;
        comodinesJugadorMisComodines[11]=comodinJugador12;
    }
     /**
      * Recupera los comodines de la vista y lo guarda en un array 
      */
     private void inicializarDatosInfoComodines(){
        imagenesInfoComodines[0]=imagenComodin1;
        imagenesInfoComodines[1]=imagenComodin2;
        imagenesInfoComodines[2]=imagenComodin3;
        imagenesInfoComodines[3]=imagenComodin4;
        imagenesInfoComodines[4]=imagenComodin5;
        imagenesInfoComodines[5]=imagenComodin6;
        imagenesInfoComodines[6]=imagenComodin7;
        imagenesInfoComodines[7]=imagenComodin8;
        imagenesInfoComodines[8]=imagenComodin9;
        imagenesInfoComodines[9]=imagenComodin10;
        imagenesInfoComodines[10]=imagenComodin11;
        imagenesInfoComodines[11]=imagenComodin12;
        imagenesInfoComodines[12]=imagenComodin13;
        imagenesInfoComodines[13]=imagenComodin14;
        imagenesInfoComodines[14]=imagenComodin15;
        
        nombreImagenesInfoComodines[0]=detalleComodin1;
        nombreImagenesInfoComodines[1]=detalleComodin2;
        nombreImagenesInfoComodines[2]=detalleComodin3;
        nombreImagenesInfoComodines[3]=detalleComodin4;
        nombreImagenesInfoComodines[4]=detalleComodin5;
        nombreImagenesInfoComodines[5]=detalleComodin6;
        nombreImagenesInfoComodines[6]=detalleComodin7;
        nombreImagenesInfoComodines[7]=detalleComodin8;
        nombreImagenesInfoComodines[8]=detalleComodin9;
        nombreImagenesInfoComodines[9]=detalleComodin10;
        nombreImagenesInfoComodines[10]=detalleComodin11;
        nombreImagenesInfoComodines[11]=detalleComodin12;
        nombreImagenesInfoComodines[12]=detalleComodin13;
        nombreImagenesInfoComodines[13]=detalleComodin14;
        nombreImagenesInfoComodines[14]=detalleComodin15;
     }
     /**
      * Rellenamos las imagenes de comodines accediendo a la base de datos
      */
     private void rellenarImagenComodinesInfo(){
         try{
             Statement st= con.createStatement();
             ResultSet rs = st.executeQuery("SELECT nombre, nombreImagen FROM comodin");
             int contador = 0;
             while(rs.next()){
                 imagenesInfoComodines[contador].setIcon(new ImageIcon(getClass().getResource("img/comodines/" + rs.getString(2))));
                 nombreImagenesInfoComodines[contador].setText(rs.getString(1));
                 contador++;
             }
         }catch(SQLException e){
             e.printStackTrace();
         }
         
     }
     /**
      * Implementamos el sonido de fondo al iniciar el programa
      */
     private void implementarSonido(){
         musicaParada=true;
         try {
            InputStream audioSrcMusicaFondo = getClass().getResourceAsStream("sonido/musicaFondo.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrcMusicaFondo);
            AudioInputStream audioInputMusicaFondo = AudioSystem.getAudioInputStream(bufferedIn);
            clipMusicaFondo = AudioSystem.getClip();
            clipMusicaFondo.open(audioInputMusicaFondo);

            // Obtener el control de volumen
            volumeControlMusicaFondo = (FloatControl) clipMusicaFondo.getControl(FloatControl.Type.MASTER_GAIN);
            bajarVolumenMusica();
            clipMusicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
            musicaParada=false;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(this, "No se ha podido cargar la música de fondo.", "SONIDO", JOptionPane.INFORMATION_MESSAGE);
        }
         
        try {
            InputStream audioSrcAnunciarGanador = getClass().getResourceAsStream("sonido/AnunciarGanador.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrcAnunciarGanador);
            AudioInputStream audioInputAnunciarGanador = AudioSystem.getAudioInputStream(bufferedIn);
            clipAnunciarGanador = AudioSystem.getClip();
            clipAnunciarGanador.open(audioInputAnunciarGanador);
            clipAnunciarGanador.loop(Clip.LOOP_CONTINUOUSLY);
            clipAnunciarGanador.stop();

            // Obtener el control de volumen
            volumeControlAnunciarGanador = (FloatControl) clipAnunciarGanador.getControl(FloatControl.Type.MASTER_GAIN);
//            volumeControlAnunciarGanador.setValue(volumeControlAnunciarGanador.getValue() - 10.0f);
       }catch (Exception ex){
           JOptionPane.showMessageDialog(this, "No se ha podido cargar la música de anunciar el ganador.", "SONIDO", JOptionPane.INFORMATION_MESSAGE);
       }
     }
     /**
      * Permite subir el volumen de la música
      */
     private void subirVolumenMusica(){
         // Subir el volumen (el rango típico es de -80.0 dB a 6.0 dB)
        float currentVolume = volumeControlMusicaFondo.getValue();
        float newVolume = currentVolume + 10.0f; // Disminuir el volumen en 10 dB
        if (newVolume > volumeControlMusicaFondo.getMaximum()) {
            newVolume = volumeControlMusicaFondo.getMaximum(); // Asegurarse de no subir más allá del máximo
            JOptionPane.showMessageDialog(this, "No se puede subir más el volúmen de la música.", "SONIDO", JOptionPane.INFORMATION_MESSAGE);
        }
        volumeControlMusicaFondo.setValue(newVolume);
     }
     /**
      * Permite bajar el volumen de la música
      */
     private void bajarVolumenMusica(){
        // Bajar el volumen (el rango típico es de -80.0 dB a 6.0 dB)
        float currentVolume = volumeControlMusicaFondo.getValue();
        float newVolume = currentVolume - 10.0f; // Disminuir el volumen en 10 dB
        if (newVolume < volumeControlMusicaFondo.getMinimum()) {
            newVolume = volumeControlMusicaFondo.getMinimum(); // Asegurarse de no bajar más allá del mínimo
            JOptionPane.showMessageDialog(this, "No se puede bajar más el volúmen de la música.", "SONIDO", JOptionPane.INFORMATION_MESSAGE);
        }
        volumeControlMusicaFondo.setValue(newVolume);
     }
     /**
      * Permite parar o continuar con la música
      */
     private void pararMusica(){
         if(musicaParada){
             clipMusicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
         }else{
             clipMusicaFondo.stop();
         }
         musicaParada=!musicaParada;
     }
     /**
      * Anuncia el ganador, poniendo un sonido para ello
      */
     private void anunciarGanador(){
        
        jLabel20.setIcon(new ImageIcon(getClass().getResource("img/pantallaCarga.png")));
        jDialogJuego.setVisible(false);
        jDialogPantallaCarga.setVisible(true);
        jDialogPantallaCarga.setLocationRelativeTo(null);
        jDialogComodinesJugador.setVisible(false);
        jDialogInfoComodines.setVisible(false);
        clipMusicaFondo.stop();
        PantallaCarga pantallaCarga = new PantallaCarga(clipAnunciarGanador, jDialogPantallaCarga, jDialogFinRonda);
        pantallaCarga.start();
        if(!musicaParada){
            clipMusicaFondo.loop(Clip.LOOP_CONTINUOUSLY);
        }
        
     }
     
     /**
      * COMENTAR
      */
     private void onWindowClosing(){
         try{
            jDialogJuego.setVisible(true);
            jButtonPedir.setEnabled(true);
            jButtonPasar.setEnabled(true);
            jButtonComodines.setEnabled(true);
            jButtonInfo.setEnabled(true);
            mesa.reestablecerMesa();
            
            if(maquina.getVida()==0 || jugador.getVida()==0){
                String ganador;
                String resultado;
                if(maquina.getVida()==0){
                    ganador = usuarioJugador;
                    jLabelFondoVictoria.setIcon(new ImageIcon(getClass().getResource("img/pantallaGanador.jpg")));
                    jDialogFinVictoria.setVisible(true);
                }else{
                    ganador = nombreMaquina;
                    jLabelFondoDerrota.setIcon(new ImageIcon(getClass().getResource("img/pantallaPerdedor.jpg")));
                    jDialogFinDerrota.setVisible(true);
                }

                jDialogJuego.setVisible(false);
                jDialogComodinesJugador.setVisible(false);
                jDialogInfoComodines.setVisible(false);
                botonJugar.setEnabled(false);
                try{
                    resultado = jugador.getVida()+"-" +maquina.getVida();
                    PreparedStatement ps = con.prepareStatement("UPDATE partida SET nombreGanador = ?, resultado= ? WHERE idPartida = ?");
                    ps.setString(1, ganador);
                    ps.setString(2, resultado);
                    ps.setInt(3, idPartida);
                    int filasAfectadas = ps.executeUpdate();
                    jugador = new Player();
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(jDialogJuego, "No te has podido rendir. Vuelve a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
         }catch(UltimoRepartoException e){
             JOptionPane.showMessageDialog(this, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
         }
    }
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogRegistrarPartida = new javax.swing.JDialog();
        textNombrePartida = new javax.swing.JTextArea();
        botonCrearPartida = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        principianteRadioButton = new javax.swing.JRadioButton();
        normalRadioButton = new javax.swing.JRadioButton();
        dificilRadioButton = new javax.swing.JRadioButton();
        loquilloRadioButton = new javax.swing.JRadioButton();
        jLabelFondoRegistrarPartida = new javax.swing.JLabel();
        jDialogInicioSesion = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        usuarioInicioSesion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        contrasenhaInicioSesion = new javax.swing.JTextField();
        inicioSesionButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabelFondoInicioSesion = new javax.swing.JLabel();
        jDialogCrearCuenta = new javax.swing.JDialog();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        usuarioNuevaCuenta = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        contrasenhaNuevaCuenta = new javax.swing.JTextField();
        botonCrearCuenta = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        crearNombre = new javax.swing.JTextField();
        jLabelFondoCrearCuenta = new javax.swing.JLabel();
        jDialogJuego = new javax.swing.JDialog();
        jButtonPedir = new javax.swing.JButton();
        jButtonPasar = new javax.swing.JButton();
        jLabelNombreMaquina = new javax.swing.JLabel();
        jLabelConteoMaquina = new javax.swing.JLabel();
        jLabelNombreJugador = new javax.swing.JLabel();
        jLabelConteoJugador = new javax.swing.JLabel();
        jButtonComodines = new javax.swing.JButton();
        cartaJugador1 = new javax.swing.JLabel();
        cartaJugador2 = new javax.swing.JLabel();
        cartaJugador3 = new javax.swing.JLabel();
        cartaJugador4 = new javax.swing.JLabel();
        cartaJugador5 = new javax.swing.JLabel();
        cartaJugador6 = new javax.swing.JLabel();
        cartaJugador7 = new javax.swing.JLabel();
        cartaJugador8 = new javax.swing.JLabel();
        cartaJugador9 = new javax.swing.JLabel();
        cartaMaquina1 = new javax.swing.JLabel();
        cartaMaquina2 = new javax.swing.JLabel();
        cartaMaquina3 = new javax.swing.JLabel();
        cartaMaquina4 = new javax.swing.JLabel();
        cartaMaquina5 = new javax.swing.JLabel();
        cartaMaquina6 = new javax.swing.JLabel();
        cartaMaquina7 = new javax.swing.JLabel();
        cartaMaquina8 = new javax.swing.JLabel();
        cartaMaquina9 = new javax.swing.JLabel();
        jLabelApuestaMaquina = new javax.swing.JLabel();
        jLabelApuestaJugador = new javax.swing.JLabel();
        comodinMaquina1 = new javax.swing.JLabel();
        comodinMaquina2 = new javax.swing.JLabel();
        comodinMaquina3 = new javax.swing.JLabel();
        comodinMaquina4 = new javax.swing.JLabel();
        comodinMaquina5 = new javax.swing.JLabel();
        comodinMaquina6 = new javax.swing.JLabel();
        comodinMaquina7 = new javax.swing.JLabel();
        comodinMaquina8 = new javax.swing.JLabel();
        comodinMaquina9 = new javax.swing.JLabel();
        comodinMaquina10 = new javax.swing.JLabel();
        comodinMaquina11 = new javax.swing.JLabel();
        comodinMesaJugador1 = new javax.swing.JLabel();
        comodinMesaJugador2 = new javax.swing.JLabel();
        comodinMesaJugador3 = new javax.swing.JLabel();
        comodinMesaJugador4 = new javax.swing.JLabel();
        comodinMesaJugador5 = new javax.swing.JLabel();
        comodinMesaJugador6 = new javax.swing.JLabel();
        comodinMesaJugador7 = new javax.swing.JLabel();
        comodinMesaJugador8 = new javax.swing.JLabel();
        comodinMesaJugador9 = new javax.swing.JLabel();
        comodinMesaJugador10 = new javax.swing.JLabel();
        comodinMesaJugador11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelValorCartaOculta = new javax.swing.JLabel();
        jLabelVidasMaquina = new javax.swing.JLabel();
        jLabelVidasJugador = new javax.swing.JLabel();
        jLabelObjetivoJugador = new javax.swing.JLabel();
        jLabelObjetivoMaquina = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButtonAbandonar = new javax.swing.JButton();
        jButtonInfo = new javax.swing.JButton();
        fondoJuego = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jDialogComodinesJugador = new javax.swing.JDialog();
        comodinJugador1 = new javax.swing.JLabel();
        comodinJugador2 = new javax.swing.JLabel();
        comodinJugador3 = new javax.swing.JLabel();
        comodinJugador4 = new javax.swing.JLabel();
        comodinJugador9 = new javax.swing.JLabel();
        comodinJugador10 = new javax.swing.JLabel();
        comodinJugador11 = new javax.swing.JLabel();
        comodinJugador12 = new javax.swing.JLabel();
        comodinJugador8 = new javax.swing.JLabel();
        comodinJugador5 = new javax.swing.JLabel();
        comodinJugador6 = new javax.swing.JLabel();
        comodinJugador7 = new javax.swing.JLabel();
        jLabelFondoComodinesJugador = new javax.swing.JLabel();
        jDialogFinDerrota = new javax.swing.JDialog();
        jLabelFondoDerrota = new javax.swing.JLabel();
        jDialogFinVictoria = new javax.swing.JDialog();
        jLabelFondoVictoria = new javax.swing.JLabel();
        jDialogInfoComodines = new javax.swing.JDialog();
        imagenComodin1 = new javax.swing.JLabel();
        imagenComodin2 = new javax.swing.JLabel();
        imagenComodin3 = new javax.swing.JLabel();
        imagenComodin4 = new javax.swing.JLabel();
        imagenComodin11 = new javax.swing.JLabel();
        imagenComodin12 = new javax.swing.JLabel();
        imagenComodin13 = new javax.swing.JLabel();
        imagenComodin14 = new javax.swing.JLabel();
        imagenComodin6 = new javax.swing.JLabel();
        imagenComodin7 = new javax.swing.JLabel();
        imagenComodin8 = new javax.swing.JLabel();
        imagenComodin9 = new javax.swing.JLabel();
        imagenComodin10 = new javax.swing.JLabel();
        imagenComodin15 = new javax.swing.JLabel();
        imagenComodin5 = new javax.swing.JLabel();
        detalleComodin1 = new javax.swing.JLabel();
        detalleComodin2 = new javax.swing.JLabel();
        detalleComodin3 = new javax.swing.JLabel();
        detalleComodin4 = new javax.swing.JLabel();
        detalleComodin5 = new javax.swing.JLabel();
        detalleComodin6 = new javax.swing.JLabel();
        detalleComodin7 = new javax.swing.JLabel();
        detalleComodin8 = new javax.swing.JLabel();
        detalleComodin9 = new javax.swing.JLabel();
        detalleComodin10 = new javax.swing.JLabel();
        detalleComodin11 = new javax.swing.JLabel();
        detalleComodin12 = new javax.swing.JLabel();
        detalleComodin13 = new javax.swing.JLabel();
        detalleComodin14 = new javax.swing.JLabel();
        detalleComodin15 = new javax.swing.JLabel();
        jLabelFondoInfoComodines = new javax.swing.JLabel();
        jDialogFinRonda = new javax.swing.JDialog();
        jLabel21 = new javax.swing.JLabel();
        jLabelPuntosJugadorFinRonda = new javax.swing.JLabel();
        jLabelPuntosMaquinaFinRonda = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabelNombreJugadorFinRonda = new javax.swing.JLabel();
        jLabelCartelGanadorFinRonda = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jDialogPantallaCarga = new javax.swing.JDialog();
        jLabel20 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButtonSubirVolumenMenu = new javax.swing.JButton();
        jButtonBajarVolumenMenu = new javax.swing.JButton();
        jButtonBajarVolumenMenu1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        inicioSesion = new javax.swing.JButton();
        botonHistorial = new javax.swing.JButton();
        botonJugar = new javax.swing.JButton();
        botonRegistrarPartida = new javax.swing.JButton();
        fondoMenuPrincipal = new javax.swing.JLabel();

        jDialogRegistrarPartida.setTitle("Registrar partida");
        jDialogRegistrarPartida.setBounds(new java.awt.Rectangle(360, 287, 360, 287));
        jDialogRegistrarPartida.setIconImage(null);
        jDialogRegistrarPartida.setResizable(false);
        jDialogRegistrarPartida.setSize(new java.awt.Dimension(375, 320));
        jDialogRegistrarPartida.getContentPane().setLayout(null);

        textNombrePartida.setColumns(20);
        textNombrePartida.setRows(5);
        jDialogRegistrarPartida.getContentPane().add(textNombrePartida);
        textNombrePartida.setBounds(149, 77, 207, 25);

        botonCrearPartida.setText("Crear partida");
        botonCrearPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrearPartidaActionPerformed(evt);
            }
        });
        jDialogRegistrarPartida.getContentPane().add(botonCrearPartida);
        botonCrearPartida.setBounds(110, 240, 120, 23);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre de la partida:");
        jDialogRegistrarPartida.getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 80, 140, 16);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Registrar partida");
        jDialogRegistrarPartida.getContentPane().add(jLabel5);
        jLabel5.setBounds(81, 6, 193, 32);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Dificultad:");
        jDialogRegistrarPartida.getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 120, 54, 16);

        buttonGroup1.add(principianteRadioButton);
        principianteRadioButton.setForeground(new java.awt.Color(255, 255, 255));
        principianteRadioButton.setText("Principiante");
        principianteRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                principianteRadioButtonActionPerformed(evt);
            }
        });
        jDialogRegistrarPartida.getContentPane().add(principianteRadioButton);
        principianteRadioButton.setBounds(20, 150, 110, 21);

        buttonGroup1.add(normalRadioButton);
        normalRadioButton.setForeground(new java.awt.Color(255, 255, 255));
        normalRadioButton.setSelected(true);
        normalRadioButton.setText("Normal");
        jDialogRegistrarPartida.getContentPane().add(normalRadioButton);
        normalRadioButton.setBounds(130, 150, 63, 21);

        buttonGroup1.add(dificilRadioButton);
        dificilRadioButton.setForeground(new java.awt.Color(255, 255, 255));
        dificilRadioButton.setText("Difícil");
        jDialogRegistrarPartida.getContentPane().add(dificilRadioButton);
        dificilRadioButton.setBounds(20, 180, 53, 21);

        buttonGroup1.add(loquilloRadioButton);
        loquilloRadioButton.setForeground(new java.awt.Color(255, 255, 255));
        loquilloRadioButton.setText("Loquillo");
        jDialogRegistrarPartida.getContentPane().add(loquilloRadioButton);
        loquilloRadioButton.setBounds(130, 180, 66, 21);

        jLabelFondoRegistrarPartida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoMenuOperaciones.jpg"))); // NOI18N
        jLabelFondoRegistrarPartida.setText("jLabel15");
        jDialogRegistrarPartida.getContentPane().add(jLabelFondoRegistrarPartida);
        jLabelFondoRegistrarPartida.setBounds(0, 0, 360, 290);

        jDialogInicioSesion.setTitle("Iniciar sesión");
        jDialogInicioSesion.setBounds(new java.awt.Rectangle(500, 420, 500, 420));
        jDialogInicioSesion.setResizable(false);
        jDialogInicioSesion.getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("INICIO SESIÓN");
        jDialogInicioSesion.getContentPane().add(jLabel2);
        jLabel2.setBounds(160, 40, 167, 32);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Usuario:");
        jDialogInicioSesion.getContentPane().add(jLabel4);
        jLabel4.setBounds(102, 125, 64, 25);

        usuarioInicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioInicioSesionActionPerformed(evt);
            }
        });
        jDialogInicioSesion.getContentPane().add(usuarioInicioSesion);
        usuarioInicioSesion.setBounds(190, 130, 198, 22);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Contraseña:");
        jDialogInicioSesion.getContentPane().add(jLabel8);
        jLabel8.setBounds(72, 187, 94, 25);

        contrasenhaInicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenhaInicioSesionActionPerformed(evt);
            }
        });
        jDialogInicioSesion.getContentPane().add(contrasenhaInicioSesion);
        contrasenhaInicioSesion.setBounds(190, 190, 198, 22);

        inicioSesionButton.setText("Iniciar Sesion");
        inicioSesionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inicioSesionButtonActionPerformed(evt);
            }
        });
        jDialogInicioSesion.getContentPane().add(inicioSesionButton);
        inicioSesionButton.setBounds(180, 250, 120, 23);

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("¿No tienes cuenta? Registrate");
        jDialogInicioSesion.getContentPane().add(jLabel11);
        jLabel11.setBounds(150, 320, 190, 16);

        jButton3.setText("Registrarse");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jDialogInicioSesion.getContentPane().add(jButton3);
        jButton3.setBounds(180, 340, 110, 23);

        jLabelFondoInicioSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoMenuOperacionesCompleto.jpg"))); // NOI18N
        jDialogInicioSesion.getContentPane().add(jLabelFondoInicioSesion);
        jLabelFondoInicioSesion.setBounds(0, -10, 500, 400);

        jDialogCrearCuenta.setTitle("Crear cuenta");
        jDialogCrearCuenta.setBounds(new java.awt.Rectangle(400, 320, 400, 320));
        jDialogCrearCuenta.setResizable(false);
        jDialogCrearCuenta.getContentPane().setLayout(null);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Crear Cuenta");
        jDialogCrearCuenta.getContentPane().add(jLabel9);
        jLabel9.setBounds(123, 6, 148, 32);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Usuario:");
        jDialogCrearCuenta.getContentPane().add(jLabel12);
        jLabel12.setBounds(92, 116, 64, 25);

        usuarioNuevaCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioNuevaCuentaActionPerformed(evt);
            }
        });
        jDialogCrearCuenta.getContentPane().add(usuarioNuevaCuenta);
        usuarioNuevaCuenta.setBounds(174, 120, 166, 22);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Contraseña:");
        jDialogCrearCuenta.getContentPane().add(jLabel10);
        jLabel10.setBounds(62, 166, 94, 25);

        contrasenhaNuevaCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenhaNuevaCuentaActionPerformed(evt);
            }
        });
        jDialogCrearCuenta.getContentPane().add(contrasenhaNuevaCuenta);
        contrasenhaNuevaCuenta.setBounds(174, 170, 166, 22);

        botonCrearCuenta.setText("Crear cuenta");
        botonCrearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrearCuentaActionPerformed(evt);
            }
        });
        jDialogCrearCuenta.getContentPane().add(botonCrearCuenta);
        botonCrearCuenta.setBounds(140, 220, 120, 23);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nombre:");
        jDialogCrearCuenta.getContentPane().add(jLabel13);
        jLabel13.setBounds(88, 72, 68, 25);
        jDialogCrearCuenta.getContentPane().add(crearNombre);
        crearNombre.setBounds(174, 76, 166, 22);

        jLabelFondoCrearCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoMenuOperaciones.jpg"))); // NOI18N
        jDialogCrearCuenta.getContentPane().add(jLabelFondoCrearCuenta);
        jLabelFondoCrearCuenta.setBounds(0, 0, 400, 300);

        jDialogJuego.setTitle("El último reparto");
        jDialogJuego.setBounds(new java.awt.Rectangle(1495, 900, 1495, 900));
        jDialogJuego.setResizable(false);
        jDialogJuego.getContentPane().setLayout(null);

        jButtonPedir.setText("PEDIR");
        jButtonPedir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPedirActionPerformed(evt);
            }
        });
        jDialogJuego.getContentPane().add(jButtonPedir);
        jButtonPedir.setBounds(690, 800, 72, 23);

        jButtonPasar.setText("PASAR");
        jButtonPasar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPasarActionPerformed(evt);
            }
        });
        jDialogJuego.getContentPane().add(jButtonPasar);
        jButtonPasar.setBounds(600, 800, 72, 23);

        jLabelNombreMaquina.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombreMaquina.setText("NOMBRE MAQUINA");
        jDialogJuego.getContentPane().add(jLabelNombreMaquina);
        jLabelNombreMaquina.setBounds(69, 7, 280, 16);

        jLabelConteoMaquina.setForeground(new java.awt.Color(255, 255, 255));
        jLabelConteoMaquina.setText("0");
        jDialogJuego.getContentPane().add(jLabelConteoMaquina);
        jLabelConteoMaquina.setBounds(1370, 10, 20, 16);

        jLabelNombreJugador.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombreJugador.setText("NOMBRE JUGADOR");
        jDialogJuego.getContentPane().add(jLabelNombreJugador);
        jLabelNombreJugador.setBounds(70, 800, 360, 16);

        jLabelConteoJugador.setForeground(new java.awt.Color(255, 255, 255));
        jLabelConteoJugador.setText("0");
        jDialogJuego.getContentPane().add(jLabelConteoJugador);
        jLabelConteoJugador.setBounds(1384, 789, 20, 16);

        jButtonComodines.setText("MIS COMODINES");
        jButtonComodines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComodinesActionPerformed(evt);
            }
        });
        jDialogJuego.getContentPane().add(jButtonComodines);
        jButtonComodines.setBounds(780, 800, 150, 23);

        cartaJugador1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta1.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador1);
        cartaJugador1.setBounds(69, 536, 140, 239);

        cartaJugador2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador2);
        cartaJugador2.setBounds(221, 536, 140, 239);

        cartaJugador3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta11.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador3);
        cartaJugador3.setBounds(373, 536, 145, 240);

        cartaJugador4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador4);
        cartaJugador4.setBounds(530, 536, 140, 239);

        cartaJugador5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador5);
        cartaJugador5.setBounds(682, 536, 140, 239);

        cartaJugador6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador6);
        cartaJugador6.setBounds(834, 536, 140, 239);

        cartaJugador7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador7);
        cartaJugador7.setBounds(986, 536, 140, 239);

        cartaJugador8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador8);
        cartaJugador8.setBounds(1142, 536, 140, 239);

        cartaJugador9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaJugador9);
        cartaJugador9.setBounds(1294, 536, 140, 239);

        cartaMaquina1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina1);
        cartaMaquina1.setBounds(69, 35, 140, 239);

        cartaMaquina2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina2);
        cartaMaquina2.setBounds(221, 35, 140, 239);

        cartaMaquina3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina3);
        cartaMaquina3.setBounds(373, 35, 140, 239);

        cartaMaquina4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina4);
        cartaMaquina4.setBounds(525, 35, 140, 239);

        cartaMaquina5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina5);
        cartaMaquina5.setBounds(677, 35, 140, 239);

        cartaMaquina6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina6);
        cartaMaquina6.setBounds(829, 35, 140, 239);

        cartaMaquina7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina7);
        cartaMaquina7.setBounds(981, 35, 140, 239);

        cartaMaquina8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina8);
        cartaMaquina8.setBounds(1137, 35, 140, 239);

        cartaMaquina9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cartas/carta4.png"))); // NOI18N
        jDialogJuego.getContentPane().add(cartaMaquina9);
        cartaMaquina9.setBounds(1289, 35, 140, 239);

        jLabelApuestaMaquina.setForeground(new java.awt.Color(255, 255, 255));
        jLabelApuestaMaquina.setText("1");
        jDialogJuego.getContentPane().add(jLabelApuestaMaquina);
        jLabelApuestaMaquina.setBounds(20, 340, 20, 16);

        jLabelApuestaJugador.setForeground(new java.awt.Color(255, 255, 255));
        jLabelApuestaJugador.setText("1");
        jDialogJuego.getContentPane().add(jLabelApuestaJugador);
        jLabelApuestaJugador.setBounds(20, 460, 20, 16);

        comodinMaquina1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina1);
        comodinMaquina1.setBounds(69, 286, 111, 110);

        comodinMaquina2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina2);
        comodinMaquina2.setBounds(198, 286, 111, 110);

        comodinMaquina3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina3);
        comodinMaquina3.setBounds(321, 286, 111, 110);

        comodinMaquina4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina4);
        comodinMaquina4.setBounds(444, 286, 111, 110);

        comodinMaquina5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina5);
        comodinMaquina5.setBounds(567, 286, 111, 110);

        comodinMaquina6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina6);
        comodinMaquina6.setBounds(690, 286, 111, 110);

        comodinMaquina7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina7);
        comodinMaquina7.setBounds(813, 286, 111, 110);

        comodinMaquina8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina8);
        comodinMaquina8.setBounds(930, 286, 111, 110);

        comodinMaquina9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina9);
        comodinMaquina9.setBounds(1047, 286, 111, 110);

        comodinMaquina10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina10);
        comodinMaquina10.setBounds(1170, 286, 111, 110);

        comodinMaquina11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMaquina11);
        comodinMaquina11.setBounds(1293, 286, 111, 110);

        comodinMesaJugador1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador1);
        comodinMesaJugador1.setBounds(69, 414, 111, 110);

        comodinMesaJugador2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador2);
        comodinMesaJugador2.setBounds(198, 414, 111, 110);

        comodinMesaJugador3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador3);
        comodinMesaJugador3.setBounds(321, 414, 111, 110);

        comodinMesaJugador4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador4);
        comodinMesaJugador4.setBounds(444, 414, 111, 110);

        comodinMesaJugador5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador5);
        comodinMesaJugador5.setBounds(567, 414, 111, 110);

        comodinMesaJugador6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador6);
        comodinMesaJugador6.setBounds(690, 414, 111, 110);

        comodinMesaJugador7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador7);
        comodinMesaJugador7.setBounds(813, 414, 111, 110);

        comodinMesaJugador8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador8);
        comodinMesaJugador8.setBounds(930, 414, 111, 110);

        comodinMesaJugador9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador9);
        comodinMesaJugador9.setBounds(1047, 414, 111, 110);

        comodinMesaJugador10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador10);
        comodinMesaJugador10.setBounds(1170, 414, 111, 110);

        comodinMesaJugador11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(comodinMesaJugador11);
        comodinMesaJugador11.setBounds(1293, 414, 111, 110);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("/");
        jDialogJuego.getContentPane().add(jLabel7);
        jLabel7.setBounds(1390, 10, 10, 16);

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("/");
        jDialogJuego.getContentPane().add(jLabel14);
        jLabel14.setBounds(1405, 789, 10, 16);

        jLabelValorCartaOculta.setForeground(new java.awt.Color(255, 255, 255));
        jLabelValorCartaOculta.setText("jLabel15");
        jDialogJuego.getContentPane().add(jLabelValorCartaOculta);
        jLabelValorCartaOculta.setBounds(120, 780, 43, 16);

        jLabelVidasMaquina.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVidasMaquina.setText("10");
        jDialogJuego.getContentPane().add(jLabelVidasMaquina);
        jLabelVidasMaquina.setBounds(50, 340, 30, 16);

        jLabelVidasJugador.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVidasJugador.setText("10");
        jDialogJuego.getContentPane().add(jLabelVidasJugador);
        jLabelVidasJugador.setBounds(50, 460, 30, 16);

        jLabelObjetivoJugador.setForeground(new java.awt.Color(255, 255, 255));
        jLabelObjetivoJugador.setText("21");
        jDialogJuego.getContentPane().add(jLabelObjetivoJugador);
        jLabelObjetivoJugador.setBounds(1413, 789, 20, 16);

        jLabelObjetivoMaquina.setForeground(new java.awt.Color(255, 255, 255));
        jLabelObjetivoMaquina.setText("21");
        jDialogJuego.getContentPane().add(jLabelObjetivoMaquina);
        jLabelObjetivoMaquina.setBounds(1400, 10, 20, 16);

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("? +");
        jDialogJuego.getContentPane().add(jLabel17);
        jLabel17.setBounds(1350, 10, 30, 16);

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("/");
        jDialogJuego.getContentPane().add(jLabel18);
        jLabel18.setBounds(40, 340, 5, 16);

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("/");
        jDialogJuego.getContentPane().add(jLabel19);
        jLabel19.setBounds(40, 460, 10, 16);

        jButtonAbandonar.setText("ABANDONAR");
        jButtonAbandonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbandonarActionPerformed(evt);
            }
        });
        jDialogJuego.getContentPane().add(jButtonAbandonar);
        jButtonAbandonar.setBounds(480, 800, 103, 23);

        jButtonInfo.setText("INFO");
        jButtonInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInfoActionPerformed(evt);
            }
        });
        jDialogJuego.getContentPane().add(jButtonInfo);
        jButtonInfo.setBounds(940, 800, 72, 23);

        fondoJuego.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoJuego.jpg"))); // NOI18N
        jDialogJuego.getContentPane().add(fondoJuego);
        fondoJuego.setBounds(0, 0, 1480, 870);

        jDialogComodinesJugador.setTitle("Mis comodines");
        jDialogComodinesJugador.setBounds(new java.awt.Rectangle(520, 437, 520, 437));
        jDialogComodinesJugador.setResizable(false);
        jDialogComodinesJugador.getContentPane().setLayout(null);

        comodinJugador1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador1);
        comodinJugador1.setBounds(6, 14, 111, 110);

        comodinJugador2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador2);
        comodinJugador2.setBounds(135, 14, 111, 110);

        comodinJugador3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador3);
        comodinJugador3.setBounds(258, 14, 111, 110);

        comodinJugador4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador4);
        comodinJugador4.setBounds(381, 14, 111, 110);

        comodinJugador9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador9);
        comodinJugador9.setBounds(6, 280, 111, 110);

        comodinJugador10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador10);
        comodinJugador10.setBounds(135, 280, 111, 110);

        comodinJugador11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador11);
        comodinJugador11.setBounds(258, 280, 111, 110);

        comodinJugador12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador12);
        comodinJugador12.setBounds(381, 280, 111, 110);

        comodinJugador8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador8);
        comodinJugador8.setBounds(381, 148, 111, 110);

        comodinJugador5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador5);
        comodinJugador5.setBounds(6, 148, 111, 110);

        comodinJugador6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador6);
        comodinJugador6.setBounds(135, 148, 111, 110);

        comodinJugador7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(comodinJugador7);
        comodinJugador7.setBounds(258, 148, 111, 110);

        jLabelFondoComodinesJugador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo_mesa_apuestas.jpg"))); // NOI18N
        jDialogComodinesJugador.getContentPane().add(jLabelFondoComodinesJugador);
        jLabelFondoComodinesJugador.setBounds(-90, 0, 610, 400);

        jDialogFinDerrota.setTitle("DERROTA");
        jDialogFinDerrota.setBounds(new java.awt.Rectangle(508, 400, 508, 400));
        jDialogFinDerrota.setResizable(false);
        jDialogFinDerrota.setSize(new java.awt.Dimension(508, 400));
        jDialogFinDerrota.getContentPane().setLayout(null);

        jLabelFondoDerrota.setText("jLabel15");
        jDialogFinDerrota.getContentPane().add(jLabelFondoDerrota);
        jLabelFondoDerrota.setBounds(0, 0, 500, 360);

        jDialogFinVictoria.setTitle("VICTORIA");
        jDialogFinVictoria.setBounds(new java.awt.Rectangle(600, 400, 600, 400));
        jDialogFinVictoria.setResizable(false);
        jDialogFinVictoria.setSize(new java.awt.Dimension(600, 400));
        jDialogFinVictoria.getContentPane().setLayout(null);
        jDialogFinVictoria.getContentPane().add(jLabelFondoVictoria);
        jLabelFondoVictoria.setBounds(0, 0, 610, 360);

        jDialogInfoComodines.setTitle("Comodines");
        jDialogInfoComodines.setBounds(new java.awt.Rectangle(675, 510, 675, 510));
        jDialogInfoComodines.setResizable(false);
        jDialogInfoComodines.getContentPane().setLayout(null);

        imagenComodin1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin1);
        imagenComodin1.setBounds(10, 10, 111, 110);

        imagenComodin2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin2);
        imagenComodin2.setBounds(140, 10, 111, 110);

        imagenComodin3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin3);
        imagenComodin3.setBounds(270, 10, 111, 110);

        imagenComodin4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin4);
        imagenComodin4.setBounds(400, 10, 111, 110);

        imagenComodin11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin11);
        imagenComodin11.setBounds(10, 330, 111, 110);

        imagenComodin12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin12);
        imagenComodin12.setBounds(140, 330, 111, 110);

        imagenComodin13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin13);
        imagenComodin13.setBounds(270, 330, 111, 110);

        imagenComodin14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin14);
        imagenComodin14.setBounds(400, 330, 111, 110);

        imagenComodin6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin6);
        imagenComodin6.setBounds(10, 170, 111, 110);

        imagenComodin7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin7);
        imagenComodin7.setBounds(140, 170, 111, 110);

        imagenComodin8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin8);
        imagenComodin8.setBounds(270, 170, 111, 110);

        imagenComodin9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin9);
        imagenComodin9.setBounds(400, 170, 111, 110);

        imagenComodin10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin10);
        imagenComodin10.setBounds(530, 170, 111, 110);

        imagenComodin15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin15);
        imagenComodin15.setBounds(530, 330, 111, 110);

        imagenComodin5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/comodines/destruccion.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(imagenComodin5);
        imagenComodin5.setBounds(530, 10, 111, 110);

        detalleComodin1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin1.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin1.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin1);
        detalleComodin1.setBounds(10, 130, 111, 14);

        detalleComodin2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin2.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin2.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin2);
        detalleComodin2.setBounds(140, 130, 111, 14);

        detalleComodin3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin3.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin3.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin3);
        detalleComodin3.setBounds(270, 130, 111, 14);

        detalleComodin4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin4.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin4.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin4);
        detalleComodin4.setBounds(400, 130, 111, 14);

        detalleComodin5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin5.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin5.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin5);
        detalleComodin5.setBounds(530, 130, 111, 14);

        detalleComodin6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin6.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin6.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin6);
        detalleComodin6.setBounds(10, 290, 105, 14);

        detalleComodin7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin7.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin7.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin7);
        detalleComodin7.setBounds(140, 290, 111, 14);

        detalleComodin8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin8.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin8.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin8);
        detalleComodin8.setBounds(270, 290, 111, 14);

        detalleComodin9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin9.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin9.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin9);
        detalleComodin9.setBounds(400, 290, 111, 14);

        detalleComodin10.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin10.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin10.setText("jLabel15");
        jDialogInfoComodines.getContentPane().add(detalleComodin10);
        detalleComodin10.setBounds(530, 290, 111, 14);

        detalleComodin11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin11.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin11.setText("jLabel28");
        jDialogInfoComodines.getContentPane().add(detalleComodin11);
        detalleComodin11.setBounds(10, 450, 111, 14);

        detalleComodin12.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin12.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin12.setText("jLabel28");
        jDialogInfoComodines.getContentPane().add(detalleComodin12);
        detalleComodin12.setBounds(140, 450, 111, 14);

        detalleComodin13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin13.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin13.setText("jLabel28");
        jDialogInfoComodines.getContentPane().add(detalleComodin13);
        detalleComodin13.setBounds(270, 450, 111, 14);

        detalleComodin14.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin14.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin14.setText("jLabel28");
        jDialogInfoComodines.getContentPane().add(detalleComodin14);
        detalleComodin14.setBounds(400, 450, 111, 14);

        detalleComodin15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        detalleComodin15.setForeground(new java.awt.Color(255, 255, 255));
        detalleComodin15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detalleComodin15.setText("jLabel28");
        jDialogInfoComodines.getContentPane().add(detalleComodin15);
        detalleComodin15.setBounds(530, 450, 111, 14);

        jLabelFondoInfoComodines.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFondoInfoComodines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoJuego.jpg"))); // NOI18N
        jDialogInfoComodines.getContentPane().add(jLabelFondoInfoComodines);
        jLabelFondoInfoComodines.setBounds(0, 0, 680, 490);

        jDialogFinRonda.setTitle("FIN DE LA RONDA");
        jDialogFinRonda.setBounds(new java.awt.Rectangle(415, 337, 415, 337));
        jDialogFinRonda.getContentPane().setLayout(null);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N
        jLabel21.setText("/");
        jDialogFinRonda.getContentPane().add(jLabel21);
        jLabel21.setBounds(160, 50, 50, 150);

        jLabelPuntosJugadorFinRonda.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabelPuntosJugadorFinRonda.setText("21");
        jDialogFinRonda.getContentPane().add(jLabelPuntosJugadorFinRonda);
        jLabelPuntosJugadorFinRonda.setBounds(120, 80, 60, 60);

        jLabelPuntosMaquinaFinRonda.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabelPuntosMaquinaFinRonda.setText("21");
        jDialogFinRonda.getContentPane().add(jLabelPuntosMaquinaFinRonda);
        jLabelPuntosMaquinaFinRonda.setBounds(190, 130, 60, 60);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("Máquina");
        jDialogFinRonda.getContentPane().add(jLabel24);
        jLabel24.setBounds(200, 200, 70, 30);

        jLabelNombreJugadorFinRonda.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabelNombreJugadorFinRonda.setText("Rafa");
        jDialogFinRonda.getContentPane().add(jLabelNombreJugadorFinRonda);
        jLabelNombreJugadorFinRonda.setBounds(70, 50, 210, 30);

        jLabelCartelGanadorFinRonda.setBackground(new java.awt.Color(255, 255, 255));
        jLabelCartelGanadorFinRonda.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelCartelGanadorFinRonda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCartelGanadorFinRonda.setText("Rafa ha ganado");
        jLabelCartelGanadorFinRonda.setOpaque(true);
        jDialogFinRonda.getContentPane().add(jLabelCartelGanadorFinRonda);
        jLabelCartelGanadorFinRonda.setBounds(80, 260, 230, 20);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondoFinRonda.jpg"))); // NOI18N
        jDialogFinRonda.getContentPane().add(jLabel16);
        jLabel16.setBounds(-240, 0, 1180, 320);

        jDialogPantallaCarga.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialogPantallaCarga.setTitle("Y EL GANADOR ES...");
        jDialogPantallaCarga.setBounds(new java.awt.Rectangle(1495, 900, 1495, 900));
        jDialogPantallaCarga.setResizable(false);
        jDialogPantallaCarga.getContentPane().setLayout(null);

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pantallaCarga.png"))); // NOI18N
        jDialogPantallaCarga.getContentPane().add(jLabel20);
        jLabel20.setBounds(-680, 0, 2200, 1440);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("El último reparto");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Volumen");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, -1, -1));

        jButtonSubirVolumenMenu.setText("+");
        jButtonSubirVolumenMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSubirVolumenMenuActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSubirVolumenMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 370, -1, -1));

        jButtonBajarVolumenMenu.setText("-");
        jButtonBajarVolumenMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBajarVolumenMenuActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonBajarVolumenMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, -1, -1));

        jButtonBajarVolumenMenu1.setText("⏯");
        jButtonBajarVolumenMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBajarVolumenMenu1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonBajarVolumenMenu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, 40, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setText("EL ÚLTIMO REPARTO");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, -1, -1));

        inicioSesion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        inicioSesion.setText("Iniciar Sesión");
        inicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inicioSesionActionPerformed(evt);
            }
        });
        getContentPane().add(inicioSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 200, 40));

        botonHistorial.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        botonHistorial.setText("Historial");
        botonHistorial.setEnabled(false);
        botonHistorial.setMaximumSize(new java.awt.Dimension(204, 39));
        botonHistorial.setMinimumSize(new java.awt.Dimension(204, 39));
        botonHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonHistorialActionPerformed(evt);
            }
        });
        getContentPane().add(botonHistorial, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 200, 40));

        botonJugar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        botonJugar.setText("Jugar");
        botonJugar.setEnabled(false);
        botonJugar.setMaximumSize(new java.awt.Dimension(204, 39));
        botonJugar.setMinimumSize(new java.awt.Dimension(204, 39));
        botonJugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonJugarActionPerformed(evt);
            }
        });
        getContentPane().add(botonJugar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 200, 40));

        botonRegistrarPartida.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        botonRegistrarPartida.setText("Registrar partida");
        botonRegistrarPartida.setEnabled(false);
        botonRegistrarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarPartidaActionPerformed(evt);
            }
        });
        getContentPane().add(botonRegistrarPartida, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 200, 40));

        fondoMenuPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo_mesa_apuestas.jpg"))); // NOI18N
        fondoMenuPrincipal.setText("jLabel7");
        getContentPane().add(fondoMenuPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, 0, 540, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Cuando le da al botón de iniciar sesión, se abre la ventana de inicio de sesión
     * @param evt 
     */
    private void inicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inicioSesionActionPerformed
        jDialogInicioSesion.setVisible(true);
    }//GEN-LAST:event_inicioSesionActionPerformed
    /**
     * Cuando le da al botón de historial, se muestra una tabla en una ventana diferente
     * @param evt 
     */
    private void botonHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonHistorialActionPerformed
        try{
            PreparedStatement ps = con.prepareStatement("SELECT fecha, NVL(nombreGanador, 'Maquina') as nombreGanador, NVL(resultado, 'ABANDONO') as resultado FROM partida WHERE idJugador = ?");
            ps.setInt(1, idJugador);
            List<Partida> listaPartidas = new ArrayList<Partida>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                listaPartidas.add(new Partida(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
            modeloPartida = new PartidaTableModel(listaPartidas);
            // Crear el JTable con el modelo
            JTable tabla = new JTable(modeloPartida);

            // Configurar la tabla
            tabla.setFillsViewportHeight(true); // Ajustar al tamaño del panel
            tabla.setRowHeight(25); // Altura de las filas

            // Crear un JScrollPane para la tabla de los contactos
            JScrollPane scrollPane = new JScrollPane(tabla);

            // Crear el JFrame
            JFrame ventana = new JFrame("Historial de partidas");
            ventana.setSize(1000, 400);
            ventana.setLayout(null);
            ventana.setResizable(false);
            JLabel titulo = new JLabel("Historial de partidas");
            titulo.setSize(18, 18);
            titulo.setBounds(500, -80, 200, 200);

            // Añadir los componentes a la ventana
            scrollPane.setBounds(100, 50, 800, 200);
            ventana.add(titulo);
            ventana.add(scrollPane);
            ventana.getContentPane().setBackground(Color.decode("#cedaf4"));
            
            // Mostrar la ventana
            ventana.setVisible(true);
            
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "No se ha podido mostrar el historial, intentelo más tarde.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_botonHistorialActionPerformed
    /**
     * Cuando le da al botón de registrar partida, se muestra el jDialog del historial
     * @param evt 
     */
    private void botonRegistrarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarPartidaActionPerformed
                this.jDialogRegistrarPartida.setVisible(true);
    }//GEN-LAST:event_botonRegistrarPartidaActionPerformed
    /**
     * Cuando le da a crear partida, dependiendo de la dificultad, se obtiene el nombre de la máquina y se inserta la partida en la base de datos
     * @param evt 
     */
    private void botonCrearPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrearPartidaActionPerformed
        Dificultad dificultad;
        try{
            if(textNombrePartida.getText().isEmpty()){
                throw new UltimoRepartoException("Debes rellenar el nombre de la partida.");
            }
            if(principianteRadioButton.isSelected()){
                dificultad = Dificultad.PRINCIPIANTE;
            }else{
                if(normalRadioButton.isSelected()){
                    dificultad = Dificultad.NORMAL;
                }else{
                    if(dificilRadioButton.isSelected()){
                        dificultad = Dificultad.DIFICIL;
                    }else{
                        dificultad = Dificultad.LOQUILLO;
                    }
                }
            }
            maquina=new Maquina(dificultad, jugador);
            PreparedStatement ps2 = con.prepareStatement("SELECT idMaquina, nombre FROM maquina WHERE dificultad = ?");
            ps2.setString(1, maquina.getDificultad());
            ResultSet rs =ps2.executeQuery();
            int idMaquina=2;
            if(rs.next()){
                idMaquina = rs.getInt(1);
                nombreMaquina=rs.getString(2);
            }
            nombreMaquina=nombreMaquina.replace("_", " ");
            PreparedStatement ps = con.prepareStatement("INSERT INTO partida (nombre, fecha, nombreGanador, resultado, idMaquina, idJugador) VALUES (?, CURDATE(), NULL, NULL, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, textNombrePartida.getText());
            ps.setInt(2, idMaquina);
            ps.setInt(3, idJugador);
            ps.executeUpdate();
            ResultSet rs2=ps.getGeneratedKeys();
            rs2.next();
            idPartida=rs2.getInt(1);
            mesa=new Mesa(jDialogPantallaCarga, jugador, maquina, cartasJugador, cartasMaquina, comodinesJugador, comodinesMaquina, jLabelConteoMaquina, jLabelConteoJugador, jLabelValorCartaOculta, jLabelApuestaJugador, jLabelApuestaMaquina, jLabelObjetivoJugador, jLabelObjetivoMaquina, comodinesJugadorMisComodines, jDialogJuego,con);
            jLabelNombreJugador.setText(usuarioJugador);
            jLabelNombreMaquina.setText(nombreMaquina);
            botonJugar.setEnabled(true);
            jDialogRegistrarPartida.setVisible(false);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(jDialogRegistrarPartida, "Ha ocurrido un error al registrar los datos de la partida.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }catch(UltimoRepartoException e){
            JOptionPane.showMessageDialog(jDialogRegistrarPartida, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        textNombrePartida.setText("");
    }//GEN-LAST:event_botonCrearPartidaActionPerformed

    private void principianteRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_principianteRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_principianteRadioButtonActionPerformed

    private void usuarioInicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioInicioSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioInicioSesionActionPerformed

    private void contrasenhaInicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenhaInicioSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contrasenhaInicioSesionActionPerformed
    /**
     * Cuando se inicia sesión, se comprueba que excista el usuario y se guarda su nombre y nombre de usuario
     * @param evt 
     */
    private void inicioSesionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inicioSesionButtonActionPerformed
        if(usuarioInicioSesion.getText().isEmpty() || contrasenhaInicioSesion.getText().isEmpty()){
           JOptionPane.showMessageDialog(jDialogInicioSesion, "No has rellenado todos los campos para poder iniciar sesión.", "ERROR", JOptionPane.ERROR_MESSAGE);
       }else{
           try{
               PreparedStatement ps = con.prepareStatement("SELECT * FROM jugador WHERE usuario = ? AND contrasenha = ?");
               ps.setString(1, usuarioInicioSesion.getText());
               ps.setString(2, contrasenhaInicioSesion.getText());
               ResultSet rs = ps.executeQuery();
               if(rs.next()){
                   idJugador = rs.getInt(1);
                   jugador=new Player();
                   JOptionPane.showMessageDialog(jDialogInicioSesion, "Hola " + rs.getString(2) + ", has iniciado sesión correctamente.", "Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
                   jDialogInicioSesion.setVisible(false);
                   botonRegistrarPartida.setEnabled(true);
                   botonJugar.setEnabled(false);
                   botonHistorial.setEnabled(true);
                   nombreJugador = rs.getString(2);
                   usuarioJugador = rs.getString(3);
                   inicioSesion.setText("Cambiar cuenta");
               }else{
                   JOptionPane.showMessageDialog(jDialogInicioSesion, "No has introducido bien el usuario o contraseña.", "ERROR", JOptionPane.ERROR_MESSAGE);
               }
           }catch(SQLException e){
               JOptionPane.showMessageDialog(jDialogInicioSesion, "Ha ocurrido un error inesperado al iniciar sesión. Vuelva a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
           }
       }
       usuarioInicioSesion.setText("");
       contrasenhaInicioSesion.setText("");
    }//GEN-LAST:event_inicioSesionButtonActionPerformed
    /**
     * Cuando le de al botón de crear cuenta, se abre el jDialog de crear cuenta
     * @param evt 
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jDialogCrearCuenta.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void usuarioNuevaCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioNuevaCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioNuevaCuentaActionPerformed

    private void contrasenhaNuevaCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenhaNuevaCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contrasenhaNuevaCuentaActionPerformed
    /**
     * Cuando se crea una nueva cuenta, se comprueba si los campos no están vacíos y luego se inserta el usuario en la base de datos.
     * Posteriormente, el usuario tednrá que iniciar sesión para poder jugar
     * @param evt 
     */
    private void botonCrearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrearCuentaActionPerformed
                if(crearNombre.getText().isEmpty() || usuarioNuevaCuenta.getText().isEmpty() || contrasenhaNuevaCuenta.getText().isEmpty()){
                    JOptionPane.showMessageDialog(jDialogCrearCuenta, "No has rellenado todos los campos necesarios.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }else{
                    if(usuarioNuevaCuenta.getText().length()>17){
                        JOptionPane.showMessageDialog(jDialogCrearCuenta, "El usuario no debe superar los 17 caracteres.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }else{
                        try{
                            PreparedStatement ps = con.prepareStatement("INSERT INTO jugador(nombre, usuario, contrasenha) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                            ps.setString(1, crearNombre.getText());
                            ps.setString(2, usuarioNuevaCuenta.getText());
                            ps.setString(3, contrasenhaNuevaCuenta.getText());
                            nombreJugador = crearNombre.getText();
                            int filasAfectadas = ps.executeUpdate();
                            if(filasAfectadas>0){
                                    JOptionPane.showMessageDialog(jDialogCrearCuenta, "Se ha creado la cuenta correctamente.", "Cuenta creada", JOptionPane.INFORMATION_MESSAGE);
                                    jDialogCrearCuenta.setVisible(false);
                                }
                            ResultSet rs = ps.getGeneratedKeys();
                            if(rs.next()){
                                idJugador = rs.getInt(1);
                            }
                        }catch(SQLException e){
                            JOptionPane.showMessageDialog(jDialogCrearCuenta, "El usuario " + usuarioNuevaCuenta.getText() + " ya existe.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                crearNombre.setText("");
                usuarioNuevaCuenta.setText("");
                contrasenhaNuevaCuenta.setText("");
    }//GEN-LAST:event_botonCrearCuentaActionPerformed
    /**
     * Cuando el usuario le de al botón para ver sus comodines. Para ello se abrirá el jDialog correspondiente
     * @param evt 
     */
    private void jButtonComodinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComodinesActionPerformed
        jDialogJuego.setTitle(nombrePartida);
        jDialogComodinesJugador.setVisible(true);
    }//GEN-LAST:event_jButtonComodinesActionPerformed
    /**
     * Dependiendo de los puntos, se calcula el ganador, restandole la apuesta al perdedor.
     * También se actualiza los elementos de la pantalla
     * @throws UltimoRepartoException 
     */
    private void calcularGanador() throws UltimoRepartoException{
            int objetivo = maquina.getNumeroObjetivo();
            int puntosMaquina = maquina.calcularMisPuntos();
            int puntosJugador = Integer.parseInt(jLabelConteoJugador.getText());
            if(puntosMaquina==puntosJugador){
                ganador=0;
            }else{
                if(puntosMaquina>objetivo && puntosJugador<=objetivo){
                ganador=1;
                }else{
                    if(puntosJugador>objetivo && puntosMaquina<=objetivo){
                        ganador=2;
                    }else{
                        if(puntosJugador>objetivo && puntosMaquina>objetivo){
                            if(puntosJugador>puntosMaquina){
                                ganador=2;
                            }else{
                                ganador=1;
                            }
                        }else{
                            if(puntosJugador<puntosMaquina){
                                ganador=2;
                            }else{
                                ganador=1;
                            }
                        }
                    }
                }
            }
            if(ganador==1){
               jLabelVidasMaquina.setText(String.valueOf(maquina.acabarTurno(false)));
                jugador.acabarTurno(true);
            }else{
                if(ganador==2){
                    maquina.acabarTurno(true);
                    jLabelVidasJugador.setText(String.valueOf(jugador.acabarTurno(false)));
                }else{
                    maquina.acabarTurno(true);
                    jugador.acabarTurno(true);
                }
            }
            
            anunciarGanador();
            jLabelCartelGanadorFinRonda.setText(ganador==1?usuarioJugador+" gana":ganador==2?nombreMaquina+" gana":"Ha habido un empate");
            jLabelNombreJugadorFinRonda.setText(usuarioJugador);
            jLabelPuntosJugadorFinRonda.setText(String.valueOf(puntosJugador));
            jLabelPuntosMaquinaFinRonda.setText(String.valueOf(puntosMaquina));
            jButtonPedir.setEnabled(false);
            jButtonPasar.setEnabled(false);
            jButtonComodines.setEnabled(false);
            jButtonInfo.setEnabled(false);
            
    }
    /**
     * Si ambos jugadores pasan, se calcula el ganador
     * @param evt 
     */
    private void jButtonPasarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPasarActionPerformed
        mesa.haRealizadoAccionMaquina=false;
        try{
            if(maquina.turno()){
                mesa.repartirCarta(maquina);
                mesa.haRealizadoAccionJugador=false;
            }else{
                JOptionPane.showMessageDialog(jDialogJuego, "La máquina ha pasado.", "PASAR", JOptionPane.INFORMATION_MESSAGE);
                if(!mesa.haRealizadoAccionMaquina && !mesa.haRealizadoAccionJugador){
                    calcularGanador();
                }else{
                    mesa.haRealizadoAccionJugador=false;
                }
            }
        }catch(UltimoRepartoException e){
            JOptionPane.showMessageDialog(jDialogJuego, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPasarActionPerformed
    /**
     * Se le da una carta al usuario cuando le de a pedir
     * @param evt 
     */
    private void jButtonPedirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPedirActionPerformed
        try{
            mesa.repartirCarta(jugador);
            mesa.haRealizadoAccionJugador=true;
            mesa.haRealizadoAccionMaquina=false;
        }catch(UltimoRepartoException e){
            JOptionPane.showMessageDialog(jDialogJuego, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPedirActionPerformed
    /**
     * Se muestra la pantalla de info, mostrando 
     * @param evt 
     */
    private void jButtonInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInfoActionPerformed
        try{
            List<Comodin> listaContactos = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre, descripcion FROM comodin");
            while(rs.next()){
                listaContactos.add(new Comodin(rs.getString(1),rs.getString(2), mesa));
            }
            modeloComodin = new ComodinTableModel(listaContactos);
            // Crear el JTable con el modelo
            JTable tabla = new JTable(modeloComodin);

            // Configurar la tabla
            tabla.setFillsViewportHeight(true); // Ajustar al tamaño del panel
            tabla.setRowHeight(25); // Altura de las filas
           
            tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(800);
//            tabla.setPreferredScrollableViewportSize(new Dimension(1000, 300));

            // Crear un JScrollPane para la tabla de los contactos
            JScrollPane scrollPane = new JScrollPane(tabla);

            // Crear el JFrame
            JFrame ventana = new JFrame("Comodines");
            ventana.setSize(1000, 500);
            ventana.setLayout(null);
            ventana.setResizable(false);
            JLabel titulo = new JLabel("Comodines");
            titulo.setSize(18, 18);
            titulo.setBounds(500, -80, 200, 200);

            // Añadir los componentes a la ventana
            scrollPane.setBounds(100, 50, 800, 200);
            ventana.add(titulo);
            ventana.add(scrollPane);
            JButton botonMasInfo = new JButton();
            botonMasInfo.setBounds(400, 300, 200, 50);
            botonMasInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción a realizar cuando se presiona el botón
                jDialogInfoComodines.setVisible(true);
            }
        });
            botonMasInfo.setText("Imágenes comodin");
            ventana.add(botonMasInfo);
            ventana.getContentPane().setBackground(Color.decode("#cedaf4"));

            // Mostrar la ventana
            ventana.setVisible(true);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(jDialogJuego, "Ha ocurrido un error al cargar los comodines.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonInfoActionPerformed
    /**
     * Cuando le da al botón de jugar, se muestra el jDialog del juego
     * @param evt 
     */
    private void botonJugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonJugarActionPerformed
        jDialogJuego.setLocationRelativeTo(null);
        jDialogJuego.setVisible(true);
    }//GEN-LAST:event_botonJugarActionPerformed
    /**
     * Cuando el usuario abandona la partida, se actualiza el estado de la partida a abandono
     * @param evt 
     */
    private void jButtonAbandonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbandonarActionPerformed
        try{
            PreparedStatement ps = con.prepareStatement("UPDATE partida SET nombreGanador = ?, resultado='ABANDONO' WHERE idPartida = ?");
            ps.setString(1, nombreMaquina);
            ps.setInt(2, idPartida);
            int filasAfectadas = ps.executeUpdate();
            if(filasAfectadas>0){
                jDialogJuego.setVisible(false);
                jDialogComodinesJugador.setVisible(false);
                jDialogInfoComodines.setVisible(false);
            }else{
                JOptionPane.showMessageDialog(jDialogJuego, "No te has podido rendir. Vuelve a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(jDialogJuego, "No te has podido rendir. Vuelve a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAbandonarActionPerformed
    /**
     * Permite subir la música que suena de fondo
     * @param evt 
     */
    private void jButtonSubirVolumenMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSubirVolumenMenuActionPerformed
        subirVolumenMusica();
    }//GEN-LAST:event_jButtonSubirVolumenMenuActionPerformed
    /**
     * Permite bajar la música que suena de fondo
     * @param evt 
     */
    private void jButtonBajarVolumenMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBajarVolumenMenuActionPerformed
        bajarVolumenMusica();
    }//GEN-LAST:event_jButtonBajarVolumenMenuActionPerformed
    /**
     * Permite parar la música que suena de fondo
     * @param evt 
     */
    private void jButtonBajarVolumenMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBajarVolumenMenu1ActionPerformed
        pararMusica();
    }//GEN-LAST:event_jButtonBajarVolumenMenu1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UltimoReparto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UltimoReparto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UltimoReparto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UltimoReparto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UltimoReparto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCrearCuenta;
    private javax.swing.JButton botonCrearPartida;
    private javax.swing.JButton botonHistorial;
    private javax.swing.JButton botonJugar;
    private javax.swing.JButton botonRegistrarPartida;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel cartaJugador1;
    private javax.swing.JLabel cartaJugador2;
    private javax.swing.JLabel cartaJugador3;
    private javax.swing.JLabel cartaJugador4;
    private javax.swing.JLabel cartaJugador5;
    private javax.swing.JLabel cartaJugador6;
    private javax.swing.JLabel cartaJugador7;
    private javax.swing.JLabel cartaJugador8;
    private javax.swing.JLabel cartaJugador9;
    private javax.swing.JLabel cartaMaquina1;
    private javax.swing.JLabel cartaMaquina2;
    private javax.swing.JLabel cartaMaquina3;
    private javax.swing.JLabel cartaMaquina4;
    private javax.swing.JLabel cartaMaquina5;
    private javax.swing.JLabel cartaMaquina6;
    private javax.swing.JLabel cartaMaquina7;
    private javax.swing.JLabel cartaMaquina8;
    private javax.swing.JLabel cartaMaquina9;
    private javax.swing.JLabel comodinJugador1;
    private javax.swing.JLabel comodinJugador10;
    private javax.swing.JLabel comodinJugador11;
    private javax.swing.JLabel comodinJugador12;
    private javax.swing.JLabel comodinJugador2;
    private javax.swing.JLabel comodinJugador3;
    private javax.swing.JLabel comodinJugador4;
    private javax.swing.JLabel comodinJugador5;
    private javax.swing.JLabel comodinJugador6;
    private javax.swing.JLabel comodinJugador7;
    private javax.swing.JLabel comodinJugador8;
    private javax.swing.JLabel comodinJugador9;
    private javax.swing.JLabel comodinMaquina1;
    private javax.swing.JLabel comodinMaquina10;
    private javax.swing.JLabel comodinMaquina11;
    private javax.swing.JLabel comodinMaquina2;
    private javax.swing.JLabel comodinMaquina3;
    private javax.swing.JLabel comodinMaquina4;
    private javax.swing.JLabel comodinMaquina5;
    private javax.swing.JLabel comodinMaquina6;
    private javax.swing.JLabel comodinMaquina7;
    private javax.swing.JLabel comodinMaquina8;
    private javax.swing.JLabel comodinMaquina9;
    private javax.swing.JLabel comodinMesaJugador1;
    private javax.swing.JLabel comodinMesaJugador10;
    private javax.swing.JLabel comodinMesaJugador11;
    private javax.swing.JLabel comodinMesaJugador2;
    private javax.swing.JLabel comodinMesaJugador3;
    private javax.swing.JLabel comodinMesaJugador4;
    private javax.swing.JLabel comodinMesaJugador5;
    private javax.swing.JLabel comodinMesaJugador6;
    private javax.swing.JLabel comodinMesaJugador7;
    private javax.swing.JLabel comodinMesaJugador8;
    private javax.swing.JLabel comodinMesaJugador9;
    private javax.swing.JTextField contrasenhaInicioSesion;
    private javax.swing.JTextField contrasenhaNuevaCuenta;
    private javax.swing.JTextField crearNombre;
    private javax.swing.JLabel detalleComodin1;
    private javax.swing.JLabel detalleComodin10;
    private javax.swing.JLabel detalleComodin11;
    private javax.swing.JLabel detalleComodin12;
    private javax.swing.JLabel detalleComodin13;
    private javax.swing.JLabel detalleComodin14;
    private javax.swing.JLabel detalleComodin15;
    private javax.swing.JLabel detalleComodin2;
    private javax.swing.JLabel detalleComodin3;
    private javax.swing.JLabel detalleComodin4;
    private javax.swing.JLabel detalleComodin5;
    private javax.swing.JLabel detalleComodin6;
    private javax.swing.JLabel detalleComodin7;
    private javax.swing.JLabel detalleComodin8;
    private javax.swing.JLabel detalleComodin9;
    private javax.swing.JRadioButton dificilRadioButton;
    private javax.swing.JLabel fondoJuego;
    private javax.swing.JLabel fondoMenuPrincipal;
    private javax.swing.JLabel imagenComodin1;
    private javax.swing.JLabel imagenComodin10;
    private javax.swing.JLabel imagenComodin11;
    private javax.swing.JLabel imagenComodin12;
    private javax.swing.JLabel imagenComodin13;
    private javax.swing.JLabel imagenComodin14;
    private javax.swing.JLabel imagenComodin15;
    private javax.swing.JLabel imagenComodin2;
    private javax.swing.JLabel imagenComodin3;
    private javax.swing.JLabel imagenComodin4;
    private javax.swing.JLabel imagenComodin5;
    private javax.swing.JLabel imagenComodin6;
    private javax.swing.JLabel imagenComodin7;
    private javax.swing.JLabel imagenComodin8;
    private javax.swing.JLabel imagenComodin9;
    private javax.swing.JButton inicioSesion;
    private javax.swing.JButton inicioSesionButton;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonAbandonar;
    private javax.swing.JButton jButtonBajarVolumenMenu;
    private javax.swing.JButton jButtonBajarVolumenMenu1;
    private javax.swing.JButton jButtonComodines;
    private javax.swing.JButton jButtonInfo;
    private javax.swing.JButton jButtonPasar;
    private javax.swing.JButton jButtonPedir;
    private javax.swing.JButton jButtonSubirVolumenMenu;
    private javax.swing.JDialog jDialogComodinesJugador;
    private javax.swing.JDialog jDialogCrearCuenta;
    private javax.swing.JDialog jDialogFinDerrota;
    private javax.swing.JDialog jDialogFinRonda;
    private javax.swing.JDialog jDialogFinVictoria;
    private javax.swing.JDialog jDialogInfoComodines;
    private javax.swing.JDialog jDialogInicioSesion;
    private javax.swing.JDialog jDialogJuego;
    private javax.swing.JDialog jDialogPantallaCarga;
    private javax.swing.JDialog jDialogRegistrarPartida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelApuestaJugador;
    private javax.swing.JLabel jLabelApuestaMaquina;
    private javax.swing.JLabel jLabelCartelGanadorFinRonda;
    private javax.swing.JLabel jLabelConteoJugador;
    private javax.swing.JLabel jLabelConteoMaquina;
    private javax.swing.JLabel jLabelFondoComodinesJugador;
    private javax.swing.JLabel jLabelFondoCrearCuenta;
    private javax.swing.JLabel jLabelFondoDerrota;
    private javax.swing.JLabel jLabelFondoInfoComodines;
    private javax.swing.JLabel jLabelFondoInicioSesion;
    private javax.swing.JLabel jLabelFondoRegistrarPartida;
    private javax.swing.JLabel jLabelFondoVictoria;
    private javax.swing.JLabel jLabelNombreJugador;
    private javax.swing.JLabel jLabelNombreJugadorFinRonda;
    private javax.swing.JLabel jLabelNombreMaquina;
    private javax.swing.JLabel jLabelObjetivoJugador;
    private javax.swing.JLabel jLabelObjetivoMaquina;
    private javax.swing.JLabel jLabelPuntosJugadorFinRonda;
    private javax.swing.JLabel jLabelPuntosMaquinaFinRonda;
    private javax.swing.JLabel jLabelValorCartaOculta;
    private javax.swing.JLabel jLabelVidasJugador;
    private javax.swing.JLabel jLabelVidasMaquina;
    private javax.swing.JRadioButton loquilloRadioButton;
    private javax.swing.JRadioButton normalRadioButton;
    private javax.swing.JRadioButton principianteRadioButton;
    private javax.swing.JTextArea textNombrePartida;
    private javax.swing.JTextField usuarioInicioSesion;
    private javax.swing.JTextField usuarioNuevaCuenta;
    // End of variables declaration//GEN-END:variables
}
