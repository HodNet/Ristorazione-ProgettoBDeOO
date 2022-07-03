package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import DAO.Ristorante;
import controller.Controller;

public class RistoranteFrame extends JFrame {

	private static final int width = Controller.screenWidth;
	private static final int height = Controller.screenHeight-100;
	private static final int x = Controller.screenWidth/2 - width/2;
	private static final int y = Controller.screenHeight/2 - height/2;
	
	private JPanel contentPane;

	public RistoranteFrame(Ristorante ristorante) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(x, y, width, height);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		JPanel westPanel = new JPanel();
		westPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		westPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("200px")},
			new RowSpec[] {
				RowSpec.decode("150px"),
				RowSpec.decode("40px"),
				RowSpec.decode("20px"),
				RowSpec.decode("20px")}));
		contentPane.add(westPanel, BorderLayout.WEST);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(centerPanel, BorderLayout.CENTER);
		
		
		JLabel nome = new JLabel(ristorante.getNome());
		nome.setFont(new Font("Tahoma", Font.BOLD, 26));
		northPanel.add(nome);
		
		JLabel foto = new JLabel("");
		Controller.scaleImage(foto, ristorante.getFoto());
		westPanel.add(foto, "1, 1, fill, fill");
		
		JTextPane indirizzo = new JTextPane();
		indirizzo.setEditable(false);
		indirizzo.setText(ristorante.getIndirizzo() + " " + ristorante.getNumeroCivico() + ", " + ristorante.getCittà());
		indirizzo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		westPanel.add(indirizzo, "1, 2, fill, fill");
		
		JTextPane orario = new JTextPane();
		indirizzo.setEditable(false);
		indirizzo.setText("Aperto dalle " + String.valueOf(ristorante.getOraapertura()) + " alle " + String.valueOf(ristorante.getOrachiusura()));
		orario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		westPanel.add(orario, "1, 3, fill, fill");
		
		JTextPane sale = new JTextPane();
		sale.setEditable(false);
		sale.setText(String.valueOf(ristorante.getNumeroSale()) + " Sal" + (ristorante.getNumeroSale()==1 ? "a" : "e"));
		sale.setFont(new Font("Tahoma", Font.PLAIN, 13));
		westPanel.add(sale, "1, 4, fill, fill");
		
		JLabel yearHistogram = new JLabel("");
		yearHistogram.setBounds(0, 0, 310, 248);
		Controller.scaleImage(yearHistogram, "blank histogram.png");
		
		JLabel monthHistogram = new JLabel("");
		monthHistogram.setBounds(0, 0, 310, 248);
		Controller.scaleImage(monthHistogram, "blank histogram.png");
		
		JLabel dayHistogram = new JLabel("");
		dayHistogram.setBounds(0, 0, 310, 248);
		Controller.scaleImage(dayHistogram, "blank histogram.png");
		
		JButton indietro = new JButton("Indietro");
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.backToHome();
			}
		});
		southPanel.add(indietro);
		
		JButton boh = new JButton("Boh");
		southPanel.add(boh);
		
		
		
//		createHistogram(10, histogramRect.getY(), 300, null, null, 0);
	}
	
//	private void createHistogram(int x, int y, int h, int value[], String bin[], int n) {
//		int w = 10*h/8; //10:8 proportion scale
//		
//		JLabel histogram = new JLabel("");
//		histogram.setBounds(x, y, w, h);
//		Controller.scaleImage(histogram, "blank histogram.png");
//		contentPane.add(histogram);
//	}
}
