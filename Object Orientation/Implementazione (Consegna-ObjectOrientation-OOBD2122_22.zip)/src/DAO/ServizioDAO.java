package DAO;

import java.sql.Connection;
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
	
	public ServizioDAO(Connection connection) throws SQLException {
		query = "SELECT* FROM Servizio";
		statement = connection.createStatement();
		table = statement.executeQuery(query);
		while(table.next()) {
			if (servizioList == null)
				servizioList = new LinkedList<Servizio>();
			servizioList.add(new Servizio(table.getString("codCameriere"),
							   			table.getString("dataDiArrivo"),
							   			table.getString("codT")));
		}
	}

	public List<Servizio> getCamerieriOf(String dataDiArrivo, String tavoloID) {
		LinkedList<Servizio> servizioSelected = null;
		for (Servizio x : servizioList) {
			if(x.getDataDiArrivo().equals(dataDiArrivo) && x.getTavoloID().equals(tavoloID)) {
				if (servizioSelected == null)
					servizioSelected = new LinkedList<Servizio>();
				servizioSelected.add(x);
			}
		}
		return servizioSelected;
	}
	
	@Override
	public List<Servizio> getAll() {
		return servizioList;
	}

	@Override
	public void insert(Servizio element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Servizio element) {
		// TODO Auto-generated method stub
		
	}

}
