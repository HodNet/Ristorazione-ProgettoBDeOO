package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.Ristorante;
import controller.Controller;

public class NuovaTavolata extends JFrame {
	private static final int width = 600;
	private static final int height = 400;
	private static final int x = Controller.screenWidth/2 - width/2;
	private static final int y = Controller.screenHeight/2 - height/2;

	private JPanel contentPane;
	
	private Ristorante ristorante;

	/**
	 * Create the frame.
	 */
	public NuovaTavolata(Ristorante ristoranteScelto) {
		setTitle("Inserimento di una nuova tavolata");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(x, y, width, height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ristorante = ristoranteScelto;
	}

}
