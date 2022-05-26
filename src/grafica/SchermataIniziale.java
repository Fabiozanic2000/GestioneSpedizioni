package grafica;

import grafica.autenticazione.LoginAmministratore;
import dati.archivi.Archivio;
import dati.clienti.Cliente;
import grafica.cliente.SceltaUtente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


/**
 * Classe che implementa la schermata iniziale del programma.
 * Implementa ActionListener per permettere all'utente di cambiare schermata cliccando sui pulsanti.
 */
public class SchermataIniziale extends MyFrame implements ActionListener, WindowListener {
    //Inizializzazione pulsanti
    private final JButton pulsanteCliente = new JButton("Cliente");
    private final JButton pulsanteAmministratore = new JButton("Amministratore");

    //Archivio dei clienti
    private final Archivio<Cliente> elencoClienti;


    /**
     * Implementa la schermata principale con un BorderLayout e un GridLayout.
     * @param elencoClienti L'elenco dei clienti, che contiene i loro dati e spedizioni
     */
    public SchermataIniziale(Archivio<Cliente> elencoClienti){
        this.elencoClienti = elencoClienti;
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();
        JPanel pannelloSud = new JPanel();

        //Pannello Nord
        JLabel benvenuti = new JLabel("BENVENUTO NEL GESTIONALE SPEDIZIONI 2020-2021!", SwingConstants.CENTER);
        benvenuti.setFont(new Font("Font nuovo", Font.ITALIC, 14));
        benvenuti.setBackground(Color.RED);
        benvenuti.setForeground(Color.RED);
        pannelloNord.add(benvenuti);

        //Pannello Centro
        pannelloCentro.add(new JLabel("Scegliere come autenticarsi", SwingConstants.CENTER));

        //Pannello Sud
        pannelloSud.setLayout(new GridLayout(1, 2));
        pannelloSud.add(pulsanteCliente);
        pannelloSud.add(pulsanteAmministratore);

        //Ascoltatori dei pulsanti
        pulsanteCliente.addActionListener(this);
        pulsanteAmministratore.addActionListener(this);

        //Aggiunta pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

        //Rende visibile il frame
        rendiVisibile(this);
    }

    /**
     * Cambia la schermata in base al pulsante premuto dall'utente
     * @param e L'ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Cliente"))
            cambiaSchermata(this, new SceltaUtente(elencoClienti));
        if(e.getActionCommand().equals("Amministratore"))
            cambiaSchermata(this, new LoginAmministratore(elencoClienti));

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

