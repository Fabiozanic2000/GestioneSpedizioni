package grafica.cliente;

import dati.archivi.Archivio;
import dati.clienti.Cliente;
import grafica.MyFrame;
import grafica.SchermataIniziale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata con il menu dell'utente
 *
 * @author Fabio Zanichelli
 */
public class MenuCliente extends MyFrame implements ActionListener, WindowListener {
    private final JButton pulsanteSpedizione = new JButton("Inserire una spedizione normale");
    private final JButton pulsanteAssicurata = new JButton("Inserire una spedizione assicurata");
    private final JButton pulsanteVisualizza = new JButton("Visualizza le tue spedizioni");
    private final JButton pulsanteHome = new JButton("Logout");
    private final Archivio<Cliente> elencoClienti;
    private final int indiceCliente;

    /**
     * Metodo costruttore, assegna l'elenco dei clienti e l'indice del cliente loggato agli attributi della classe e
     * implementa la schermata con il menu
     * @param elencoClienti L'elenco dei clienti
     * @param indiceCliente L'indice del cliente loggato
     */
    public MenuCliente(Archivio<Cliente> elencoClienti, int indiceCliente){
        super("Menu cliente");
        this.indiceCliente = indiceCliente;
        this.elencoClienti = elencoClienti;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();

        //Pannello Nord
        pannelloNord.setLayout(new GridLayout(2,1 ));
        pannelloNord.add(new JLabel("Benvenuto " + elencoClienti.get(indiceCliente).getUsername() + "! " +
                "Per procedere seleziona una voce nel menu:", SwingConstants.CENTER));
        JLabel quanteSpedizioni = new JLabel("Hai registrato " + elencoClienti.get(indiceCliente).getElencoSpedizioni().size() +
                " spedizioni", SwingConstants.CENTER);
        quanteSpedizioni.setFont(new Font("FontSpedizioni", Font.ITALIC, 12));
        pannelloNord.add(quanteSpedizioni);

        //Pannello Centro
        pannelloCentro.setLayout(new GridLayout(2,2));
        pannelloCentro.add(pulsanteSpedizione);
        pannelloCentro.add(pulsanteAssicurata);
        pannelloCentro.add(pulsanteVisualizza);
        pulsanteHome.setBackground(Color.RED);
        pannelloCentro.add(pulsanteHome);

        //Ascoltatori
        pulsanteSpedizione.addActionListener(this);
        pulsanteAssicurata.addActionListener(this);
        pulsanteVisualizza.addActionListener(this);
        pulsanteHome.addActionListener(this);

        //Aggiunta dei pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);

    }

    /**
     * Metodo invocato alla pressione di un pulsante da parte dell'utente; cambia schermata in base al pulsante premuto
     * @param e L'ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Logout")){
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));
        }
        if(e.getActionCommand().equals("Inserire una spedizione normale")){
            cambiaSchermata(this, new InserimentoSpedizione(elencoClienti, indiceCliente));
        }
        if(e.getActionCommand().equals("Inserire una spedizione assicurata")){
            cambiaSchermata(this, new InserimentoAssicurata(elencoClienti, indiceCliente));
        }
        if(e.getActionCommand().equals("Visualizza le tue spedizioni")){
            cambiaSchermata(this, new VistaUtente(elencoClienti, indiceCliente));
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