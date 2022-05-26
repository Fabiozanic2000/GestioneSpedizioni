package grafica;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe astratta che fa da base a tutti gli altri frame; sono presenti inoltre tutte le operazioni comuni
 * che gli altri frame possono effettuare, come il cambio schermata.
 * Implementa WindowListener.
 *
 * @author Fabio Zanichelli
 */
public abstract class MyFrame extends JFrame implements WindowListener {

    /**
     * Costruttore senza argomenti che richiama il costruttore che prende come parametro il titolo
     * passando una stringa vuota
     */
    public MyFrame(){
        this("");
    }

    /**
     * Setta il titolo del frame a quello passato per parametro e gli associa il WindowListener
     * @param titolo Il titolo del frame
     */
    public MyFrame(String titolo){
        super(titolo);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //Il programma viene chiuso nella funzione WindowClosing
        this.addWindowListener(this);
    }


    /**
     * Rende visibile un frame, centrandolo nella schermata e adattando la sua grandezza
     * @param f il frame da rendere visibile
     */
    public void rendiVisibile(MyFrame f){
        f.pack(); //Adatta la dimensione del frame
        f.setLocationRelativeTo(null); //Centra il frame
        f.setVisible(true); //Rende visibile il frame
    }

    /**
     * Cambia la schermata, distruggendo un frame e rendendo visibile il nuovo frame tramite il metodo rendiVisibile
     * @param vecchioFrame il frame da distruggere
     * @param nuovoFrame la nuova schermata
     */
    public void cambiaSchermata(MyFrame vecchioFrame, MyFrame nuovoFrame){
        rendiVisibile(nuovoFrame);
        vecchioFrame.dispose();
    }

    /**
     * Viene chiamato all'apertura di un frame. E' vuoto.
     * @param e Il WindowEvent
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }


    /**
     * Viene chiamata alla chiusura di un frame ed esce dal programma
     * @param e Il WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent e){
        System.exit(102);
    }

    /**
     * Viene invocato dopo la chiusura di una finestra. E' vuoto.
     * @param e Il WindowEvent
     */
    @Override
    public void windowClosed(WindowEvent e) {

    }

    /**
     * Viene chiamato quando una finestra viene ridotta a icone. E' vuoto.
     * @param e Il WindowEvent
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * Viene invocato quando una finestra non è più ridotta a icona. E' vuoto.
     * @param e Il WindowEvent
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * Metodo invocato quando una finestra acquisisce il focus. E' vuoto.
     * @param e Il WindowEvent
     */
    @Override
    public void windowActivated(WindowEvent e) {

    }

    /**
     * Metodo invocato quando una finestra perde il focus. E' vuoto.
     * @param e Il WindowEvent
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
