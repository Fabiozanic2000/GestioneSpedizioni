package grafica.cliente;

import grafica.MyFrame;
import grafica.SchermataIniziale;
import dati.archivi.Archivio;
import dati.clienti.Cliente;
import dati.spedizioni.Spedizione;
import dati.spedizioni.SpedizioneNormale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata di inserimento di una spedizione normale
 *
 * @author Fabio Zanichelli
 */
public class InserimentoSpedizione extends MyFrame implements ActionListener, WindowListener {
    private final JTextField destinazione = new JTextField("", 25);
    private final JTextField peso = new JTextField("", 4);
    private final JButton pulsanteInserimento = new JButton("Inserire spedizione normale");
    private final JButton pulsanteHome = new JButton("Logout");
    private final JButton pulsanteIndietro = new JButton("Indietro");
    private final Archivio<Cliente> elencoClienti;
    private final int indiceCliente;

    /**
     * Metodo costruttore che assegna l'elenco dei clienti e l'indice del cliente loggato agli attributi della classe,
     * e implementa la schermata di inserimento della spedizione normale.
     * @param elencoClienti L'elenco dei clienti
     * @param indiceCliente L'indice del cliente loggato
     */
    public InserimentoSpedizione(Archivio<Cliente> elencoClienti, int indiceCliente){
        super("Inserimento spedizione");
        this.elencoClienti = elencoClienti;
        this.indiceCliente = indiceCliente;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro= new JPanel();
        JPanel pannelloSud = new JPanel();

        //Pannello Nord
        pannelloNord.add(new JLabel("Inserire TUTTI i campi richiesti"));

        //Pannello Centro
        pannelloCentro.setLayout(new GridLayout(2,2));
        pannelloCentro.add(new JLabel("Inserire la destinazione", SwingConstants.CENTER));
        pannelloCentro.add(destinazione);
        pannelloCentro.add(new JLabel("Inserire il peso", SwingConstants.CENTER));
        pannelloCentro.add(peso);

        //Pannello Sud
        pulsanteHome.setBackground(Color.RED);
        pulsanteInserimento.setBackground(Color.GREEN);
        pannelloSud.add(pulsanteHome);
        pannelloSud.add(pulsanteIndietro);
        pannelloSud.add(pulsanteInserimento);


        //Ascoltatori
        pulsanteIndietro.addActionListener(this);
        pulsanteHome.addActionListener(this);
        pulsanteInserimento.addActionListener(this);

        //Aggiunta dei pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

    }

    /**
     * Metodo che viene invocato alla pressione di un pulsante da parte dell'utente ed esegue diverse istruzioni in base
     * al pulsante premuto.
     * Se viene premuto "Indietro" la schermata verrà cambiata a quella precedente.
     * Se viene premuto "Logout" verrà effettuato il logout (ritorno alla schermata principale)
     * Se viene premuto "Inserire spedizione normale" verranno effettuati dei controlli sui dati inseriti dall'utente
     * e, in caso di errori, verranno mostrati messaggi all'utente; in caso contrario, la spedizione verrà inserita nell'elenco.
     * @param e L'ActionEvento
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Logout")){
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));
        }

        if(e.getActionCommand().equals("Indietro"))
            cambiaSchermata(this, new MenuCliente(elencoClienti, indiceCliente));

        if(e.getActionCommand().equals("Inserire spedizione normale")){
            String d, p;
            int pesoNumero;
            d = destinazione.getText();
            p = peso.getText();

            if(d.equals("") || p.equals("")){
                JOptionPane.showMessageDialog(this, "Immettere TUTTI i dati richiesti!",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                pesoNumero = Integer.parseInt(p);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Inserire il peso in un formato corretto!",
                        "Errore", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(pesoNumero <= 0){
                JOptionPane.showMessageDialog(this, "Inserire il peso in un formato corretto!",
                        "Errore", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(d.length() < 3){
                JOptionPane.showMessageDialog(this, "La destinazione deve avere almeno 3 caratteri!",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente c = elencoClienti.get(indiceCliente);
            Spedizione buff = new SpedizioneNormale(d, pesoNumero, c);

            c.getElencoSpedizioni().aggiungi(buff);
            JOptionPane.showMessageDialog(this, "Spedizione registrata!",
                    "Spedizione registrata", JOptionPane.INFORMATION_MESSAGE);
            cambiaSchermata(this, new MenuCliente(elencoClienti, indiceCliente));
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
