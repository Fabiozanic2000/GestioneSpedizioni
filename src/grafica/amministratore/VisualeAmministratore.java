package grafica.amministratore;

import grafica.ColoraCelle;
import grafica.MyFrame;
import grafica.SchermataIniziale;
import dati.archivi.Archivio;
import dati.clienti.Cliente;
import dati.spedizioni.Spedizione;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe che implementa la schermata con la vista dell'amministratore
 *
 * @author Fabio Zanichelli
 */
public class VisualeAmministratore extends MyFrame implements ActionListener, WindowListener {
    private final JButton pulsanteEliminazione = new JButton("ELIMINA");
    private final JTextField spedizioneDaEliminare = new JTextField("", 5);
    private final JButton pulsanteInfo;
    private final JComboBox<String> combo;
    private final JTable tabella;
    private final AmministratoreTableModel t;

    /**
     * L'elenco dei clienti
     */
    private Archivio<Cliente> elencoClienti;

    /**
     * Stringa che contiene il messaggio di guida per l'utente
     */
    private String guida = "<html><center>Benvenuto nella schermata amministratore!<br/>" +
            "Da qui puoi vedere le spedizioni di tutti i clienti e modificarne lo stato; per farlo ti " +
            "basta cliccare sulla casella \"stato\" della spedizione corrispondente.<br/>" +
            "Ti ricordo che spedizioni non assicurate non possono avere stati relativi al rimborso, " +
            "ma non preoccuparti! Il sistema vieterà che questo accada notificandoti l'errore.<br/>" +
            "NON puoi modificare spedizioni nel loro stato finale (\"ricevuta\" o \"rimborsata\"), ma puoi eliminarle dal " +
            "sistema;<br/>è sufficiente inserire il numero della spedizione (prima colonna) nel box di testo in basso e " +
            "premere ELIMINA!<br/>" +
            "Buon lavoro!</html>";


    /**
     * Costruttore che assegna l'elenco dei clienti all'attributo della classe
     * @param elencoClienti L'elenco dei clienti
     */
    public VisualeAmministratore(Archivio<Cliente> elencoClienti){
        super("Elenco amministratore");
        this.elencoClienti = elencoClienti;
        this.setLayout(new BorderLayout());
        JPanel pannelloNord = new JPanel();
        JPanel pannelloCentro = new JPanel();
        JPanel pannelloSud = new JPanel();

        //Tabella
        t = new AmministratoreTableModel(this.elencoClienti);
        tabella = new JTable(t);
        combo = new JComboBox<>();
        combo.addItem("In preparazione");
        combo.addItem("In transito");
        combo.addItem("Ricevuta");
        combo.addItem("Fallita");
        combo.addItem("Richiesta rimborso");
        combo.addItem("Rimborsata");
        TableColumn col = tabella.getColumnModel().getColumn(5);
        col.setCellEditor(new DefaultCellEditor(combo));
        tabella.setDefaultRenderer(Object.class,new ColoraCelle());
        JScrollPane scrollPane = new JScrollPane(tabella);
        combo.addActionListener(this);


        //Pannello Nord
        pulsanteInfo = new JButton("Guida");
        pulsanteInfo.setBackground(Color.GREEN);
        pannelloNord.add(new JLabel("Elenco spedizioni di tutti i clienti"));
        pannelloNord.add(pulsanteInfo);

        //Pannello Centro
        pannelloCentro.add(scrollPane);

        //Pannello Sud
        pannelloSud.setLayout(new FlowLayout());
        JButton pulsanteLogout = new JButton("Logout");
        pulsanteLogout.setBackground(Color.RED);
        pannelloSud.add(spedizioneDaEliminare);
        pannelloSud.add(pulsanteEliminazione);
        pannelloSud.add(pulsanteLogout, FlowLayout.RIGHT);

        //Ascoltatori
        pulsanteInfo.addActionListener(this);
        combo.addActionListener(this);
        pulsanteEliminazione.addActionListener(this);
        pulsanteLogout.addActionListener(this);

        //Aggiunta pannelli al frame
        this.add(pannelloNord, BorderLayout.NORTH);
        this.add(pannelloCentro, BorderLayout.CENTER);
        this.add(pannelloSud, BorderLayout.SOUTH);

        rendiVisibile(this);


    }
    /**
     * Classe che implementa le azioni da eseguire in base ai pulsanti premuti dall'utente.
     * Se questo preme "Logout" verrà eseguito il logout (ritorno alla schermata principale)
     * Se viene premuto "Elimina" verrà eliminata (se possibile) la spedizione corrispondente
     * alla riga della tabella immessa dal cliente
     * @param e L'ActionEvent
     */
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand().equals("Logout")) {
            cambiaSchermata(this, new SchermataIniziale(elencoClienti));
        }

        if(e.getActionCommand().equals("ELIMINA")){
            int cl = 0, somma = 0, totale = 0;
            int rowIndex;
            try {
                rowIndex = Integer.parseInt(spedizioneDaEliminare.getText());
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(this, "Formato numero non valido!",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(rowIndex <= 0 || rowIndex > t.getRowCount()) {
                if(t.getRowCount() == 0){
                    JOptionPane.showMessageDialog(this, "Nessuna spedizione presente!",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(this, "Numero non valido!\nDeve essere compreso tra 1 e "
                        + t.getRowCount(), "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            rowIndex--;
            while(totale < rowIndex + 1){
                totale += elencoClienti.get(cl).getElencoSpedizioni().size();
                if(totale < rowIndex+1){
                    somma += elencoClienti.get(cl).getElencoSpedizioni().size();
                    cl++;
                }
            }
            int indiceSpedizione = rowIndex - somma;
            elencoClienti.get(cl);
            Cliente c;
            c = elencoClienti.get(cl);
            Spedizione s = c.getElencoSpedizioni().get(indiceSpedizione);
            if (!(s.getStatoSpedizione().equals("Ricevuta") || s.getStatoSpedizione().equals("Rimborsata"))){
                JOptionPane.showMessageDialog(this, "Spedizione NON eliminabile!",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            c.getElencoSpedizioni().cancella(indiceSpedizione);
            t.aggiorna();
        }

        if(e.getActionCommand().equals("Guida"))
            JOptionPane.showMessageDialog(this, guida, "Guida", JOptionPane.PLAIN_MESSAGE);
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

