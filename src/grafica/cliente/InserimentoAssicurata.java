package grafica.cliente;

import grafica.MyFrame;
import grafica.SchermataIniziale;
import dati.archivi.Archivio;
import dati.clienti.Cliente;
import dati.spedizioni.SpedizioneAssicurata;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata di inserimento di una spedizione assicurata
 *
 * @author Fabio Zanichelli
 */
public class InserimentoAssicurata extends MyFrame implements ActionListener, WindowListener {
    private final JTextField destinazione = new JTextField("", 25);
    private final JTextField peso = new JTextField("", 4);
    private final JTextField valoreAssicurato = new JTextField("", 4);
    private final JButton pulsanteInserimento = new JButton("Inserire spedizione assicurata");
    private final JButton pulsanteLogout = new JButton("Logout");
    private final JButton pulsanteIndietro = new JButton("Indietro");
    private final Archivio<Cliente> elencoClienti;
    private final int indiceCliente;

    /**
     * Metodo costruttore che assegna l'elenco dei clienti e l'indice del cliente loggato agli attributi della classe,
     * e implementa la schermata di inserimento della spedizione assicurata.
     * @param elencoClienti L'elenco dei clienti
     * @param indiceCliente L'indice del cliente loggato
     */
    public InserimentoAssicurata(Archivio<Cliente> elencoClienti, int indiceCliente){
        super("Inserimento spedizione");
        this.indiceCliente = indiceCliente;
        this.elencoClienti = elencoClienti;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro= new JPanel();
        JPanel pannelloSud = new JPanel();

        //Pannello Nord
        pannelloNord.add(new JLabel("Inserire TUTTI i campi richiesti"));

        //Pannello Centro
        pannelloCentro.setLayout(new GridLayout(3,2));
        pannelloCentro.add(new JLabel("Inserire la destinazione", SwingConstants.CENTER));
        pannelloCentro.add(destinazione);
        pannelloCentro.add(new JLabel("Inserire il peso", SwingConstants.CENTER));
        pannelloCentro.add(peso);
        pannelloCentro.add(new JLabel("Inserire il valore assicurato", SwingConstants.CENTER));
        pannelloCentro.add(valoreAssicurato);

        //Pannello Sud
        pulsanteLogout.setBackground(Color.RED);
        pulsanteInserimento.setBackground(Color.GREEN);
        pannelloSud.add(pulsanteLogout);
        pannelloSud.add(pulsanteIndietro);
        pannelloSud.add(pulsanteInserimento);

        //Ascoltatori
        pulsanteIndietro.addActionListener(this);
        pulsanteLogout.addActionListener(this);
        pulsanteInserimento.addActionListener(this);

        //Aggiunta dei pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

        rendiVisibile(this);
    }

    /**
     * Metodo che viene invocato alla pressione di un pulsante da parte dell'utente ed esegue diverse istruzioni in base
     * al pulsante premuto.
     * Se viene premuto "Indietro" la schermata verrà cambiata a quella precedente.
     * Se viene premuto "Logout" verrà effettuato il logout (ritorno alla schermata principale)
     * Se viene premuto "Inserire spedizione assicurata" verranno effettuati dei controlli sui dati inseriti dall'utente
     * e, in caso di errori, verranno mostrati messaggi all'utente; in caso contrario, la spedizione verrà inserita nell'elenco.
     * @param e L'ActionEvento
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Logout")){
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));
            this.dispose();
        }
        if(e.getActionCommand().equals("Inserire spedizione assicurata")){
            String d, p, va;
            int pesoNumero, vaNumero;

            d = destinazione.getText();
            p = peso.getText();
            va = valoreAssicurato.getText();

            if(d.equals("") || p.equals("") || va.equals("")){
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

            try{
                vaNumero = Integer.parseInt(va);
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Inserire il valore assicurato in un formato corretto!",
                        "Errore", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(pesoNumero <= 0 || vaNumero <= 0){
                JOptionPane.showMessageDialog(this, "Inserire solo numeri positivi",
                        "Errore", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if(d.length() < 3){
                JOptionPane.showMessageDialog(this, "La destinazione deve avere almeno 3 caratteri!",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }


            Cliente c = elencoClienti.get(indiceCliente);
            SpedizioneAssicurata sp = new SpedizioneAssicurata(d, pesoNumero, vaNumero, c);

            c.getElencoSpedizioni().aggiungi(sp);
            JOptionPane.showMessageDialog(this, "Spedizione assicurata inserita correttamente!",
                    "Spedizione aggiunta", JOptionPane.INFORMATION_MESSAGE);
            cambiaSchermata(this, new MenuCliente(elencoClienti, indiceCliente));
        }

        if(e.getActionCommand().equals("Indietro"))
            cambiaSchermata(this, new MenuCliente(elencoClienti, indiceCliente));
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

