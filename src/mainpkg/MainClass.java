package mainpkg;

import grafica.SchermataIniziale;
import dati.archivi.Archivio;
import dati.clienti.Cliente;

/**
 * Classe che implementa il metodo main; carica l'elenco dei clienti da file e rende visibile la schermata iniziale
 *
 * @author Fabio Zanichelli
 */
public class MainClass {
    /**
     * Metodo main
     * @param args Argomenti all'avvio del programma (nessuno in questo programma)
     */
    public static void main(String[] args){
        Archivio<Cliente> elencoClienti = new Archivio<>();
        elencoClienti = elencoClienti.carica();
        new SchermataIniziale(elencoClienti);
    }
}
