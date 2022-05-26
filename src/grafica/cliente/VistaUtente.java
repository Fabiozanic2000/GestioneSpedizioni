package grafica.cliente;

import grafica.ColoraCelle;
import grafica.MyFrame;
import grafica.SchermataIniziale;
import dati.archivi.Archivio;
import dati.archivi.ArchivioConEliminazione;
import dati.clienti.Cliente;
import dati.spedizioni.Spedizione;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata in cui il cliente può visualizzare le sue spedizioni
 *
 * @author Fabio Zanichelli
 */
public class VistaUtente extends MyFrame implements ActionListener, WindowListener {
    private final JButton pulsanteMenu = new JButton("Torna al menu");
    private final JButton pulsanteLogout = new JButton("Logout");
    private final JButton pulsanteGuida = new JButton("Guida");
    JComboBox<String> combo;

    /**
     * L'elenco dei clienti
     */
    private final Archivio<Cliente> elencoClienti;

    /**
     * L'indice del cliente loggato
     */
    private final int indiceCliente;

    /**
     * Stringa di guida per l'utente.
     */
    private final String guida = "<html><center>Benvenuto nella schermata utente!<br/>Da qui puoi vedere tutte le tue spedizioni e" +
            " il loro stato, ma non è tutto!<br/>Se una tua spedizione è assicurata (ovvero ha un valore assicurato) ed " +
            "è fallita, puoi richiederne il rimborso;<br/>Per farlo basta cliccare sulla casella dello stato della spedizione " +
            "corrispondente e selezionare \"Richiesta rimborso\".<br/>Buon lavoro!</html>";

    /**
     * Metodo costruttore, assegna i due parametri in ingresso agli attributi della classe e implementa la schermata
     * con le spedizioni del cliente
     * @param elencoClienti L'elenco dei clienti
     * @param indiceCliente L'indice del cliente loggati
     */
    public VistaUtente(Archivio<Cliente> elencoClienti, int indiceCliente){
        super("Elenco utente");
        ArchivioConEliminazione<Spedizione> as = elencoClienti.get(indiceCliente).getElencoSpedizioni();
        this.elencoClienti = elencoClienti;
        this.indiceCliente = indiceCliente;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();
        JPanel pannelloSud = new JPanel();

        //Tabella
        TableModel t = new UtenteTableModel(as);
        JTable tabella = new JTable(t);
        combo = new JComboBox<>();
        combo.addItem("Fallita");
        combo.addItem("Richiesta rimborso");
        TableColumn col = tabella.getColumnModel().getColumn(5);
        combo.addActionListener(this);
        col.setCellEditor(new DefaultCellEditor(combo));
        tabella.setDefaultRenderer(Object.class, new ColoraCelle());
        JScrollPane scrollPane = new JScrollPane(tabella);

        //Pannello Nord
        pulsanteGuida.setBackground(Color.GREEN);
        pannelloNord.add(new JLabel("Elenco spedizioni"));
        pannelloNord.add(pulsanteGuida);

        //Pannello Centro
        pannelloCentro.add(scrollPane);

        //Pannello Sud
        pulsanteLogout.setBackground(Color.RED);
        pannelloSud.add(pulsanteLogout);
        pannelloSud.add(pulsanteMenu);

        //Ascoltatori
        pulsanteGuida.addActionListener(this);
        pulsanteLogout.addActionListener(this);
        pulsanteMenu.addActionListener(this);

        //Aggiunta dei pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);
    }

    /**
     * Metodo invocato al premere di un pulsante o di una azione sulle JComboBox, le istruzioni eseguite cambiano
     * in base al pulsante premuto.
     * Se viene premuto "Logout" verrà effettuato il logout del cliente (ritorno alla schermata home).
     * Se viene premuto "Torna al menu" verrà cambiata la schermata al menu del cliente.
     * Se viene premuto "Guida" verrà mostrato un messaggio che da spiegazioni all'utente su come interagire con la
     * schermata
     * Se l'evento è associato ad una JComboBox, viene cambiato il suo Item selezionato.
     * @param e L'ActionEvent
     */
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Logout"))
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));

        if(e.getActionCommand().equals("Torna al menu"))
            cambiaSchermata(this, new MenuCliente(elencoClienti, indiceCliente));

        if(e.getSource() == combo)
            combo.setSelectedItem(combo.getSelectedItem());

        if(e.getActionCommand().equals("Guida")){
            JOptionPane.showMessageDialog(this, guida, "Guida", JOptionPane.PLAIN_MESSAGE);
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