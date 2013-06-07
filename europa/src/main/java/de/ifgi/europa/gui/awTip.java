package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import com.toedter.calendar.JDateChooser;

import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.facade.Facade;

/**
 * This class represents all functionalities inside the <code>{@link AnnotationWindow}</code>
 * for playing time series of a SOSFeatureOfInterest. 
 * @author Matthias Pfeil
 *
 */
public class awTip extends JPanel {
	
	private MainFrame mainFrame;
	private String startTime ="";
	private String endTime = "";
	private ArrayList<ArrayList<SOSObservation>> ons = new ArrayList<ArrayList<SOSObservation>>();
	private int delay = 1000;
	private Image imgPlay = null;
	private Image imgPause = null;

	JSlider slider = null;
	
    public awTip(MainFrame mF)
    {
    	super(true);
		this.setLayout(new BorderLayout());
		this.setMainFrame(mF);
		slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		final SimpleDateFormat dateFormatString = new SimpleDateFormat("yyyy-MM-dd");
		final JButton btnPlay = new JButton("Play");
//		try {
//			imgPlay = ImageIO.read(getClass().getResource("/src/main/resources/play.png"));
////			imgPause = ImageIO.read(getClass().getResource("/de/ifgi/europa/resources/pause.png"));
//			btnPlay.setIcon(new ImageIcon(imgPlay.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		final JDateChooser dcFrom = new JDateChooser();
		final JDateChooser dcUntil = new JDateChooser();
		
		MyDateListener dateChooserListener = new MyDateListener();
		
		dcFrom.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (dcFrom.getDate() != null) {
					String tempDate = dateFormatString.format(dcFrom.getDate());
					tempDate = tempDate + "T00:00:00Z";
					System.out.println("From: "+tempDate);
					startTime = tempDate;
					buildTimeSeries();
				}
			}
		});
		
		dcUntil.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if (dcFrom.getDate() != null) {
					String tempDate = dateFormatString.format(dcUntil.getDate());
					tempDate = tempDate + "T23:59:59Z";
					System.out.println("Until: "+tempDate);
					endTime = tempDate;
					buildTimeSeries();
				}
			}
		});
		
		JPanel pnlDateChooser = new JPanel(new GridLayout(2, 1));
		
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
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
		
		final JComboBox cbDelay = new JComboBox();
		cbDelay.addItem(1);
		cbDelay.addItem(2);
		cbDelay.addItem(3);
		
		cbDelay.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					setDelay((Integer) cbDelay.getSelectedItem());
				}
			}
		});
		
		
		JPanel blCenter = new JPanel(new BorderLayout());
		JPanel panelDelay = new JPanel(new GridLayout(1, 2));
		
		JLabel labelDelay = new JLabel("Delay in sec:");
		
		panelDelay.add(labelDelay);
		panelDelay.add(cbDelay);
		
		blCenter.add(slider,BorderLayout.PAGE_START);
		blCenter.add(panelDelay,BorderLayout.CENTER);
		add(btnPlay, BorderLayout.PAGE_START);
		add(blCenter, BorderLayout.CENTER);
		
		pnlDateChooser.add(dcFrom);
		pnlDateChooser.add(dcUntil);

		add(pnlDateChooser,BorderLayout.SOUTH);
		
		
		/**
		 * taskPerformer for playing the time series with a delay of 3 seconds
		 */
		ActionListener taskPerformer = new ActionListener() {
			
			int sliderIndex = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				slider.setValue(sliderIndex++);
				
				if(sliderIndex <= slider.getMaximum()) {
					((MapPanel) getMainFrame().getMapPanel()).clearGlobe();
					for (int i = 0; i < ons.size(); i++) {
						SOSObservation obs =  ons.get(i).get(slider.getValue());
						((MapPanel) getMainFrame().getMapPanel()).updateGlobe(obs, "", ons.get(i).get(slider.getValue()).getLabel().toLowerCase());
					}
				} else {
					btnPlay.doClick();
				}
			}
		};
		
		final javax.swing.Timer timerswing = new javax.swing.Timer(delay, taskPerformer);
		
		/**
		 * ActionListener for Play button inside the AnnotationWindow.
		 */
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (startTime != "" && endTime != "") {
					if (timerswing.isRunning()) {
						btnPlay.setText("Play");
//						btnPlay.setIcon(new ImageIcon(imgPlay.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
						timerswing.stop();
					} else {
						btnPlay.setText("Pause");
//						btnPlay.setIcon(new ImageIcon(imgPause.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
						timerswing.start();
					}
				}
			}
		});
    }
    
    /**
     * Gets all information and observation for playing the time series together 
     * and sets up the slider
     */
	protected void buildTimeSeries() {
		if (startTime != "" && endTime != "") {
			TimeInterval interval = new TimeInterval(startTime, endTime);
			ArrayList<SOSFeatureOfInterest> selectedFOIs = ((FilterPanel) getMainFrame().getFilterPanel()).getFOIs();

			Facade facade = ((FilterPanel) getMainFrame().getFilterPanel()).getFacade();
			
			for (int i = 0; i < selectedFOIs.size(); i++) {
				ArrayList<SOSObservation> obs = facade.getObservationByInterval(selectedFOIs.get(i), interval);
				for (int j = 0; j < obs.size(); j++) {
					obs.get(j).setLabel(selectedFOIs.get(i).getLabel());
				}
				ons.add(obs);
			}
			
			slider.setMaximum(ons.get(0).size()-1);
			slider.setMinorTickSpacing(1);
			slider.setMajorTickSpacing(1);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.updateUI();
		}
		
	}

	/**
	 * Gets MainFrame
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Sets MainFrame
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	private class MyDateListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			System.out.println("changed");
		}

	}
}
