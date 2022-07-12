package DAO;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class RistoranteDAO implements DAO<Ristorante> {

	private LinkedList<Ristorante> ristoranti;
	private String query;
	private Statement statement;
	private ResultSet table;

	public RistoranteDAO(Connection connection) throws SQLException {
		query = "SELECT* FROM Ristorante";
		statement = connection.createStatement();
		table = statement.executeQuery(query);
		while(table.next()) {
			if (ristoranti == null)
				ristoranti = new LinkedList<Ristorante>();
			ristoranti.add(new Ristorante((table.getString("codR")),
										  table.getString("nome"),
										  table.getString("città"),
										  table.getString("indirizzo"),
										  table.getString("n°civico"),
										  table.getByte("n°sale")));
		}
	}
	
	@Override
	public Ristorante get(String ID) {
		for (Ristorante x : ristoranti)
			if(x.getID().equals(ID))
				return x;
		return null;
	}
	
	@Override
	public List<Ristorante> getAll() {
		return ristoranti;
	}
	
//	@Override
//	public void insert(Ristorante ristorante) {
//		//TODO
//	}
//	
//	public void update(String ID, String nome, String città, String indirizzo, String numeroCivico) {
//		//TODO
//	}
//    
//	@Override
//    public void delete(Ristorante ristorante) {
//    	//TODO
//   }
	
	public int getNumeroRistoranti() {
		return ristoranti.size();
	}
}
