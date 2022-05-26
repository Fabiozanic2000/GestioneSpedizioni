package dati.archivi;

import java.io.Serializable;

/**
 * Classe che estende la classe Archivio, implementando anche il metodo per l'eliminazione di un elemento.
 * @param <E> il tipo degli elementi dell'archivio; deve essere serializzabile.
 *
 * @author Fabio Zanichelli
 */
public class ArchivioConEliminazione<E extends Serializable> extends dati.archivi.Archivio<E> {


    /**
     * Cancella l'elemento all'indice passato come parametro
     * @param indice l'indice dell'elemento che si desidera eliminare
     */
    public void cancella(int indice){
        elenco.remove(indice);
    }
}

