import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class RistoranteDAO implements DAO<Ristorante> {

	private LinkedList<Ristorante> ristoranti = null;
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
			String foto = System.getProperty("user.dir") + File.separator + "src\\images\\" + table.getString("nome");
			ristoranti.add(new Ristorante(table.getString("codR"),
										  table.getString("nome"),
										  table.getString("città"),
										  table.getString("indirizzo"),
										  table.getString("n°civico"),
										  table.getByte("n°sale"), foto));
		}
	}
	
	@Override
	public Ristorante get(String ID) {
		for (Ristorante x : ristoranti)
			if(x.getID() == ID)
				return x;
		return null;
	}
	
	@Override
	public List<Ristorante> getAll() {
		return ristoranti;
	}
	
	@Override
	public void insert(Ristorante ristorante) {
		
	}
	
	public void update(String ID, String nome, String città, String indirizzo, String numeroCivico) {
		
	}
    
	@Override
    public void delete(Ristorante ristorante) {
    	
    }
}
