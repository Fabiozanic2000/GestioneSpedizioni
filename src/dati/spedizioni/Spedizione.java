package dati.spedizioni;

import dati.clienti.Cliente;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Classe astratta che implementa una spedizione e gli attributi in comune tra la spedizione normale e
 * la spedizione assicurata.
 *
 * @author Fabio Zanichelli
 */
public abstract class Spedizione implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * La destinazione della spedizione
     */
    private final String destinazione;

    /**
     * La data dell'immissione della spedizione, in formato gg/mm/aaaa
     */
    private final String data;

    /**
     * Il peso della spedizione
     */
    private final int peso;
    /**
     * Il codice della spedizione; viene generato nel costruttore in base agli altri parametri
     */
    private final String codice;

    /**
     * Lo stato della spedizione
     */
    private String statoSpedizione;

    /**
     * Costruttore della classe Spedizione, inizializza tutti gli attributi della spedizione; lo stato sarà inizializzato
     * a "In preparazione", la data sarà inizializzata alla data corrente mentre gli altri attributi sono dipendenti
     * dai dati inseriti dall'utente.
     * @param destinazione La destinazione della spedizione
     * @param peso Il peso della spedizione
     * @param c Il cliente che ha immesso la nuova spedizione
     */
    public Spedizione(String destinazione, int peso, Cliente c){
        this.destinazione = destinazione;
        this.peso = peso;
        this.statoSpedizione = "In preparazione";
        codice = c.getUsername().toUpperCase() + c.getNumeroProgressivo() + destinazione.substring(0, 1).toUpperCase() +
                Integer.toString(peso).charAt(0);
        c.incrementaNumeroProgressivo();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        data = dataFormat.format(new Date());
    }


    /**
     * Ritorna la destinazione della spedizione
     * @return la destinazione della spedizione
     */
    public String getDestinazione() {
        return destinazione;
    }

    /**
     * Ritorna la data in cui è stata immessa la spedizione in una stringa nel formato gg/mm/aaaa
     * @return la data di immissione della spedizione
     */
    public String getData() {
        return data;
    }

    /**
     * Ritorna il peso della spedizione
     * @return il peso della spedizione
     */
    public int getPeso() {
        return peso;
    }

    /**
     * Ritorna il codice della spedizione
     * @return il codice della spedizione
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Ritorna lo stato della spedizione
     * @return lo stato della spedizione
     */
    public String getStatoSpedizione() {
        return statoSpedizione;
    }

    /**
     * Cambia lo stato della spedizione, settandolo a quello passato come parametro
     * @param statoSpedizione il nuovo stato della spedizione
     */
    public void setStatoSpedizione(String statoSpedizione) {
        this.statoSpedizione = statoSpedizione;
    }
}
