package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import DAO.Ristorante;
import controller.Controller;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;

public class NuovaTavolata extends JFrame {
	private static final int width = 600;
	private static final int height = 600;
	private static final int x = Controller.screenWidth/2 - width/2;
	private static final int y = Controller.screenHeight/2 - height/2;

	private Ristorante ristorante;
	private String tavoloID;
	
	private JPanel contentPane;
	private JLabel numeroMaxAvventori;
	private int numeroPostiDisponibili = 0;
	private JTextField data;
	private JTextField nome;
	private JTextField cognome;
	private JTextField numeroDiTelefono;
	private JTextField codCartaIdentit‡;
	private ListaAvventoriPanel listaAvventoriPanel;
	private JScrollPane listaAvventoriScrollPane;

	/**
	 * Create the frame.
	 */
	public NuovaTavolata(Ristorante ristoranteScelto) {
		setResizable(false);
		setTitle("Inserimento di una nuova tavolata");
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Controller.backToClientelaFrame();
			}
		});
		setBounds(x, y, width, height);
		setAlwaysOnTop(true);
		contentPane =  new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ristorante = ristoranteScelto;
		
		JLabel titolo = new JLabel("Inserisci una nuova tavolata");
		titolo.setFont(new Font("Tahoma", Font.BOLD, 20));
		titolo.setBounds(10, 11, 287, 25);
		contentPane.add(titolo);
		
		JLabel tavoloTitle = new JLabel("Codice del tavolo:");
		tavoloTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tavoloTitle.setBounds(10, 47, 102, 16);
		contentPane.add(tavoloTitle);
		
		JComboBox<String> codiceTavolo = new JComboBox<String>();
		codiceTavolo.setModel(new DefaultComboBoxModel<String>(Controller.getTavoliOf(ristorante)));
		codiceTavolo.setSelectedIndex(-1);
		codiceTavolo.setToolTipText("");
		codiceTavolo.setMaximumRowCount(500);
		codiceTavolo.setBounds(10, 65, 151, 22);
		codiceTavolo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tavoloID = codiceTavolo.getItemAt(codiceTavolo.getSelectedIndex());
				numeroPostiDisponibili = Controller.tavoloDAO.get(tavoloID).getNumeroMassimoDiAvventori();
				numeroMaxAvventori.setText(numeroPostiDisponibili + " posti disponibili");
			}
		});
		contentPane.add(codiceTavolo);
		
		numeroMaxAvventori = new JLabel(numeroPostiDisponibili + " posti disponibili");
		numeroMaxAvventori.setFont(new Font("Tahoma", Font.PLAIN, 11));
		numeroMaxAvventori.setBounds(10, 91, 102, 14);
		contentPane.add(numeroMaxAvventori);
		
		JLabel dataTitle = new JLabel("Giorno di arrivo dei clienti:");
		dataTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dataTitle.setBounds(10, 140, 151, 14);
		contentPane.add(dataTitle);
		
		data = new JTextField();
		data.setBounds(10, 157, 151, 20);
		contentPane.add(data);
		data.setColumns(10);
		
		JLabel esempioData = new JLabel("Es: AAAA-MM-GG");
		esempioData.setForeground(Color.GRAY);
		esempioData.setBounds(10, 181, 102, 14);
		contentPane.add(esempioData);
		
		JLabel cameriereTitle = new JLabel("Servito da:");
		cameriereTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cameriereTitle.setBounds(10, 233, 63, 14);
		contentPane.add(cameriereTitle);
		
		JComboBox<String> cameriere = new JComboBox<String>();
		cameriere.setMaximumRowCount(50);
		cameriere.setModel(new DefaultComboBoxModel<String>(Controller.getCamerieriOf(ristorante)));
		cameriere.setSelectedIndex(-1);
		cameriere.setBounds(10, 249, 151, 22);
		contentPane.add(cameriere);
		
		InserisciAvventoriPanel();
		
		JButton inserisci = new JButton("Inserisci");
		inserisci.setBounds(485, 527, 89, 23);
		contentPane.add(inserisci);
		
		JButton annulla = new JButton("Annulla");
		annulla.setBounds(386, 527, 89, 23);
		contentPane.add(annulla);
	}
	
	private void InserisciAvventoriPanel() {
		JPanel avventorePanel = new JPanel();
		avventorePanel.setBackground(Color.WHITE);
		avventorePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		avventorePanel.setBounds(171, 47, 403, 469);
		contentPane.add(avventorePanel);
		avventorePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel inserisciAvventorePanel = new JPanel();
		inserisciAvventorePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		inserisciAvventorePanel.setBackground(SystemColor.menu);
		avventorePanel.add(inserisciAvventorePanel, BorderLayout.NORTH);
		GridBagLayout gbl_inserisciAvventorePanel = new GridBagLayout();
		gbl_inserisciAvventorePanel.columnWidths = new int[]{199, 199, 0};
		gbl_inserisciAvventorePanel.rowHeights = new int[]{20, 20, 20, 20, 20, 0, 0};
		gbl_inserisciAvventorePanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_inserisciAvventorePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		inserisciAvventorePanel.setLayout(gbl_inserisciAvventorePanel);
		
		JLabel avventoreTitle = new JLabel("Inserisci Avventore");
		avventoreTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_avventoreTitle = new GridBagConstraints();
		gbc_avventoreTitle.fill = GridBagConstraints.BOTH;
		gbc_avventoreTitle.insets = new Insets(0, 0, 5, 5);
		gbc_avventoreTitle.gridx = 0;
		gbc_avventoreTitle.gridy = 0;
		inserisciAvventorePanel.add(avventoreTitle, gbc_avventoreTitle);
		
		JLabel nomeTitle = new JLabel("Nome:");
		nomeTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_nomeTitle = new GridBagConstraints();
		gbc_nomeTitle.fill = GridBagConstraints.BOTH;
		gbc_nomeTitle.insets = new Insets(0, 0, 5, 5);
		gbc_nomeTitle.gridx = 0;
		gbc_nomeTitle.gridy = 1;
		inserisciAvventorePanel.add(nomeTitle, gbc_nomeTitle);
		
		JLabel cognomeTitle = new JLabel("Cognome:");
		cognomeTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_cognomeTitle = new GridBagConstraints();
		gbc_cognomeTitle.fill = GridBagConstraints.BOTH;
		gbc_cognomeTitle.insets = new Insets(0, 0, 5, 0);
		gbc_cognomeTitle.gridx = 1;
		gbc_cognomeTitle.gridy = 1;
		inserisciAvventorePanel.add(cognomeTitle, gbc_cognomeTitle);
		
		nome = new JTextField();
		GridBagConstraints gbc_nome = new GridBagConstraints();
		gbc_nome.fill = GridBagConstraints.BOTH;
		gbc_nome.insets = new Insets(0, 0, 5, 5);
		gbc_nome.gridx = 0;
		gbc_nome.gridy = 2;
		inserisciAvventorePanel.add(nome, gbc_nome);
		nome.setColumns(10);
		
		cognome = new JTextField();
		GridBagConstraints gbc_cognome = new GridBagConstraints();
		gbc_cognome.fill = GridBagConstraints.BOTH;
		gbc_cognome.insets = new Insets(0, 0, 5, 0);
		gbc_cognome.gridx = 1;
		gbc_cognome.gridy = 2;
		inserisciAvventorePanel.add(cognome, gbc_cognome);
		cognome.setColumns(10);
		
		JLabel numeroDiTelefonoTitle = new JLabel("Numero di Telefono:");
		numeroDiTelefonoTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_numeroDiTelefonoTitle = new GridBagConstraints();
		gbc_numeroDiTelefonoTitle.fill = GridBagConstraints.BOTH;
		gbc_numeroDiTelefonoTitle.insets = new Insets(0, 0, 5, 5);
		gbc_numeroDiTelefonoTitle.gridx = 0;
		gbc_numeroDiTelefonoTitle.gridy = 3;
		inserisciAvventorePanel.add(numeroDiTelefonoTitle, gbc_numeroDiTelefonoTitle);
		
		JLabel codCartaIdentit‡Title = new JLabel("Codice della Carta d'Identit\u00E0:");
		codCartaIdentit‡Title.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_codCartaIdentit‡Title = new GridBagConstraints();
		gbc_codCartaIdentit‡Title.fill = GridBagConstraints.BOTH;
		gbc_codCartaIdentit‡Title.insets = new Insets(0, 0, 5, 0);
		gbc_codCartaIdentit‡Title.gridx = 1;
		gbc_codCartaIdentit‡Title.gridy = 3;
		inserisciAvventorePanel.add(codCartaIdentit‡Title, gbc_codCartaIdentit‡Title);
		
		numeroDiTelefono = new JTextField();
		numeroDiTelefono.setText("+39");
		GridBagConstraints gbc_numeroDiTelefono = new GridBagConstraints();
		gbc_numeroDiTelefono.fill = GridBagConstraints.BOTH;
		gbc_numeroDiTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_numeroDiTelefono.gridx = 0;
		gbc_numeroDiTelefono.gridy = 4;
		inserisciAvventorePanel.add(numeroDiTelefono, gbc_numeroDiTelefono);
		numeroDiTelefono.setColumns(10);
		
		codCartaIdentit‡ = new JTextField();
		GridBagConstraints gbc_codCartaIdentit‡ = new GridBagConstraints();
		gbc_codCartaIdentit‡.insets = new Insets(0, 0, 5, 0);
		gbc_codCartaIdentit‡.fill = GridBagConstraints.BOTH;
		gbc_codCartaIdentit‡.gridx = 1;
		gbc_codCartaIdentit‡.gridy = 4;
		inserisciAvventorePanel.add(codCartaIdentit‡, gbc_codCartaIdentit‡);
		codCartaIdentit‡.setColumns(10);
		
		JButton aggiungi = new JButton("Aggiungi");
		aggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aggiungiAvventore();
			}
		});
		GridBagConstraints gbc_aggiungi = new GridBagConstraints();
		gbc_aggiungi.gridx = 1;
		gbc_aggiungi.gridy = 5;
		inserisciAvventorePanel.add(aggiungi, gbc_aggiungi);
		
		listaAvventoriPanel = new ListaAvventoriPanel();
		listaAvventoriScrollPane = new JScrollPane(listaAvventoriPanel);
		listaAvventoriScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		listaAvventoriScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		avventorePanel.add(listaAvventoriScrollPane, BorderLayout.CENTER);
	}
	
	private void aggiungiAvventore() {
		try {
			listaAvventoriPanel.addAvventore(codCartaIdentit‡.getText(), nome.getText(), cognome.getText(), numeroDiTelefono.getText());
			listaAvventoriScrollPane.setViewportView(listaAvventoriPanel);
		} catch (InformazioniVuoteException exc) {
			ErrorMessage error = new ErrorMessage(this, exc.getMessage());
			error.setVisible(true);
			exc.printStackTrace();
		}
	}
}
