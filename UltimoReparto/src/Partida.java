/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DAM2
 */
public class Partida {
   private String fecha;
   private String nombreGanador;
   private String resultado;

    public Partida(String fecha, String nombreGanador, String resultado) {
        this.fecha = fecha;
        this.nombreGanador = nombreGanador;
        this.resultado = resultado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombreGanador() {
        return nombreGanador;
    }

    public String getResultado() {
        return resultado;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombreGanador(String nombreGanador) {
        this.nombreGanador = nombreGanador;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
   
   
}
