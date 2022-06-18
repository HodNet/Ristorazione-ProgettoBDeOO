import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.awt.FlowLayout;

public class Home extends Controller {

	private JPanel contentPane;

	public Home() {
		try {
			ristoranteDAO = new RistoranteDAO(DBConnector.getConnection());
		} catch(SQLException exc) {
			ErrorMessage error = new ErrorMessage(this, "Errore nel cercare di estrarre i dati dal database");
			error.setVisible(true);
			exc.printStackTrace();
		}
		
		setTitle("Tracciamento Covid-19 per ristoranti");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 386);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Sistema di tracciamento COVID-19 per ristoranti");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblNewLabel);
	}

}