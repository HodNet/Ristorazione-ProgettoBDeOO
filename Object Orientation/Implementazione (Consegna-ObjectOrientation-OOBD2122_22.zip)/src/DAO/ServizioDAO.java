package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ServizioDAO implements DAO<Servizio> {
	private LinkedList<Servizio> servizioList;
	private String query;
	private Statement statement;
	private ResultSet table;
	private Connection connection;
	
	public ServizioDAO(Connection connection) throws SQLException {
		servizioList = new LinkedList<Servizio>();
		query = "SELECT* FROM Servizio";
		statement = connection.createStatement();
		table = statement.executeQuery(query);
		while(table.next()) {
			servizioList.add(new Servizio(table.getString("codCameriere"),
							   			table.getString("dataDiArrivo"),
							   			table.getString("codT")));
		}
		this.connection = connection;
	}

	public Servizio getCameriereOf(String dataDiArrivo, String tavoloID) {
		for (Servizio x : servizioList) {
			if(x.getDataDiArrivo().equals(dataDiArrivo) && x.getTavoloID().equals(tavoloID)) {
				return x;
			}
		}
		return null;
	}
	
	@Override
	public List<Servizio> getAll() {
		return servizioList;
	}

	@Override
	public void insert(Servizio element) throws SQLException {
		query = "INSERT INTO Servizio(codCameriere, dataDiArrivo, codT) VALUES (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, element.getCodCameriere());
		preparedStatement.setDate(2, Date.valueOf(element.getDataDiArrivo()));
		preparedStatement.setInt(3, Integer.valueOf(element.getTavoloID()));
		preparedStatement.execute();
	}

	@Override
	public void delete(Servizio element) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}