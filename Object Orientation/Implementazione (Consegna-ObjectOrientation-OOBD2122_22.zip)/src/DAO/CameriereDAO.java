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
		camerieri = new LinkedList<Cameriere>();
		query = "SELECT* FROM Cameriere";
		statement = connection.createStatement();
		table = statement.executeQuery(query);
		while(table.next()) {
			camerieri.add(new Cameriere(table.getString("codCartaIdentità"),
							   			table.getString("nome"),
							   			table.getString("cognome"),
							   			table.getString("n°telefono"),
							   			table.getString("dataDiNascita"),
							   			table.getString("città"),
							   			table.getString("indirizzo"),
							   			table.getString("n°civico"),
							   			table.getString("sesso"),
							   			table.getString("codR")));
		}
	}

	public Cameriere get(String codCartaIdentità) {
		for (Cameriere cameriere : camerieri) {
			if(cameriere.getCodCartaIdentità().equals(codCartaIdentità))
				return cameriere;
		}
		return null;
	}
	
	public List<Cameriere> getCamerieriOf(Ristorante ristorante) {
		LinkedList<Cameriere> camerieriSelected = new LinkedList<Cameriere>();
		for (Cameriere cameriere : camerieri) {
			if(cameriere.getRistoranteID().equals(ristorante.getID()))
				camerieriSelected.add(cameriere);
		}
		return camerieriSelected;
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
