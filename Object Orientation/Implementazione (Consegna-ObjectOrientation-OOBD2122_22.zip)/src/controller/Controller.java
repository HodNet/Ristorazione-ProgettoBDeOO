package controller;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import DAO.Ristorante;
import DAO.RistoranteDAO;
import GUI.DBLogin;
import GUI.ErrorMessage;
import GUI.Home;
import GUI.RistoranteFrame;
import database.DBConnector;

public class Controller {
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWidth = (int) screenSize.getWidth();
	public static final int screenHeight = (int) screenSize.getHeight();
	
	private static DBLogin DBlogin;
	public static Home home;
	public static RistoranteFrame ristoranteFrame;
	
	public static RistoranteDAO ristoranteDAO;
	
	public static void main(String[] args) {
		DBlogin = new DBLogin();
		DBlogin.setVisible(true);
	}
	
	/*
	 * General Purpose Functions:
	 * 
	 */
	public static void scaleImage(JLabel label, String file_name) {
		ImageIcon icon = new ImageIcon(Controller.class.getResource("/images/" + file_name));
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		label.setIcon(scaledIcon);
	}
	
	public static void scaleImage(JButton label, String file_name) {
		ImageIcon icon = new ImageIcon(Controller.class.getResource("/images/" + file_name));
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(label.getWidth()-10, label.getHeight()-10, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		label.setIcon(scaledIcon);
	}
	
	/*
	 * DBLogin functions:
	 * 
	 */
	public static void saveAllIntoFile() throws IOException {
		BufferedWriter DBinfo = new BufferedWriter( new FileWriter(DBLogin.DBinfoFilePath) );
		DBinfo.write(DBlogin.getUrl() + "\n");
		DBinfo.write(DBlogin.getUsername() + "\n");
		DBinfo.write(DBlogin.getPassword() + "\n");
		DBinfo.close();
	}
	
	public static String getUrlFromFile() {
		try {
			BufferedReader DBinfo = new BufferedReader( new FileReader(DBLogin.DBinfoFilePath) );
			String ret = DBinfo.readLine();
			DBinfo.close();
			return ret;
		} catch (IOException e) {
			return "jdbc:postgresql://localhost:5432/<NOME_DATABASE>";
		}
	}
	
	public static String getUsernameFromFile() {
		try {
			BufferedReader DBinfo = new BufferedReader( new FileReader(DBLogin.DBinfoFilePath) );
			String ret = DBinfo.readLine();
			ret = DBinfo.readLine();
			DBinfo.close();
			return ret;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String getPasswordFromFile() {
		try {
			BufferedReader DBinfo = new BufferedReader( new FileReader(DBLogin.DBinfoFilePath) );
			String ret = DBinfo.readLine();
			ret = DBinfo.readLine();
			ret = DBinfo.readLine();
			DBinfo.close();
			return ret;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static void checkDataBase() {
		try {
			DBConnector.getIstance(DBlogin.getUrl(), DBlogin.getUsername(), DBlogin.getPassword());
			if(DBlogin.ricordaPasswordIsSelected())
				saveAllIntoFile();
			try {
				ristoranteDAO = new RistoranteDAO(DBConnector.getConnection());
			} catch(SQLException exc) {
				ErrorMessage error = new ErrorMessage(DBlogin, "Errore nel cercare di estrarre i dati dal database");
				error.setVisible(true);
				exc.printStackTrace();
			}
			home = new Home();
			DBlogin.setVisible(false);
			home.setVisible(true);
		} catch(ClassNotFoundException exc) {
			ErrorMessage error = new ErrorMessage(DBlogin, "Error in connecting to PostgreSQL server");
			error.setVisible(true);
			exc.printStackTrace();
		} catch(SQLException exc) {
			ErrorMessage error = new ErrorMessage(DBlogin, "Error in connecting to the PostgreSQL Database");
			error.setVisible(true);
			exc.printStackTrace();
		} catch(IOException exc) {
			ErrorMessage error = new ErrorMessage(DBlogin, "Error in saving the password");
			error.setVisible(true);
			exc.printStackTrace();
		}
	}
	
	/*
	 * Home functions:
	 * 
	 */
	public static void goToRistoranteFrame(Ristorante ristoranteScelto) {
		ristoranteFrame = new RistoranteFrame(ristoranteScelto);
		home.setVisible(false);
		ristoranteFrame.setVisible(true);
	}
	
	/*
	 * RistoranteFrame functions:
	 * 
	 */
	public static void backToHome() {
		ristoranteFrame.setVisible(false);
		ristoranteFrame = null;
		home.setVisible(true);
		
	}
}
