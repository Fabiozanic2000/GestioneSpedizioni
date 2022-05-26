package grafica.amministratore;

import dati.archivi.Archivio;
import dati.clienti.Cliente;
import dati.spedizioni.Spedizione;
import dati.spedizioni.SpedizioneAssicurata;
import dati.spedizioni.SpedizioneNormale;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Classe che estende AbstractTableModel e implementa il modello per la tabella nella schermata dell'amministratore
 *
 * @author Fabio Zanichelli
 */
public class AmministratoreTableModel extends AbstractTableModel {

    /**
     * Vettore di stringhe che colonne che contiene le intestazioni delle colonne
     */
    String[] nomiColonne = {"N", "Destinazione", "Peso", "Data", "Codice", "Stato", "Valore Assicurato"};

    /**
     * Elenco dei clienti
     */
    private Archivio<Cliente> elencoClienti;

    /**
     * Assegna l'elenco dei clienti all'attrtibuto di questa classe
     * @param elencoClienti l'elenco dei clienti
     */
    public AmministratoreTableModel(Archivio<Cliente> elencoClienti) {
        this.elencoClienti = elencoClienti;
    }

    /**
     * Ritorna il numero di righe della tabella.
     * @return il numero di righe della tabella
     */
    @Override
    public int getRowCount() {
        int righe = 0;
        for (int i = 0; i < elencoClienti.size(); i++) {
            righe += elencoClienti.get(i).getElencoSpedizioni().size();
        }
        return righe;
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
        Spedizione s = trovaSpedizione(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex+1;
            case 1:
                return s.getDestinazione();
            case 2:
                return s.getPeso();
            case 3:
                return s.getData();
            case 4:
                return s.getCodice();
            case 5: return s.getStatoSpedizione();
            case 6:
                if (s instanceof SpedizioneAssicurata) {
                    return ((SpedizioneAssicurata) s).getValoreAssicurato();
                }
            default: return null;
        }
    }

    /**
     * Ritorna la spedizione corrispondente alla riga passata come parametro.
     * Per farlo, accede in sequenza a tutti i clienti calcolando la somma delle
     * spedizioni dei clienti precedenti
     * @param rowIndex L'indice di riga
     * @return La spedizione della riga di indice RowIndex
     */
    private Spedizione trovaSpedizione(int rowIndex) {
        int cl = 0, somma = 0, totale = 0;
        while(totale < rowIndex + 1){
            totale += elencoClienti.get(cl).getElencoSpedizioni().size();
            if(totale < rowIndex+1){
                somma += elencoClienti.get(cl).getElencoSpedizioni().size();
                cl++;
            }
        }

        int indiceSpedizione = rowIndex - somma;
        Cliente c = elencoClienti.get(cl);
        return c.getElencoSpedizioni().get(indiceSpedizione);
    }

    /**
     * Ritorna l'intestazione della colonna all'indice col
     * @param col L'indice della colonna
     * @return L'intestazione della colonna all'indice col
     */
    public String getColumnName(int col){
        return nomiColonne[col];
    }

    /**
     * Ritorna true se la cella è editabile, false altrimenti
     * @param rowIndex L'indice di riga della cella
     * @param columnIndex L'indice di colonna della cella
     * @return true se la cella è editabile, false altrimenti
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5 && !(this.getValueAt(rowIndex, columnIndex).equals("Ricevuta")) &&
                !(this.getValueAt(rowIndex, columnIndex).equals("Rimborsata"));
    }

    /**
     * Cambia i valori nella tabella se questi sono cambiati e aggiorna la tabella
     * @param aValue Il nuovo valore
     * @param rowIndex L'indice di riga della cella col cambiamento
     * @param columnIndex L'indice di colonna della cella col cambiamento
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Spedizione s = trovaSpedizione(rowIndex);
        if(trovaSpedizione(rowIndex) instanceof SpedizioneNormale && (aValue.equals("Richiesta rimborso") || aValue.equals("Rimborsata"))){
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "La spedizione non è assicurata!",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        s.setStatoSpedizione((String) aValue);
        fireTableDataChanged();
    }

    /**
     * Aggiorna la tabella
     */
    public void aggiorna(){
        fireTableDataChanged();
    }
}
