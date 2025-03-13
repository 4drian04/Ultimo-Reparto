
import java.util.List;
import javax.swing.table.AbstractTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DAM2
 */
public class PartidaTableModel extends AbstractTableModel{
    private final String[] columnas = {"Fecha", "Nombre ganador", "Resultado"}; //Nombre de las columnas
    private final List<Partida> partidas; // Creamos una lista de contactos

    public PartidaTableModel(List<Partida> partidas) {
        this.partidas = partidas;
    }

    @Override
    public int getRowCount() {
        return partidas.size(); // Número de filas = número de contactos
    }

    @Override
    public int getColumnCount() {
        return columnas.length; // Número de columnas
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex]; // Nombre de la columna
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Partida partida = partidas.get(rowIndex); // Producto en la fila actual
        return switch (columnIndex) {
            case 0 -> partida.getFecha();// Columna donde aparece el nombre del contacto
            case 1 -> partida.getNombreGanador();
            case 2 -> partida.getResultado();// Columna donde aparece el primer apellido del contacto
            default -> null;
        }; 
        
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Partida partida = partidas.get(rowIndex); // Equipo en la fila actual
        switch (columnIndex) {
            case 0 -> partida.setFecha((String) aValue);// Columna donde aparece el nombre del contacto
            case 1 -> partida.setNombreGanador((String) aValue);
            case 2 -> partida.setResultado((String) aValue);// Columna donde aparece el primer apellido del contacto
}
        fireTableCellUpdated(rowIndex, columnIndex); // Notificar el cambio
    }

    public void agregarContacto(Partida partida) {
        partidas.add(partida);
        fireTableRowsInserted(partidas.size() - 1, partidas.size() - 1); // Notificar nueva fila
    }

    public void eliminarContacto(int rowIndex) {
        partidas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex); // Notificar eliminación
    }
}
