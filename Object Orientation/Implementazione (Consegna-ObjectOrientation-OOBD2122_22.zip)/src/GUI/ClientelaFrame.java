 package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import DAO.Clientela;
import controller.Controller;
import java.awt.Dialog.ModalExclusionType;

public class ClientelaFrame extends JFrame {

	private static final int width = 800;
	private static final int height = 600;
	private static final int x = Controller.screenWidth/2 - width/2;
	private static final int y = Controller.screenHeight/2 - height/2;
	
	private JPanel contentPane;
	private JScrollPane westPanel;
	private JScrollPane centerPanel;

	/**
	 * Create the frame.
	 */
	public ClientelaFrame() {
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setTitle("Cronologia dei Clienti");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		westPanel = new JScrollPane(FillWestPanel());
		westPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		westPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(westPanel, BorderLayout.WEST);
		
		centerPanel = new JScrollPane(FillCenterPanel(""));
		centerPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		centerPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(centerPanel, BorderLayout.CENTER);
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
		for(String data : Controller.clientelaDAO.getAllDistinctDates()) {
			JLabel nomeData = new JLabel(data);
			nomeData.setBounds(10, 40+scrollHeight, 80, 15);
			nomeData.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
					centerPanel.setViewportView(FillCenterPanel("del " + data));
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
		
		setScrollRectangle(contentWestPane, 90, scrollHeight);
		
		return contentWestPane;
	}
	
	private JPanel FillCenterPanel(String data) {
		JPanel contentCenterPane = new JPanel();
		contentCenterPane.setBackground(Color.WHITE);
		contentCenterPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel titolo = new JLabel("Lista Avventori " + data);
		titolo.setBounds(10, 10, 500, 20);
		titolo.setFont(new Font("Tahoma", Font.BOLD, 22));
		contentCenterPane.add(titolo);
		
		setScrollRectangle(contentCenterPane, 500, 500);
		
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
