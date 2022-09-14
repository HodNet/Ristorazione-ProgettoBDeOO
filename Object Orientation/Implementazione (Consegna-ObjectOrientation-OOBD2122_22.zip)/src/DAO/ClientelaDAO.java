package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ClientelaDAO implements DAO<Clientela> {
	private LinkedList<Clientela> clientelaList;
	private String query;
	private Statement statement;
	private ResultSet table;
	
	public ClientelaDAO(Connection connection) throws SQLException {
		query = "SELECT* FROM Clientela";
		statement = connection.createStatement();
		table = statement.executeQuery(query);
		while(table.next()) {
			if (clientelaList == null)
				clientelaList = new LinkedList<Clientela>();
			clientelaList.add(new Clientela(table.getString("codCartaIdentità"),
							   				table.getString("dataDiArrivo"),
							   				table.getString("codT")));
		}
	}
	
	public List<Clientela> getAvventoriOf(String dataDiArrivo, String tavoloID) {
		LinkedList<Clientela> clientelaSelected = null;
		for (Clientela cliente : clientelaList) {
			if(cliente.getDataDiArrivo().equals(dataDiArrivo) && cliente.getTavoloID().equals(tavoloID)) {
				if (clientelaSelected == null)
					clientelaSelected = new LinkedList<Clientela>();
				clientelaSelected.add(cliente);
			}
		}
		return clientelaSelected;
	}

	@Override
	public List<Clientela> getAll() {
		return clientelaList;
	}

	@Override
	public void insert(Clientela element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Clientela element) {
		// TODO Auto-generated method stub
		
	}

}
