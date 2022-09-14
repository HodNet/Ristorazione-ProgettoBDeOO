package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class AvventoreDAO implements DAO<Avventore> {
	private LinkedList<Avventore> avventori;
	private String query;
	private Statement statement;
	private ResultSet table;
	
	public AvventoreDAO(Connection connection) throws SQLException {
		query = "SELECT* FROM Avventore";
		statement = connection.createStatement();
		table = statement.executeQuery(query);
		while(table.next()) {
			if (avventori == null)
				avventori = new LinkedList<Avventore>();
			avventori.add(new Avventore(table.getString("codCartaIdentit�"),
							   			table.getString("nome"),
							   			table.getString("cognome"),
							   			table.getString("n�telefono")));
		}
	}

	public Avventore get(String codCartaIdentit�) {
		for (Avventore avventore : avventori)
			if(avventore.getCodCartaIdentit�().equals(codCartaIdentit�))
				return avventore;
		return null;
	}

	@Override
	public List<Avventore> getAll() {
		return avventori;
	}

	@Override
	public void insert(Avventore element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Avventore element) {
		// TODO Auto-generated method stub
		
	}

}
