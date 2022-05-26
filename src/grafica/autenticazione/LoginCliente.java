package grafica.autenticazione;

import grafica.MyFrame;
import grafica.SchermataIniziale;
import grafica.cliente.MenuCliente;
import dati.archivi.Archivio;
import dati.clienti.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata di login del cliente
 *
 * @author Fabio Zanichelli
 */
public class LoginCliente extends MyFrame implements ActionListener, WindowListener {
    private final JTextField username = new JTextField("", 25);
    private final JTextField password = new JTextField("", 25);
    private final JButton pulsanteLogin = new JButton("Login");
    private final JButton pulsanteHome = new JButton("Torna alla schermata home");
    private final Archivio<Cliente> elencoClienti;

    /**
     * Metodo costruttore, implementa la schermata di login del cliente.
     * @param elencoClienti L'elenco dei clienti
     */
    public LoginCliente(Archivio<Cliente> elencoClienti){
        super("Login cliente");
        this.elencoClienti = elencoClienti;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();
        JPanel pannelloSud = new JPanel();

        //Pannello Nord
        pannelloNord.add(new JLabel("Immettere TUTTI i dati richiesti"));

        //Pannello Centro
        pannelloCentro.setLayout(new GridLayout(2,2));
        pannelloCentro.add(new JLabel("Inserire Username", SwingConstants.CENTER));
        pannelloCentro.add(username);
        pannelloCentro.add(new JLabel("Inserire Password",SwingConstants.CENTER));
        pannelloCentro.add(password);

        //Pannello Sud
        pulsanteHome.setBackground(Color.RED);
        pannelloSud.add(pulsanteHome);
        pannelloSud.add(pulsanteLogin);

        //Ascoltatori
        pulsanteHome.addActionListener(this);
        pulsanteLogin.addActionListener(this);

        //Aggiunta dei panelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

        rendiVisibile(this);
    }

    /**
     * Invocato alla pressione di un pulsante da parte dell'utente.
     * Se viene premuto "Torna alla schermata home" la schermata verrà cambiata alla home del programma.
     * Se viene premuto "Login", verranno effettuati controlli sulle credenziali ai quali seguirà o meno un messaggio
     * di errore in caso di credenziali errate
     * @param e L'ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Torna alla schermata home")){
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));

        }

        if(e.getActionCommand().equals("Login")){
            boolean trovato = false;
            String user = username.getText();
            String pass = password.getText();
            for(int i = 0; i < elencoClienti.size();i++){
                if(elencoClienti.get(i).getUsername().equals(user) && elencoClienti.get(i).getPassword().equals(pass)){
                    trovato = true;
                    cambiaSchermata(this, new MenuCliente(elencoClienti, i));
                }
            }
            if(!trovato)
                JOptionPane.showMessageDialog(this, "Credenziali errate", "Errore", JOptionPane.ERROR_MESSAGE);
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
