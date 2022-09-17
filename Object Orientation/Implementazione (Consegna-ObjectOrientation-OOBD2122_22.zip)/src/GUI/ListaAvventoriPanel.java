package GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import controller.Controller;

public class ListaAvventoriPanel extends JPanel {
	private LinkedList<JTextArea> avventori;
	private LinkedList<JLabel> cestini;
	private int scrollHeight = 10;

	public ListaAvventoriPanel() {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setLayout(null);
		avventori = new LinkedList<JTextArea>();
		cestini = new LinkedList<JLabel>();
	}
	
	public void addAvventore(String codCartaIdentità, String nome, String cognome, String numeroDiTelefono) throws InformazioniVuoteException {
		if(codCartaIdentità.length()!=9 && codCartaIdentità.length()!=10)
			throw new InformazioniVuoteException("Il codice della carta d'identità deve contenere 9 o 10 caratteri");
		if(nome.isBlank())
			throw new InformazioniVuoteException("Il nome non può essere vuoto. Inserire qualcosa");
		if(cognome.isBlank())
			throw new InformazioniVuoteException("Il cognome non può essere vuoto. Inserire qualcosa");
		if(numeroDiTelefono.isBlank())
			throw new InformazioniVuoteException("Il numero di telefono non può essere vuoto. Inserire qualcosa");
		
		JLabel trashIcon = new JLabel("");
		trashIcon.setBounds(376, scrollHeight, 15, 15);
		Controller.scaleImage(trashIcon, "trash icon.png");
		add(trashIcon);
		cestini.add(trashIcon);
		
		String infoAvventore = nome + " " + cognome + ", " + numeroDiTelefono + "\ncodice della Carta d'Identità: " + codCartaIdentità;
		JTextArea avventore = new JTextArea();
		avventore.setWrapStyleWord(true);
		avventore.setLineWrap(true);
		avventore.setEditable(false);
		avventore.setBounds(10, scrollHeight, 366, 35);
		avventore.setText(infoAvventore);
		avventore.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(avventore);
		avventori.add(avventore);
		scrollHeight+=40;
	}
}
