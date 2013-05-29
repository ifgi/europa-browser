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

import com.hp.hpl.jena.util.iterator.Filter;

import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.facade.Facade;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;

public class awTip extends JPanel {
//	Image imgPlay = null;
	
	private MainFrame mainFrame;
	private String startTime ="";
	private String endTime = "";
	private ArrayList<ArrayList<SOSObservation>> ons = new ArrayList<ArrayList<SOSObservation>>();
	JSlider slider = null;
	
    public awTip(MainFrame mF)
    {
    	super(true);
		this.setLayout(new BorderLayout());
		this.setMainFrame(mF);
//		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
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
		
		add(btnPlay, BorderLayout.PAGE_START);
		add(slider, BorderLayout.CENTER);
		pnlTime.add((Component) dpFrom);
		pnlTime.add((Component) dpUntil);
		
		add(pnlTime, BorderLayout.SOUTH);
		
		dpFrom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int month = dpFrom.getModel().getMonth()+1;
				startTime = dpFrom.getModel().getYear()+"-"+month+"-"+dpFrom.getModel().getDay()+"T00:00:00Z";
				
				buildTimeSeries();
			}
		});
		
		dpUntil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int month = dpUntil.getModel().getMonth()+1;
				endTime = dpUntil.getModel().getYear()+"-"+month+"-"+dpUntil.getModel().getDay()+"T23:59:59Z";
				
				buildTimeSeries();
			}
		});
		
		int delay = 3000;
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
		
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (startTime != "" && endTime != "") {
					if (timerswing.isRunning()) {
						btnPlay.setText("Play");
						timerswing.stop();
					} else {
						btnPlay.setText("Pause");
						timerswing.start();
					}
				}
			}
		});
    }
    
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
