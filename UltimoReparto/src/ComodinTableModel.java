
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
public class ComodinTableModel extends AbstractTableModel{
    private final String[] columnas = {"Nombre", "Descripcion"}; //Nombre de las columnas
    private final List<Comodin> comodines; // Creamos una lista de comodines

    public ComodinTableModel(List<Comodin> comodines) {
        this.comodines = comodines;
    }

    @Override
    public int getRowCount() {
        return comodines.size(); // Número de filas = número de comodines
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
        Comodin equipo = comodines.get(rowIndex); // Producto en la fila actual
        return switch (columnIndex) {
            case 0 -> equipo.getNombre();// Columna donde aparece el nombre del comodin
            case 1 -> equipo.getDescripcion();// Columna donde aparece el primer apellido del comodin
            default -> null;
        }; 
        
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Comodin comodin = comodines.get(rowIndex); // Equipo en la fila actual
        switch (columnIndex) {
            case 0 -> comodin.setNombre((String) aValue); // Editar nombre
            case 1 -> comodin.setDescripcion((String) aValue);// Editar primer apellido
}
        fireTableCellUpdated(rowIndex, columnIndex); // Notificar el cambio
    }

    public void agregarComodin(Comodin contacto) {
        comodines.add(contacto);
        fireTableRowsInserted(comodines.size() - 1, comodines.size() - 1); // Notificar nueva fila
    }

    public void eliminarComodin(int rowIndex) {
        comodines.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex); // Notificar eliminación
    }
}
