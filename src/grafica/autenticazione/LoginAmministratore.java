package grafica.autenticazione;

import grafica.MyFrame;
import grafica.SchermataIniziale;
import grafica.amministratore.VisualeAmministratore;
import dati.archivi.Archivio;
import dati.clienti.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata di Login dell'amministratore
 *
 * @author Fabio Zanichelli
 */
public class LoginAmministratore extends MyFrame implements ActionListener, WindowListener {
    private final JTextField username = new JTextField("", 25);
    private final JTextField password = new JTextField("", 25);
    private final JButton pulsanteLogin = new JButton("Login");
    private final JButton pulsanteLogout = new JButton("Indietro");
    private final Archivio<Cliente> elencoClienti;

    /**
     * Metodo costruttore, assegna l'elenco dei clienti all'attributo della classe  e implementa la schermata di login
     * dell'amministratore
     * @param elencoClienti L'elenco dei clienti
     */
    public LoginAmministratore(Archivio<Cliente> elencoClienti){
        super("Login Amministratore");
        this.elencoClienti = elencoClienti;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();
        JPanel pannelloSud = new JPanel();

        //Pannello Nord
        pannelloNord.add(new JLabel("Immettere TUTTI i dati richiesti"));

        //Pannello Centro
        pannelloCentro.setLayout(new GridLayout(2, 2));
        pannelloCentro.add(new JLabel("Inserire username", SwingConstants.CENTER));
        username.setFocusable(true);
        pannelloCentro.add(username);
        pannelloCentro.add(new JLabel("Inserire password", SwingConstants.CENTER));
        pannelloCentro.add(password);

        //Pannello Sud
        pannelloSud.setLayout(new FlowLayout());
        pulsanteLogout.setBackground(Color.RED);
        pannelloSud.add(pulsanteLogout);
        pannelloSud.add(pulsanteLogin);

        //Ascoltatori
        pulsanteLogin.addActionListener(this);
        pulsanteLogout.addActionListener(this);

        //Aggiunta pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

        this.rendiVisibile(this);
    }

    /**
     * Invocato alla pressione di un pulsante da parte dell'utente.
     * Se viene premuto "Indietro" la schermata verrà cambiata alla home del programma.
     * Se viene premuto "Login", verranno effettuati controlli sulle credenziali ai quali seguirà o meno un messaggio
     * di errore in caso di credenziali errate
     * @param e L'ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Indietro")){
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));
        }

        if(e.getActionCommand().equals("Login")){
            String user, pass;
            user = username.getText();
            pass = password.getText();
            final String USERNAME_AMMINISTRATORE = "1234";
            final String PASSWORD_AMMINISTRATORE = "1234";
            if(user.equals(USERNAME_AMMINISTRATORE) && pass.equals(PASSWORD_AMMINISTRATORE)){
                cambiaSchermata(this, new VisualeAmministratore(elencoClienti));
            }
            else{
                JOptionPane.showMessageDialog(this, "Credenziali errate", "Errore", JOptionPane.ERROR_MESSAGE);
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


