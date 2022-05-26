package dati.clienti;

import dati.archivi.ArchivioConEliminazione;
import dati.spedizioni.Spedizione;

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe pubblica che implementa un cliente con i suoi attributi e metodi
 *
 * @author Fabio Zanichelli
 */
public class Cliente implements Serializable {
    /**
     * L'ID della versione che serve per la serializzazione
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * L'username dell'utente
     */
    private final String username;
    /**
     * La password dell'utente
     */
    private final String password;
    /**
     * L'indirizzo dell'utente
     */
    private final String indirizzo;
    /**
     * L'elenco di spedizioni immesse dall'utente
     */
    private final ArchivioConEliminazione<Spedizione> elencoSpedizioni;
    /**
     * Numero totale di spedizioni immesse dall'utente, serve per generare codici univoci
     */
    private int numeroProgressivo;

    /**
     * Costruttore della classe Cliente, inizializza tutti gli attributi del cliente; viene creato l'ArrayList per la
     * memorizzazione delle spedizioni, viene settato numeroProgressivo a 0 e vengono settati gli altri attributi in base
     * ai parametri passati al costruttore
     * @param username L'username dell'utente
     * @param password La password dell'utente
     * @param indirizzo L'indirizzo dell'utente
     */
    public Cliente(String username, String password, String indirizzo){
        this.username = username;
        this.password = password;
        this.indirizzo = indirizzo;
        elencoSpedizioni = new ArchivioConEliminazione<>();
        setNumeroProgressivo(0);
    }

    /**
     * Ritorna l'username dell'utente
     * @return l'username del cliente
     */
    public String getUsername() {
        return username;
    }

    /**
     * Ritorna la password dell'utente
     * @return la password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ritorna l'archivio che contiene le spedizioni dell'utente
     * @return L'archivio che contiene le spedizioni dell'utente
     */
    public ArchivioConEliminazione<Spedizione> getElencoSpedizioni() {
        return elencoSpedizioni;
    }

    /**
     * Ritorna l'indirizzo dell'utente
     * @return L'indirizzo dell'utente
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Ritorna il numero di spedizioni totali immesse dal cliente
     * @return Il numero di spedizioni totali immesse dal cliente
     */
    public int getNumeroProgressivo() {
        return numeroProgressivo;
    }

    /**
     * Modifica il nuemro progressivo in base al parametro passato
     * @param numeroProgressivo Il nuovo valore del numero progressivo
     */
    public void setNumeroProgressivo(int numeroProgressivo) {
        this.numeroProgressivo = numeroProgressivo;
    }

    /**
     * Incrementa di 1 il valore del numero progressivo
     */
    public void incrementaNumeroProgressivo(){
        numeroProgressivo++;
    }
}
