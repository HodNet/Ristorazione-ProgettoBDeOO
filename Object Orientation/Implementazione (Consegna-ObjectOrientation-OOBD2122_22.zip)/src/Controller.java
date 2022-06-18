import javax.swing.JFrame;

public class Controller extends JFrame {
	private static DBAccess frame;
	protected Home home;
	protected RistoranteDAO ristoranteDAO;
	
	public static void main(String[] args) {
		frame = new DBAccess();
		frame.setVisible(true);
	}

}
