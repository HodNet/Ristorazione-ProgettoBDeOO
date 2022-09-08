package controller;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import DAO.Ristorante;
import DAO.RistoranteDAO;
import DAO.Sala;
import DAO.SalaDAO;
import GUI.DBLogin;
import GUI.ErrorMessage;
import GUI.Home;
import GUI.RistoranteFrame;
import database.DBConnector;

public class Controller {
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int screenWidth = (int) screenSize.getWidth();
	public static final int screenHeight = (int) screenSize.getHeight();
	
	public static final String queryStatisticheAvventoriGiornalieri = 
			  "SELECT codR, EXTRACT(DAY FROM dataDiArrivo) AS giorno, EXTRACT(MONTH FROM dataDiArrivo) AS mese, EXTRACT(YEAR FROM dataDiArrivo) AS anno,  dataDiArrivo, SUM(n°avventori) AS avventori_tot "
			+ "FROM Ristorante NATURAL JOIN Sala NATURAL JOIN Tavolo NATURAL JOIN Tavolata "
			+ "GROUP BY codR, dataDiArrivo ";
	public static final String queryStatisticheAvventoriMensili = 
			  "SELECT codR, EXTRACT(YEAR FROM dataDiArrivo) AS anno, EXTRACT(MONTH FROM dataDiArrivo) AS mese, SUM(n°avventori) AS avventori_tot "
			+ "FROM Ristorante NATURAL JOIN Sala NATURAL JOIN Tavolo NATURAL JOIN Tavolata "
			+ "GROUP BY codR, EXTRACT(YEAR FROM dataDiArrivo), EXTRACT(MONTH FROM dataDiArrivo) ";
	public static final String queryStatisticheAvventoriAnnuali = 
			  "SELECT codR, EXTRACT(YEAR FROM dataDiArrivo) AS anno, SUM(n°avventori) AS avventori_tot "
			+ "FROM Ristorante NATURAL JOIN Sala NATURAL JOIN Tavolo NATURAL JOIN Tavolata "
			+ "GROUP BY codR, EXTRACT(YEAR FROM dataDiArrivo) ";
	public static ArrayList<String> dayHistogramBins;
	public static ArrayList<Integer> dayHistogramFrequencies;
	public static ArrayList<String> monthHistogramBins;
	public static ArrayList<Integer> monthHistogramFrequencies;
	public static ArrayList<String> yearHistogramBins;
	public static ArrayList<Integer> yearHistogramFrequencies;
	
	
	private static DBLogin DBlogin;
	public static Home home;
	public static RistoranteFrame ristoranteFrame;
	
	public static RistoranteDAO ristoranteDAO;
	public static SalaDAO salaDAO;
	
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
				salaDAO = new SalaDAO(DBConnector.getConnection());
			} catch(SQLException exc) {
				ErrorMessage error = new ErrorMessage(DBlogin, "Errore nel cercare di estrarre i dati dal database");
				error.setVisible(true);
				exc.printStackTrace();
			}
			home = new Home();
			home.setVisible(true);
			DBlogin.setVisible(false);
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
		ristoranteFrame.setVisible(true);
		home.setVisible(false);
	}
	
	/*
	 * RistoranteFrame functions:
	 * 
	 */
	public static void backToHome() {
		home.setVisible(true);
		ristoranteFrame.setVisible(false);
		ristoranteFrame = null;
	}
	
	public static String getInfoOf(Ristorante ristorante) {
		String indirizzo = ristorante.getIndirizzo() + " " + ristorante.getNumeroCivico() + ", " + ristorante.getCittà() + "\n";
		String orario = "Aperto dalle " + String.valueOf(ristorante.getOraapertura()) + " alle " + String.valueOf(ristorante.getOrachiusura()) + "\n";
		String sale = "\n" + String.valueOf(ristorante.getNumeroDiSale()) + " Sal" + (ristorante.getNumeroDiSale()==1 ? "a" : "e") + "\n";
		String info = indirizzo + orario + sale;
		
		for(Sala sala : salaDAO.getAllOf(ristorante)) {
			info = info + "Sala " + sala.getID() + ": " + sala.getNumeroDiTavoli() + " tavoli\n";
		}
		
		return info;
	}
	
	public static void calculateHistograms(Ristorante ristorante) {
		dayHistogramBins = new ArrayList<String>();
		dayHistogramFrequencies = new ArrayList<Integer>();
		monthHistogramBins = new ArrayList<String>();
		monthHistogramFrequencies = new ArrayList<Integer>();
		yearHistogramBins = new ArrayList<String>();
		yearHistogramFrequencies = new ArrayList<Integer>();
		
		String dayQuery = queryStatisticheAvventoriGiornalieri + " HAVING codR=" + String.valueOf(ristorante.getID()) + " ORDER BY dataDiArrivo ";
		String monthQuery = queryStatisticheAvventoriMensili + " HAVING codR=" + String.valueOf(ristorante.getID()) + " ORDER BY EXTRACT(YEAR FROM dataDiArrivo), EXTRACT(MONTH FROM dataDiArrivo) ";
		String yearQuery = queryStatisticheAvventoriAnnuali + " HAVING codR=" + String.valueOf(ristorante.getID()) + " ORDER BY EXTRACT(YEAR FROM dataDiArrivo) ";
		
		try {
			Statement statement = DBConnector.getConnection().createStatement();
			ResultSet table = statement.executeQuery(dayQuery);
			while(table.next()) {
				//dayHistogramBins.add(table.getString("dataDiArrivo"));
				dayHistogramBins.add(table.getString("giorno") + "/" + table.getString("mese") + "/" + table.getString("anno"));
				dayHistogramFrequencies.add(table.getInt("avventori_tot"));
			}
		} catch(SQLException exc) {
			ErrorMessage error = new ErrorMessage(home, "Errore nel caricare l'istogramma degli avventori giornalieri");
			error.setVisible(true);
			exc.printStackTrace();
		}
		
		try {
			Statement statement = DBConnector.getConnection().createStatement();
			ResultSet table = statement.executeQuery(monthQuery);
			while(table.next()) {
				monthHistogramBins.add(table.getString("mese") + "/" + table.getString("anno"));
				monthHistogramFrequencies.add(table.getInt("avventori_tot"));
			}
		} catch(SQLException exc) {
			ErrorMessage error = new ErrorMessage(home, "Errore nel caricare l'istogramma degli avventori mensili");
			error.setVisible(true);
			exc.printStackTrace();
		}
		
		try {
			Statement statement = DBConnector.getConnection().createStatement();
			ResultSet table = statement.executeQuery(yearQuery);
			while(table.next()) {
				yearHistogramBins.add(table.getString("anno"));
				yearHistogramFrequencies.add(table.getInt("avventori_tot"));
			}
		} catch(SQLException exc) {
			ErrorMessage error = new ErrorMessage(home, "Errore nel caricare l'istogramma degli avventori annuali");
			error.setVisible(true);
			exc.printStackTrace();
		}
	}
}
