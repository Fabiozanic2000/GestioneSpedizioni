package dati.spedizioni;

import dati.clienti.Cliente;

/**
 * Classe che implementa una spedizione normale estendendo la classe astratta Spedizione.
 *
 * @author Fabio Zanichelli
 */
public class SpedizioneNormale extends Spedizione{


    /**
     * Costruttore della classe spedizione normale
     * @param destinazione la destinazione della spedizione
     * @param peso il peso della spedizione
     * @param c Il cliente che ha immesso la spedizione
     */
    public SpedizioneNormale(String destinazione, int peso, Cliente c) {
        super(destinazione, peso, c);
    }

}

