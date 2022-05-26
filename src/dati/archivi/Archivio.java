package dati.archivi;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe che implementa un archivio generics, con i metodi base; NON permette la rimozione.
 * @param <E> il tipo di dato, deve essere serializzabile
 *
 * @author Fabio Zanichelli
 */
public class Archivio <E extends Serializable> implements Serializable {

    /**
     * L'ID della classe per la serializzazione
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * L'ArrayList di oggetti di tipo E che conterrà gli elementi dell'archivio
     */
    ArrayList<E> elenco;

    /**
     * Costruttore, crea l'ArrayList che conterra gli elementi dell'archivio
     */
    public Archivio(){
        elenco = new ArrayList<>();
    }


    /**
     * Aggiunge un elemento all'archivio
     * @param p l'elemento da aggiungere
     */
    public void aggiungi(E p){
        elenco.add(p);
    }

    /**
     * Ritorna l'oggetto memorizzato all'indice passato come parametro
     * @param indice l'indice dell'elemento desiderato
     * @return l'elemento all'indice passato come parametro
     */
    public E get(int indice){
        return elenco.get(indice);
    }


    /**
     * Ritorna il numero di elementi nell'archivio
     * @return il numero di elementi nell'archivio
     */
    public int size(){
        return elenco.size();
    }


    /**
     * Ritorna un oggetto di classe Archivio preso in input dal file DatiProgetto.ser
     * @return L'archivio caricato dal file
     */
    public Archivio<E> carica(){
        File cd = new File("");
        File fileDati = new File(cd.getAbsolutePath() + File.separator + "DatiProgetto.ser");
        Archivio<E> ac = new Archivio<>();
        FileInputStream fis;
        ObjectInputStream ois;

        try{
            fis = new FileInputStream(fileDati);
            ois = new ObjectInputStream(fis);
            ac.elenco = (ArrayList<E>) ois.readObject();
            ois.close();
        }catch (FileNotFoundException e){
            try {
                fileDati.createNewFile();
            }catch(IOException e2){
                System.exit(1);
            }
        } catch (IOException e){
            if(fileDati.length() == 0)
                ac.elenco = new ArrayList<>(0);
        } catch (ClassNotFoundException e){
            System.exit(2);
        }
        return ac;
    }

    /**
     * Salva l'archivio nel file datiProgetto.ser
     */
    public void salva(){
        File cd = new File("");
        File fileBuono = new File(cd.getAbsolutePath() + File.separator + "DatiProgetto.ser");
        try{
            FileOutputStream fos = new FileOutputStream(fileBuono);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(elenco);
            oos.close();
        }
        catch (IOException e){
            System.exit(3);
        }
    }

}

