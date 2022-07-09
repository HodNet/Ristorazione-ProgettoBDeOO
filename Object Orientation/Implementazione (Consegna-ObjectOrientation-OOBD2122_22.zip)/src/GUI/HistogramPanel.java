package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import controller.Controller;

public class HistogramPanel extends JPanel {
	
	private static final int axisWidth = 15;
	private static final int axisHeight = 13;
	
	private static final int numberOfFrequencies = 20;
	private int numberOfBins = 2;
	
	private static final int imageFrequencyAxisWidth = 29;
	private static final int imageBinAxisHeight = 26;
	private static final int imageTopBorderHeight = 26;
	private static final int imageBinWidth = 63;
	private static final int imageWidth = 1919;
	private static final int imageHeight = 1116; 
	private int frequencyAxisWidth = imageFrequencyAxisWidth;
	private int binAxisHeight = imageBinAxisHeight;
	private int binWidth = imageBinWidth;
	private int width = imageWidth;
	private int height = imageHeight;
	
	private JLabel frequencyText[];
	private JLabel binText[];
	private JLabel frequencyAxis;
	private JLabel blankBin[];
	private JLabel binRect[];
	
	private int frequency[];
	private int maxFrequency = numberOfFrequencies;
	private LinkedList<String> dataList = new LinkedList<String>();
	
	private GroupLayout gl;
	
	/*
	 * Constructors:
	 * 
	 */
	public HistogramPanel(int numberOfBins) {
		Init(numberOfBins);
		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(int numberOfBins, int width, int height) {
		Init(numberOfBins);
		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(int numberOfBins, int width, int height, String bin[]) {
		Init(numberOfBins);
		
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin[i]) );
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}

		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(int numberOfBins, int width, int height, String bin[], int frequency[]) {
		Init(numberOfBins);
		
		this.frequency = new int[numberOfBins];
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin[i]) );
				this.frequency[i] = frequency[i];
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}

		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(int numberOfBins, String bin[]) {
		Init(numberOfBins);
		
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin[i]) );
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}

		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(int numberOfBins, String bin[], int frequency[]) {
		Init(numberOfBins);
		
		this.frequency = new int[numberOfBins];
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin[i]) );
				this.frequency[i] = frequency[i];
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}
		
		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(int width, int height, final ArrayList<String> bin) {
		Init(bin.size());
		
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin.get(i)) );
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}

		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(int width, int height, final ArrayList<String> bin, final ArrayList<Integer> frequency) {
		Init(bin.size());
		
		this.frequency = new int[numberOfBins];
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin.get(i)) );
				this.frequency[i] = frequency.get(i);
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}

		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(final ArrayList<String> bin) {
		Init(bin.size());
		
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin.get(i)) );
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}

		Resize(width, height);
		createBlankHistogram();
	}
	
	public HistogramPanel(final ArrayList<String> bin, final ArrayList<Integer> frequency) {
		Init(bin.size());
		
		this.frequency = new int[numberOfBins];
		for(int i=0; i<numberOfBins; i++) {
			try {
				dataList.add( new String(bin.get(i)) );
				this.frequency[i] = frequency.get(i);
			}
			catch(ArrayIndexOutOfBoundsException e) {}
		}
		
		Resize(width, height);
		createBlankHistogram();
	}
	
	/*
	 * Specific methods:
	 * 
	 */
	public void add(String data) {
		if( !dataList.contains(data) ) {
			dataList.add(data);
			binText[dataList.indexOf(data)].setText(data);
		}
		frequency[dataList.indexOf(data)]++;
		update();
	}
	
	/*
	 * Auxiliary functions:
	 * 
	 */
	private void Init(int numberOfBins) {
		this.numberOfBins = numberOfBins;
		
		frequencyText = new JLabel[numberOfFrequencies+1]; //uno in più per lo zero
		binText = new JLabel[numberOfBins];
		frequencyAxis = new JLabel("");
		blankBin = new JLabel[numberOfBins];
		binRect = new JLabel[numberOfBins];
		frequency = new int[numberOfBins];
		
		for(int i=0; i<numberOfBins; i++) {
			blankBin[i] = new JLabel("");
			binRect[i] = new JLabel("");
			frequency[i] = 0;
		}
	}
	
	private void Resize(int width, int height) {
		width = width - axisWidth;
		height = height - axisHeight;
		frequencyAxisWidth = imageFrequencyAxisWidth * width/imageWidth;
		binAxisHeight = imageBinAxisHeight * height/imageHeight;
		binWidth = imageBinWidth * width/imageWidth * 30/numberOfBins;
		if(binText[0]!=null) binWidth = Integer.max(binWidth, maxWidth(binText, numberOfBins));
		this.width = frequencyAxisWidth + binWidth*numberOfBins;
		this.height = height;
		
		frequencyAxis.setBounds(axisWidth, 0, frequencyAxisWidth, this.height);
		scaleImage(frequencyAxis, "histogram frequency axis.jpg");
		
		for(int i=0; i<numberOfBins; i++) {
			blankBin[i].setBounds(axisWidth + frequencyAxisWidth + i*binWidth, 0, binWidth, this.height);
			scaleImage(blankBin[i], "histogram blank bin.jpg");
		}
		
		update();
	}
	
	private int maxWidth(JLabel label[], int length) {
		int ret = 0;
		for(int i=0; i<length; i++) {
			ret = Integer.max(ret, label[i].getWidth());
		}
		return ret;
	}
	
	private void createBlankHistogram() {
		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int w = ((Component)e.getSource()).getWidth();
				int h = ((Component)e.getSource()).getHeight();
				Resize(w, h);
			}
		});
		
		gl = new GroupLayout(this);
		
		ParallelGroup parallelY = gl.createParallelGroup(Alignment.LEADING);
		SequentialGroup sequentialY = gl.createSequentialGroup();
		for(int i=numberOfFrequencies; i>=0; i--) {
			addFrequency(parallelY, sequentialY, i);
		}
		
		ParallelGroup parallelX = gl.createParallelGroup(Alignment.LEADING);
		SequentialGroup sequentialXandBins = gl.createSequentialGroup();
		ParallelGroup parallelBins = gl.createParallelGroup(Alignment.TRAILING);
		for(int i=0; i<numberOfBins; i++) {
			addBin(parallelX, sequentialXandBins, parallelBins, i);
		}
		
		gl.setHorizontalGroup(
				gl.createSequentialGroup()
				.addGroup(parallelY) //all y axis texts
				.addComponent(frequencyAxis)
				.addGroup(sequentialXandBins) //all x axis texts and histogram bins
		);
		gl.setVerticalGroup(
			gl.createSequentialGroup()
			.addGroup(gl.createParallelGroup(Alignment.LEADING)
					.addGroup(sequentialY) //all y axis texts
					.addComponent(frequencyAxis)
					.addGroup(parallelBins)) //all histogram Bins
			.addGroup(parallelX) //all x axis texts
		);
		setLayout(gl);
	}
	
	private void addFrequency(ParallelGroup parallel, SequentialGroup sequential, int i) {
		int frequencyValue = (i==numberOfFrequencies) ? (maxFrequency) : (i * maxFrequency/numberOfFrequencies);
		frequencyText[i] = new JLabel(String.valueOf(frequencyValue));
		frequencyText[i].setFont(new Font("Tahoma", Font.PLAIN, 10));
		parallel.addComponent(frequencyText[i]);
		sequential.addComponent(frequencyText[i], GroupLayout.PREFERRED_SIZE, (i%3==0 ? 14 : 12), Short.MAX_VALUE);
	}
	
	private void addBin(ParallelGroup parallelX, SequentialGroup sequential, ParallelGroup parallelBins, int i) {
		String bin = (i >= dataList.size()) ? "" : dataList.get(i);
		binText[i] = new JLabel(bin);
		binText[i].setFont(new Font("Tahoma", Font.PLAIN, 10));
		binText[i].setHorizontalAlignment(SwingConstants.CENTER);
		
		sequential.addGroup(gl.createParallelGroup(Alignment.LEADING)
				.addComponent(binRect[i])
				.addComponent(blankBin[i])
				.addComponent(binText[i]));
		parallelBins.addComponent(binRect[i]);
		parallelBins.addComponent(blankBin[i]);
		parallelX.addComponent(binText[i]);
	}
	
	private void update() {
		int gap = 2;
		int topBorderHeight = imageTopBorderHeight * height/imageHeight;
		
		int max = max(frequency);
		if(max>numberOfFrequencies) {
			maxFrequency = generateNextMultipleOfNumberOfFrequenciesOf(max);
		}
		
		for(int i=0; i<numberOfBins; i++) {
			int binHeight = (height-binAxisHeight-topBorderHeight) * frequency[i]/maxFrequency;
			binRect[i].setBounds(axisWidth + frequencyAxisWidth + i*binWidth, 0, binWidth-gap, binAxisHeight + binHeight);
			scaleImage(binRect[i], "rectangle selected.jpg");
		}
	}
	
	private int max(int n[]) {
		return Arrays.stream(n).max().getAsInt();
	}
	
	private int generateNextMultipleOfNumberOfFrequenciesOf(int n) {
		return numberOfFrequencies * (int)Math.ceil( (double)n / (double)numberOfFrequencies );
	}
	
	private void scaleImage(JLabel label, String file_name) {
		ImageIcon icon = new ImageIcon(Controller.class.getResource("/images/" + file_name));
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		label.setIcon(scaledIcon);
	}
	

}
