/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

/**
 *
 * @author David
 */
public enum Dificultad {
    PRINCIPIANTE(80),
    NORMAL(65),
    DIFICIL(45), 
    LOQUILLO(35);

    private int porcentajeArriesgo;

    private Dificultad(int porcentajeArriesgo) {
            this.porcentajeArriesgo=porcentajeArriesgo;
    }

    public int getPorcentajeArriesgo() {
            return porcentajeArriesgo;
    }
}
