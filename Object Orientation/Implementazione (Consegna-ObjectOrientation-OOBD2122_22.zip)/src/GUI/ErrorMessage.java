package GUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;

public class ErrorMessage extends JDialog {

	private static final int width = 360;
	private static final int height = 150;
	
	private final JPanel contentPanel = new JPanel();

	public ErrorMessage(JFrame frame, String error) {
		setBounds(frame.getX() + frame.getWidth()/2 - width/2, frame.getY() + frame.getHeight()/2 - height/2, width, height);
		setAlwaysOnTop(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 11, 45, 45);
		Controller.scaleImage(lblNewLabel, "error.png");
		contentPanel.add(lblNewLabel);
		
		JTextPane txtpnErrorMessage = new JTextPane();
		txtpnErrorMessage.setEditable(false);
		txtpnErrorMessage.setText(error);
		txtpnErrorMessage.setBackground(new Color(240, 240, 240));
		txtpnErrorMessage.setBounds(65, 11, 269, 45);
		contentPanel.add(txtpnErrorMessage);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
