package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import DAO.Ristorante;
import controller.Controller;
import javax.swing.LayoutStyle.ComponentPlacement;

public class RistoranteFrame extends JFrame {

	private static final int width = 700;//Controller.screenWidth;
	private static final int height = 425;//Controller.screenHeight-100;
	private static final int x = Controller.screenWidth/2 - width/2;
	private static final int y = Controller.screenHeight/2 - height/2;
	
	private JPanel contentPane;
	private Ristorante ristorante;
	
	private JLabel histogram;

	public RistoranteFrame(Ristorante r) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(x, y, width, height);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		ristorante = r;
		NorthPanel();
		WestPanel();
		CenterPanel();
		SouthPanel();
		
//		createHistogram(10, histogramRect.getY(), 300, null, null, 0);
	}
	
	private void NorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		JLabel nome = new JLabel(ristorante.getNome());
		nome.setFont(new Font("Tahoma", Font.BOLD, 26));
		northPanel.add(nome);
	}
	
	private void WestPanel() {
		JPanel westPanel = new JPanel();
		westPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(westPanel, BorderLayout.WEST);
		
		JLabel foto = new JLabel("");
		foto.setBounds(0, 0, 200, 150);
		Controller.scaleImage(foto, ristorante.getFoto());
		
		JTextArea infoRistoranteTextArea = new JTextArea();
		infoRistoranteTextArea.setWrapStyleWord(true);
		infoRistoranteTextArea.setLineWrap(true);
		infoRistoranteTextArea.setEditable(false);
		infoRistoranteTextArea.setText(Controller.getInfoOf(ristorante));
		infoRistoranteTextArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		JScrollPane infoRistorante = new JScrollPane(infoRistoranteTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		GroupLayout gl_westPanel = new GroupLayout(westPanel);
		gl_westPanel.setHorizontalGroup(
			gl_westPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(foto, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
				.addComponent(infoRistorante, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
		);
		gl_westPanel.setVerticalGroup(
			gl_westPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_westPanel.createSequentialGroup()
					.addComponent(foto, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addComponent(infoRistorante))
		);
		westPanel.setLayout(gl_westPanel);
	}
	
	private void CenterPanel() {
		JTabbedPane centerPanel = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		
		Controller.calculateHistograms(ristorante);
		
		HistogramPanel dayHistogram = new HistogramPanel(Controller.dayHistogramBins, Controller.dayHistogramFrequencies);
		dayHistogram.setBackground(Color.WHITE);
		centerPanel.addTab("Clienti giornalieri", null, dayHistogram, null);
		
		HistogramPanel monthHistogram = new HistogramPanel(Controller.monthHistogramBins, Controller.monthHistogramFrequencies);
		monthHistogram.setBackground(Color.WHITE);
		centerPanel.addTab("Clienti mensili", null, monthHistogram, null);
		
		HistogramPanel yearHistogram = new HistogramPanel(Controller.yearHistogramBins, Controller.yearHistogramFrequencies);
		yearHistogram.setBackground(Color.WHITE);
		centerPanel.addTab("Clienti annuali", null, yearHistogram, null);
	}
	
	private void SouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		contentPane.add(southPanel, BorderLayout.SOUTH);
		
		JButton indietro = new JButton("Indietro");
		indietro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.backToHome();
			}
		});
		southPanel.add(indietro);
		
		JButton boh = new JButton("Boh");
		southPanel.add(boh);
	}
}
