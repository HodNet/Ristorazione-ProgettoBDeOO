import java.sql.SQLException;

public class Controller {
	
	public static void main(String[] args) {
		try {
			DBConnector.getIstance("jdbc:postgresql://localhost:5432/Ristorazione", "postgres", "giocaremolto8");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException throwables) {
			System.out.println("Error in connecting to PostgreSQL server");
			throwables.printStackTrace();
		}
	}

}
