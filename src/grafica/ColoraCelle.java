package grafica;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Classe che estende DefaultTableCellRenderer, serve per centrare i valori nelle rispettive celle e per colorarle
 * in base allo stato della spedizione
 *
 * @author Fabio Zanichelli
 */
public class ColoraCelle extends DefaultTableCellRenderer {

    /**
     * Metodo che fa override e ritorna un componente con le impostazioni opportune.
     * @param table La tabella
     * @param value Il valore della cella
     * @param isSelected Indica se la cella è selezionata
     * @param hasFocus Indica se la cella ha il focus
     * @param row La riga della cella
     * @param column La colonna della cella
     * @return Il componente con le corrette impostazioni
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        //Gli elementi vengono centrati rispetto alla cella
        this.setHorizontalAlignment(JLabel.CENTER);

        //Setta il colore in base allo stato della spedizione
        String st = (String) table.getValueAt(row, 5);
        switch(st){
            case "In preparazione":
                setBackground(Color.LIGHT_GRAY);
                break;
            case "In transito" :
                setBackground(Color.YELLOW);
                break;
            case "Ricevuta":
                setBackground(Color.GREEN);
                break;
            case "Fallita":
                setBackground(Color.RED);
                break;
            case "Richiesta rimborso":
                setBackground(Color.ORANGE);
                break;
            case "Rimborsata":
                setBackground(Color.CYAN);
                break;
        }
        return this;
    }
}

