package dati.spedizioni;


import dati.clienti.Cliente;

/**
 * Classe che implementa una spedizione assicurata estendendo la classe Spedizione.
 *
 * @author Fabio Zanichelli
 */
public class SpedizioneAssicurata extends Spedizione{


    /**
     * Il valore assicurato della spedizione
     */
    private final int valoreAssicurato;


    /**
     * Costruttore della classe SpedizioneAssicurata, inizializza gli attributi della spedizione
     * @param destinazione La destinazione della spedizione
     * @param peso Il peso della spedizione
     * @param valoreAssicurato Il valore assicurato della spedizione
     * @param c Il cliente che ha immesso la spedizione
     */
    public SpedizioneAssicurata(String destinazione, int peso, int valoreAssicurato, Cliente c) {
        super(destinazione, peso, c);
        this.valoreAssicurato = valoreAssicurato;
    }


    /**
     * Ritorna il valore assicurato della spedizione
     * @return il valore assicurato della spedizione
     */
    public int getValoreAssicurato() {
        return valoreAssicurato;
    }
}

