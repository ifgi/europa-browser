package de.ifgi.europa.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StatusBarPanel extends JPanel {
	
	private MainFrame mainFrame;
	private JLabel downloadLabel;
	
	public StatusBarPanel(MainFrame frame) {
		super(new GridLayout(1, 1));
		setMainFrame(frame);
		
//		Image imgSpinner = Toolkit.getDefaultToolkit().createImage("resources/spinner.gif");
//        ImageIcon iconSpin = new ImageIcon(imgSpinner);
		
		ImageIcon iconSpin = new ImageIcon(getClass().getResource("/spinner.gif"));
		
		downloadLabel = new JLabel("Downloading", iconSpin, SwingConstants.TRAILING);
		downloadLabel.setForeground(Color.RED);
		downloadLabel.setVisible(false);
		add(downloadLabel);
	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void toggle(Boolean toggle) {
		if (toggle) {
			downloadLabel.setVisible(true);
		} else {
			downloadLabel.setVisible(false);
		}
	}
}
