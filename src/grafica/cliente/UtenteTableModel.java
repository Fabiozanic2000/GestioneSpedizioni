package grafica.cliente;

import dati.archivi.Archivio;
import dati.spedizioni.Spedizione;
import dati.spedizioni.SpedizioneAssicurata;

import javax.swing.table.AbstractTableModel;

/**
 * Classe che implementa il modello della tabella visionabile dall'utente
 */
public class UtenteTableModel extends AbstractTableModel {

    /**
     * Lista delle spedizioni del cliente
     */
    private final Archivio<Spedizione> listaSpedizioni;

    /**
     * Vettore di stringhe con le intestazioni delle colonne
     */
    String[] nomiColonne = {"N", "Codice", "Destinazione", "Peso", "Data", "Stato", "Valore Assicurato"};

    /**
     * Assegna l'elenco delle spedioni all'attributo della classe
     * @param a L'elenco delle spedizioni
     */
    public UtenteTableModel(Archivio<Spedizione> a){
        listaSpedizioni = a;
    }

    /**
     * Ritorna il numero di righe della tabella.
     * @return il numero di righe della tabella
     */
    @Override
    public int getRowCount() {
        return listaSpedizioni.size();
    }

    /**
     * Ritorna il numero di colonne della tabella.
     * @return Il numero di colonne della tabella
     */
    @Override
    public int getColumnCount() {
        return nomiColonne.length;
    }

    /**
     * Ritorna il valore da inserire nella cella di riga RowIndex e di colonna ColumnIndex
     * @param rowIndex L'indice di riga della cella
     * @param columnIndex L'indice di colonna della cella
     * @return L'oggetto da inserire nella cella di riga RowIndex e colonna ColumnIndex
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Spedizione s = listaSpedizioni.get(rowIndex);
        switch (columnIndex){
            case 0: return rowIndex + 1;
            case 1: return s.getCodice();
            case 2: return s.getDestinazione();
            case 3: return s.getPeso();
            case 4: return s.getData();
            case 5: return s.getStatoSpedizione();
            case 6: if (s instanceof SpedizioneAssicurata) {
                return ((SpedizioneAssicurata) s).getValoreAssicurato();}
            default: return null;
        }
    }


    /**
     * Ritorna l'intestazione della colonna all'indice col
     * @param indiceColonna L'indice della colonna
     * @return L'intestazione della colonna all'indice col
     */
    @Override
    public String getColumnName(int indiceColonna) {
        return nomiColonne[indiceColonna];
    }

    /**
     * Ritorna true se la cella è editabile, false altrimenti
     * @param riga L'indice di riga della cella
     * @param colonna L'indice di colonna della cella
     * @return true se la cella è editabile, false altrimenti
     */
    @Override
    public boolean isCellEditable(int riga, int colonna){
        return colonna == 5 && this.getValueAt(riga, colonna).equals("Fallita") && (listaSpedizioni.get(riga) instanceof SpedizioneAssicurata);
    }

    /**
     * Cambia i valori nella tabella se questi sono cambiati e aggiorna la tabella
     * @param aValue Il nuovo valore
     * @param rowIndex L'indice di riga della cella col cambiamento
     * @param columnIndex L'indice di colonna della cella col cambiamento
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex){
        Spedizione s = listaSpedizioni.get(rowIndex);
        s.setStatoSpedizione((String)aValue);
        fireTableDataChanged();
    }
}


