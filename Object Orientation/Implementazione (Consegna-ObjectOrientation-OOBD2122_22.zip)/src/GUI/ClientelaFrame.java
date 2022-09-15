 package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import DAO.Clientela;
import DAO.Ristorante;
import controller.Controller;
import java.awt.Dialog.ModalExclusionType;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientelaFrame extends JFrame {

	private static final int width = 800;
	private static final int height = 600;
	private static final int x = Controller.screenWidth/2 - width/2;
	private static final int y = Controller.screenHeight/2 - height/2;
	
	private JPanel contentPane;
	private JScrollPane westPanel;
	private JScrollPane centerPanel;
	
	private Ristorante ristorante;

	/**
	 * Create the frame.
	 */
	public ClientelaFrame(Ristorante ristoranteScelto) {
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setTitle("Cronologia dei Clienti");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ristorante = ristoranteScelto;
		
		westPanel = new JScrollPane(FillWestPanel());
		westPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		westPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(westPanel, BorderLayout.WEST);
			
		centerPanel = new JScrollPane(FillCenterPanel(null));
		centerPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		centerPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		southPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		southPanel.setBackground(Color.WHITE);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton addButton = new JButton("Aggiungi Tavolata");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.goToNuovaTavolata(ristorante);
			}
		});
		
		JButton closeButton = new JButton("Chiudi");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.backToRistoranteFrame();
			}
		});
		southPanel.add(closeButton);
		southPanel.add(addButton);
		
	}
	
	private JPanel FillWestPanel() {
		JPanel contentWestPane = new JPanel();
		contentWestPane.setBackground(Color.WHITE);
		contentWestPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel titolo = new JLabel("Date");
		titolo.setBounds(10, 10, 60, 20);
		titolo.setFont(new Font("Tahoma", Font.BOLD, 22));
		contentWestPane.add(titolo);
		
		int scrollHeight = 0;
		try {
			for(String data : Controller.clientelaDAO.getAllDistinctDatesOf(ristorante)) {
				JLabel nomeData = new JLabel(data);
				nomeData.setBounds(10, 40+scrollHeight, 80, 15);
				nomeData.setFont(new Font("Tahoma", Font.PLAIN, 13));
				contentWestPane.add(nomeData);
				
				JLabel selectionRectangle = new JLabel();
				selectionRectangle.setBounds(0, 35+scrollHeight, 200, 25);
				selectionRectangle.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						Controller.scaleImage(selectionRectangle, "rectangle selected.jpg");
					}
					
					@Override
					public void mouseReleased(MouseEvent e) {
						centerPanel.setViewportView(FillCenterPanel(data));
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						Controller.scaleImage(selectionRectangle, "selection rectangle.png");
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						selectionRectangle.setIcon(null);
					}
				});
				contentWestPane.add(selectionRectangle);
				
				scrollHeight+=20;
			}
		} catch (SQLException exc) {
			ErrorMessage error = new ErrorMessage(this, "Errore nell'estrarre la lista degli avventori dal database");
			error.setVisible(true);
			exc.printStackTrace();
		}
		
		setScrollRectangle(contentWestPane, 90, scrollHeight);
		
		return contentWestPane;
	}
	
	private JPanel FillCenterPanel(String data) {
		JPanel contentCenterPane = new JPanel();
		contentCenterPane.setBackground(Color.WHITE);
		contentCenterPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		int scrollHeight = 0;
		
		JLabel titolo = new JLabel("Lista Avventori " + (data==null ? "" : "del " + data));
		titolo.setBounds(10, 10, 500, 20);
		titolo.setFont(new Font("Tahoma", Font.BOLD, 22));
		contentCenterPane.add(titolo);
		scrollHeight+=40;
		
		try {
			for(String tavolo : Controller.clientelaDAO.getAllTavoliOf(ristorante, data)) {
				JLabel tavoloID = new JLabel("Tavolo numero " + tavolo);
				tavoloID.setBounds(0, scrollHeight, 5000, 20);
				tavoloID.setFont(new Font("Tahoma", Font.BOLD, 14));
				tavoloID.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				tavoloID.setOpaque(true);
				tavoloID.setBackground(new Color(230, 230, 230));
				contentCenterPane.add(tavoloID);
				scrollHeight+=20;
				
				JTextArea infoCameriere = new JTextArea();
				infoCameriere.setWrapStyleWord(true);
				infoCameriere.setLineWrap(true);
				infoCameriere.setEditable(false);
				infoCameriere.setBounds(0, scrollHeight, 5000, 55);
				infoCameriere.setText(Controller.getInfoOfCameriereOf(data, tavolo));
				infoCameriere.setFont(new Font("Tahoma", Font.PLAIN, 13));
				//infoCameriere.setBorder(new LineBorder(new Color(0, 0, 0)));
				infoCameriere.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				infoCameriere.setOpaque(true);
				infoCameriere.setBackground(new Color(230, 230, 230));
				contentCenterPane.add(infoCameriere);
				scrollHeight+=55;
				
				for(Clientela cliente : Controller.clientelaDAO.getAvventoriOf(data, tavolo)) {
					JTextArea infoCliente = new JTextArea();
					infoCliente.setWrapStyleWord(true);
					infoCliente.setLineWrap(true);
					infoCliente.setEditable(false);
					infoCliente.setBounds(0, scrollHeight, 5000, 35);
					infoCliente.setText(Controller.getInfoOf(cliente));
					infoCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
					infoCliente.setBorder(new LineBorder(new Color(0, 0, 0)));
					contentCenterPane.add(infoCliente);
					scrollHeight+=35;
				}
				
				//scrollHeight+=20;
			}
		} catch (SQLException exc) {
			ErrorMessage error = new ErrorMessage(this, "Errore nell'estrarre la lista degli avventori dal database");
			error.setVisible(true);
			exc.printStackTrace();
		}
		
		setScrollRectangle(contentCenterPane, 5000, scrollHeight);
		
		return contentCenterPane;
	}
	
	private void setScrollRectangle(JPanel panel, int width, int height) {
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, width, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, height, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
	}
}
