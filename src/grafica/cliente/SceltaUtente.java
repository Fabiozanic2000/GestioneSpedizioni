package grafica.cliente;

import grafica.MyFrame;
import grafica.SchermataIniziale;
import grafica.autenticazione.LoginCliente;
import grafica.autenticazione.RegistrazioneCliente;
import dati.archivi.Archivio;
import dati.clienti.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata in cui l'utente deve scegliere se fare il login o registrarsi.
 * Vengono utilizzati BorderLayout e Gridlayout.
 *
 * @author Fabio Zanichelli
 */
public class SceltaUtente extends MyFrame implements ActionListener, WindowListener {
    //Inizializzazione pulsanti
    private final JButton pulsanteLogin = new JButton("Login");
    private final JButton pulsanteRegistrazione = new JButton("Registrazione");
    private final JButton pulsanteHome = new JButton("Indietro");

    //Archivio dei clienti
    private final Archivio<Cliente> elencoClienti;

    /**
     * Costruttore che inizializza l'elenco dei clienti passato come parametro e crea la schermata grafica
     * necessaria per la scelta dell'utente tra login e registrazione
     * @param elencoClienti L'elenco dei clienti
     */
    public SceltaUtente(Archivio<Cliente> elencoClienti){
        this.elencoClienti = elencoClienti;
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();
        JPanel pannelloSud = new JPanel();

        //Pannello nord
        pannelloNord.add(new JLabel("Scegliere l'azione da compiere"));

        //Pannello Centro
        JLabel utRegistrati = new JLabel("Al momento ci sono " + elencoClienti.size() + " utenti registrati nel sistema");
        utRegistrati.setFont(new Font("FontPiccolo", Font.ITALIC, 12 ));
        pannelloCentro.add(utRegistrati);

        //Pannello Sud
        pannelloSud.setLayout(new GridLayout(1, 3));
        pannelloSud.add(pulsanteLogin);
        pulsanteLogin.addActionListener(this);
        pannelloSud.add(pulsanteRegistrazione);
        pulsanteRegistrazione.addActionListener(this);
        pulsanteHome.setBackground(Color.RED);
        pannelloSud.add(pulsanteHome);
        pulsanteHome.addActionListener(this);

        //Aggiungo i pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

        rendiVisibile(this);
    }


    /**
     * Cambia la schermata in base al pulsante premuto dall'utente
     * @param e L'ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Indietro"))
            cambiaSchermata(this, new SchermataIniziale(elencoClienti)); //Torna alla home
        if(e.getActionCommand().equals("Login"))
            cambiaSchermata(this, new LoginCliente(elencoClienti)); //Schermata login
        if(e.getActionCommand().equals("Registrazione"))
            cambiaSchermata(this, new RegistrazioneCliente(elencoClienti)); //Schermata registrazione
    }

    /**
     * Salva l'elenco dei clienti su file prima di uscire; questo metodo viene chiamato in automatico
     * se l'utente preme il pulsante per chiudere il frame
     * @param e Il WindowEvent
     */
    @Override
    public void windowClosing(WindowEvent e) {
        elencoClienti.salva();
        System.exit(0);
    }
}
