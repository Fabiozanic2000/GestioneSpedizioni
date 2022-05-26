package grafica.autenticazione;

import grafica.MyFrame;
import grafica.cliente.SceltaUtente;
import grafica.SchermataIniziale;
import dati.archivi.Archivio;
import dati.clienti.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata di registrazione di un cliente
 *
 * @author Fabio Zanichelli
 */
public class RegistrazioneCliente extends MyFrame implements ActionListener, WindowListener {
    private final JTextField username = new JTextField("", 25);
    private final JTextField password = new JTextField("", 25);
    private final JTextField indirizzo = new JTextField("", 25);
    private final JButton pulsanteRegistrazione = new JButton("Registrazione");
    private final JButton pulsanteHome = new JButton("Torna alla schermata home");
    private final Archivio<Cliente> elencoClienti;

    /**
     * Metodo costruttore, assegna l'elenco dei clienti all'attributo della classe e implementa la schermata
     * di registrazione dell'utente
     * @param elencoClienti L'elenco dei clienti
     */
    public RegistrazioneCliente(Archivio<Cliente> elencoClienti) {
        super("Registrazione cliente");
        this.elencoClienti = elencoClienti;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();
        JPanel pannelloSud = new JPanel();

        //Pannello Nord
        pannelloNord.add(new JLabel("Immettere TUTTI i dati richiesti"));

        //Pannello Centro
        pannelloCentro.setLayout(new GridLayout(3, 2));
        pannelloCentro.add(new JLabel("Inserire Username", SwingConstants.CENTER));
        pannelloCentro.add(username);
        pannelloCentro.add(new JLabel("Inserire Password", SwingConstants.CENTER));
        pannelloCentro.add(password);
        pannelloCentro.add(new JLabel("Inserire indirizzo", SwingConstants.CENTER));
        pannelloCentro.add(indirizzo);

        //Pannello Sud
        pulsanteHome.setBackground(Color.RED);
        pannelloSud.add(pulsanteHome);
        pannelloSud.add(pulsanteRegistrazione);

        //Ascoltatori
        pulsanteHome.addActionListener(this);
        pulsanteRegistrazione.addActionListener(this);

        //Aggiunta dei pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

        rendiVisibile(this);
    }

    /**
     * Metodo invocato alla pressione di un pulsante da parte dell'utente e varia il proprio comportamento sulla base
     * del pulsante premuto.
     * Se viene premuto "Torna alla schermata home" viene cambiata la schermata a quella della home del programma.
     * Se viene premuto "Registrazione" vengono controllate le credenziali inserite ed effettuata la registrazione; viene
     * visualizzato un messaggio d'errore nel caso i dati inseriti non siano accettabili.
     * @param e LActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Torna alla schermata home")) {
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));
            this.dispose();
        }
        if (e.getActionCommand().equals("Registrazione")) {
            String user, pass, ind;
            boolean trovato = false;
            user = username.getText();
            pass = password.getText();
            ind = indirizzo.getText();

            if(user.equals("") || pass.equals("") || ind.equals("")) {
                JOptionPane.showMessageDialog(this, "Riempire tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < elencoClienti.size() && !trovato; i++) {
                Cliente c = elencoClienti.get(i);
                if (c.getUsername().equals(user)) {
                    trovato = true;
                    JOptionPane.showMessageDialog(this, "Utente già registrato!", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (!trovato) {
                Cliente buff = new Cliente(user, pass, ind);
                elencoClienti.aggiungi(buff);
                cambiaSchermata(this, new SceltaUtente(elencoClienti));
            }

        }
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
