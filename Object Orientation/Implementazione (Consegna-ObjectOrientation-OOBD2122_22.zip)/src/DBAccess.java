import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.k33ptoo.components.KGradientPanel;

public class DBAccess extends Controller {

	private JPanel contentPane;
	private JTextField url;
	private JTextField username;
	private JPasswordField password;
	private JCheckBox ricordaPassword;
	private static final String DBinfoFilePath = System.getProperty("user.dir") + File.separator + "src\\saves\\DBinfo.txt";
	
	public DBAccess() {
		setTitle("Tracciamento Covid-19 per ristoranti");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 425);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		KGradientPanel gradientPanel = new KGradientPanel();
		gradientPanel.kBorderRadius = 0;
		gradientPanel.setBounds(0, 0, 246, 386);
		gradientPanel.kEndColor = Color.GRAY;
		gradientPanel.kStartColor = Color.BLACK;
		contentPane.add(gradientPanel);
		gradientPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 305, 224, 70);
		scaleImage(lblNewLabel, new ImageIcon(DBAccess.class.getResource("/images/PostegreSQL Logo.png")));
		gradientPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Using:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 279, 50, 15);
		gradientPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Accesso al Database");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(256, 11, 183, 22);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("URL:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(256, 59, 30, 22);
		contentPane.add(lblNewLabel_3);
		
		url = new JTextField();
		url.setText(getUrlFromFile());
		url.setBounds(256, 92, 291, 20);
		contentPane.add(url);
		url.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Username:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(256, 150, 66, 22);
		contentPane.add(lblNewLabel_4);
		
		username = new JTextField();
		username.setText(getUsernameFromFile());
		username.setBounds(256, 183, 291, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Password:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(256, 244, 66, 22);
		contentPane.add(lblNewLabel_5);
		
		password = new JPasswordField();
		password.setText(getPasswordFromFile());
		password.setBounds(256, 277, 291, 20);
		contentPane.add(password);
		
		ricordaPassword = new JCheckBox("Ricorda password");
		ricordaPassword.setSelected(true);
		ricordaPassword.setBackground(Color.WHITE);
		ricordaPassword.setBounds(252, 304, 187, 23);
		contentPane.add(ricordaPassword);
		
		JButton btnNewButton = new JButton("Next");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});
		btnNewButton.setBounds(458, 340, 89, 23);
		contentPane.add(btnNewButton);
	}
	
	private void scaleImage(JLabel label, ImageIcon icon) {
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		label.setIcon(scaledIcon);
	}
	
	private void saveAllIntoFile() throws IOException {
		BufferedWriter DBinfo = new BufferedWriter( new FileWriter(DBinfoFilePath) );
		DBinfo.write(url.getText() + "\n");
		DBinfo.write(username.getText() + "\n");
		DBinfo.write(String.valueOf(password.getPassword()) + "\n");
		DBinfo.close();
	}
	
	private String getUrlFromFile() {
		try {
			BufferedReader DBinfo = new BufferedReader( new FileReader(DBinfoFilePath) );
			String ret = DBinfo.readLine();
			DBinfo.close();
			return ret;
		} catch (IOException e) {
			return "jdbc:postgresql://localhost:5432/<NOME_DATABASE>";
		}
	}
	
	private String getUsernameFromFile() {
		try {
			BufferedReader DBinfo = new BufferedReader( new FileReader(DBinfoFilePath) );
			String ret = DBinfo.readLine();
			ret = DBinfo.readLine();
			DBinfo.close();
			return ret;
		} catch (IOException e) {
			return null;
		}
	}
	
	private String getPasswordFromFile() {
		try {
			BufferedReader DBinfo = new BufferedReader( new FileReader(DBinfoFilePath) );
			String ret = DBinfo.readLine();
			ret = DBinfo.readLine();
			ret = DBinfo.readLine();
			DBinfo.close();
			return ret;
		} catch (IOException e) {
			return null;
		}
	}
	
	private void next() {
		try {
			DBConnector.getIstance(url.getText(), username.getText(), String.valueOf(password.getPassword()));
			if(ricordaPassword.isSelected())
				saveAllIntoFile();
			setVisible(false);
			home = new Home();
			home.setVisible(true);
		} catch(ClassNotFoundException exc) {
			ErrorMessage error = new ErrorMessage(this, "Error in connecting to PostgreSQL server");
			error.setVisible(true);
			exc.printStackTrace();
		} catch(SQLException exc) {
			ErrorMessage error = new ErrorMessage(this, "Error in connecting to the PostgreSQL Database");
			error.setVisible(true);
			exc.printStackTrace();
		} catch(IOException exc) {
			ErrorMessage error = new ErrorMessage(this, "Error in saving the password");
			error.setVisible(true);
			exc.printStackTrace();
		}
	}
	
}
