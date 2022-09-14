package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class CameriereDAO implements DAO<Cameriere> {
	private LinkedList<Cameriere> camerieri;
	private String query;
	private Statement statement;
	private ResultSet table;
	
	public CameriereDAO(Connection connection) throws SQLException {
		query = "SELECT* FROM Cameriere";
		statement = connection.createStatement();
		table = statement.executeQuery(query);
		while(table.next()) {
			if (camerieri == null)
				camerieri = new LinkedList<Cameriere>();
			camerieri.add(new Cameriere(table.getString("codCartaIdentit�"),
							   			table.getString("nome"),
							   			table.getString("cognome"),
							   			table.getString("n�telefono"),
							   			table.getString("dataDiNascita"),
							   			table.getString("citt�"),
							   			table.getString("indirizzo"),
							   			table.getString("n�civico"),
							   			table.getString("sesso"),
							   			table.getString("codR")));
		}
	}

	public Cameriere get(String codCartaIdentit�) {
		for (Cameriere cameriere : camerieri)
			if(cameriere.getCodCartaIdentit�().equals(codCartaIdentit�))
				return cameriere;
		return null;
	}

	@Override
	public List<Cameriere> getAll() {
		return camerieri;
	}

	@Override
	public void insert(Cameriere element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Cameriere element) {
		// TODO Auto-generated method stub

	}

}
