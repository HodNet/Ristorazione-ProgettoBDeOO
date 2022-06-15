import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;

public class ErrorMessage extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public ErrorMessage(JFrame frame, String error) {
		setBounds(frame.getX() + frame.getWidth()/2 - 180, frame.getY() + frame.getHeight()/2 - 75, 360, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(10, 11, 45, 45);
		scaleImage(lblNewLabel, new ImageIcon(ErrorMessage.class.getResource("/images/error.png")));
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
	
	public void scaleImage(JLabel label, ImageIcon icon) {
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		label.setIcon(scaledIcon);
	}
}
