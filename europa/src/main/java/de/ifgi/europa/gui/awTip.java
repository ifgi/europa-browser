package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.TimeInterval;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;

public class awTip extends JPanel {
//	Image imgPlay = null;
	
	private MainFrame mainFrame;
	
    public awTip(MainFrame mF)
    {
    	super(true);
		this.setLayout(new BorderLayout());
		this.setMainFrame(mF);
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		final JButton btnPlay = new JButton("Play");
//		try {
//			Image imgPlay = ImageIO.read(getClass().getResource("resources/play.png"));
//			btnPlay.setIcon(new ImageIcon(imgPlay.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		final JDatePicker dpFrom;
		dpFrom = JDateComponentFactory.createJDatePicker();
		dpFrom.setTextEditable(true);
		dpFrom.setShowYearButtons(true);
		
		final JDatePicker dpUntil;
		dpUntil = JDateComponentFactory.createJDatePicker();
		dpUntil.setTextEditable(true);
		dpUntil.setShowYearButtons(true);
		
		JPanel pnlTime = new JPanel(new GridLayout(2, 1));
		
		slider.setMinorTickSpacing(2);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
//		Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel> ();
//	    table.put ( 0, new JLabel ( "May 2, 2000" ) );
//	    table.put ( 25, new JLabel ( "May 3, 2001" ) );
//	    table.put ( 50, new JLabel ( "May 4, 2002" ) );
//	    table.put ( 75, new JLabel ( "May 5, 2003" ) );
//	    table.put ( 100, new JLabel ( "May 6, 2004" ) );
//	    slider.setLabelTable ( table );
		
		slider.setLabelTable(slider.createStandardLabels(10));
		
		add(btnPlay, BorderLayout.PAGE_START);
		add(slider, BorderLayout.CENTER);
		pnlTime.add((Component) dpFrom);
		pnlTime.add((Component) dpUntil);
		
		add(pnlTime, BorderLayout.SOUTH);
		
		dpFrom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("test");
			}
		});
		
		int delay = 1000;
		ActionListener taskPerformer = new ActionListener() {
			int i = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				slider.setValue(i++);
				if(i == slider.getMaximum()) {
					btnPlay.doClick();
					System.out.println("reached max");
					i = 0;
				}
			}
		};
		
		final javax.swing.Timer timerswing = new javax.swing.Timer(delay, taskPerformer);
		
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(timerswing.isRunning()) {
					btnPlay.setText("Play");
					timerswing.stop();
//					btnPlay.setIcon(new ImageIcon(imgPlay.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
				} else {
					String startTime = "";
					startTime = dpFrom.getModel().getYear()+"-"+dpFrom.getModel().getMonth()+"-"+dpFrom.getModel().getDay()+"T13:02:00Z";
					String endTime = "";
					startTime = dpUntil.getModel().getYear()+"-"+dpUntil.getModel().getMonth()+"-"+dpUntil.getModel().getDay()+"T13:04:00Z";
					TimeInterval interval = new TimeInterval(startTime, endTime);
					ArrayList<SOSFeatureOfInterest> selectedFOIs = ((FilterPanel) getMainFrame().getFilterPanel()).getFOIs();
					btnPlay.setText("Pause");
//					try {
////						Image imgPause = ImageIO.read(getClass().getResource("resources/pause.png"));
////						btnPlay.setIcon(new ImageIcon(imgPause.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
					timerswing.start();
				}
			}
		});
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
}
