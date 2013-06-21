/**
   Copyright 2013 Jim Jones, Matthias Pfeil and Alber Sanchez

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import com.toedter.calendar.JDateChooser;

import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSSensorOutput;
import de.ifgi.europa.core.SOSValue;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.facade.Facade;
import de.ifgi.europa.gui.FilterPanel.FeaturesOnTheGlobe;

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
	private ArrayList<ArrayList<SOSObservation>> ons;
	private ArrayList<FeaturesOnTheGlobe> selectedFOIs;
	private ArrayList<ArrayList<Integer>> colorramp;
	private int delay = 10;
	private Image imgPlay = null;
	private Image imgPause = null;
	int sliderIndex = 0;
	final JButton btnPlay;
	JSlider slider = null;
	
    public awTip(MainFrame frame)
    {
    	super(true);
		this.setLayout(new BorderLayout());
		this.setMainFrame(frame);
		ons = new ArrayList<ArrayList<SOSObservation>>();
		colorramp = new ArrayList<ArrayList<Integer>>();
		slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		final SimpleDateFormat dateFormatString = new SimpleDateFormat("yyyy-MM-dd");
		btnPlay = new JButton("Play");
		
		imgPlay = Toolkit.getDefaultToolkit().createImage("resources/play.png");
		imgPause = Toolkit.getDefaultToolkit().createImage("resources/pause.png");
		btnPlay.setIcon(new ImageIcon(imgPlay.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
		
		btnPlay.setEnabled(false);
		
		final JDateChooser dcFrom = new JDateChooser();
		final JDateChooser dcUntil = new JDateChooser();
		
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
		cbDelay.addItem(100);
		cbDelay.addItem(200);
		cbDelay.addItem(300);
		cbDelay.addItem(400);
		cbDelay.addItem(500);
		
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
		
		JLabel labelDelay = new JLabel("Delay in msec:");
		
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
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				slider.setValue(sliderIndex++);
				System.out.println(sliderIndex);
				System.out.println(slider.getMaximum());
				System.out.println("--Check--");
				
				if(sliderIndex <= slider.getMaximum()) {
					System.out.println("--Clear globe--");
					((MapPanel) getMainFrame().getMapPanel()).clearGlobe();
					for (int i = 0; i < ons.size(); i++) {
						System.out.println("getObs");
						try {
							SOSObservation obs =  ons.get(i).get(slider.getValue());
							for (int j = 0; j < selectedFOIs.size(); j++) {
								if (obs.getFeatureOfInterest().getUri().toString().toLowerCase().compareTo(selectedFOIs.get(j).getFoi().getUri().toString().toLowerCase()) == 0) {
									String[] arrProp = selectedFOIs.get(j).getProperty().getProperty().getUri().toString().split("\\#");
									String[] arrFOI = selectedFOIs.get(j).getFoi().getUri().toString().split("\\#");
									System.out.println(arrProp[1]+"-"+arrFOI[1]);
									if (obs.getFeatureOfInterest().getIdentifier().toString().toLowerCase().compareTo(selectedFOIs.get(j).getProperty().getProperty().getUri().toString().toLowerCase()) == 0) {
										if (selectedFOIs.get(j).getProperty().getVisualization().toLowerCase().compareTo("color") == 0) {
											int color = colorramp.get(i).get(slider.getValue());
											((MapPanel) getMainFrame().getMapPanel()).updateGlobe(obs, null, selectedFOIs.get(j).getProperty(), arrProp[1]+"-"+arrFOI[1], color, false);
										} else {
											((MapPanel) getMainFrame().getMapPanel()).updateGlobe(obs, null, selectedFOIs.get(j).getProperty(), arrProp[1]+"-"+arrFOI[1], -1, false);
										}
										
										
									}
								}
							}
						
						((MapPanel) getMainFrame().getMapPanel()).redrawGlobe();
						} catch (Exception e2) {
							System.out.println("No more observation!");
						}
					}
						
				} else {
					btnPlay.doClick();
				}
			}
		};
		
		final javax.swing.Timer timerswing = new javax.swing.Timer(10, taskPerformer);
		
		/**
		 * ActionListener for Play button inside the AnnotationWindow.
		 */
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (startTime != "" && endTime != "") {
					if (timerswing.isRunning()) {
						btnPlay.setText("Play");
						btnPlay.setIcon(new ImageIcon(imgPlay.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
						timerswing.stop();
					} else {
						btnPlay.setText("Pause");
						btnPlay.setIcon(new ImageIcon(imgPause.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
						timerswing.start();
						if(sliderIndex > slider.getValue()) {
							sliderIndex = 0;
							slider.setValue(0);
							slider.updateUI();
						}
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
			((StatusBarPanel) getMainFrame().getStatusBarPanel()).toggle(true);
			new AnswerWorker().execute();
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
	
	public ArrayList<Integer> getUniqueColors(int lowerLimit, int upperLimit, int amount) {
	    final int colorStep = (upperLimit-lowerLimit)/amount;

	    final ArrayList<Integer> colors = new ArrayList<Integer>();
	    for (int i=0;i<amount;i++) {
	        int color = lowerLimit+colorStep*i;
	        colors.add(color);
	    }
	    return colors;
	}
	
	class AnswerWorker extends SwingWorker<Integer, Integer>
	{
		
	    protected Integer doInBackground() throws Exception
	    {
	    	TimeInterval interval = new TimeInterval(startTime, endTime);
			selectedFOIs = ((FilterPanel) getMainFrame().getFilterPanel()).getFoisOnTheGlobe();
			Facade facade = ((FilterPanel) getMainFrame().getFilterPanel()).getFacade();
			System.out.println("\n**** GET OBSERVATIONS BY TIME-INTERVAL **** \n");
			for (int i = 0; i < selectedFOIs.size(); i++) {
				SOSObservation obs = facade.getObservationByInterval(selectedFOIs.get(i).getFoi(), interval);
				String viz = selectedFOIs.get(i).getProperty().getVisualization();
				ArrayList<SOSObservation> tempSOSObservation = new ArrayList<SOSObservation>();
				ArrayList<SOSSensorOutput> aSo = obs.getSensorOutput();
				for (SOSSensorOutput sosSensorOutput : aSo) {
					System.out.println("-Date  --> " + sosSensorOutput.getSamplingTime());
					
					ArrayList<SOSValue> asVal = sosSensorOutput.getValue();
					
					for (SOSValue sosValue : asVal) {
						
						if (sosValue.getForProperty().getUri().toString().toLowerCase().compareTo(selectedFOIs.get(i).getProperty().getProperty().getUri().toString().toLowerCase()) == 0) {
							System.out.println("Prop: "+sosValue.getForProperty().getUri().toString());
							System.out.println("--Value --> " + sosValue.getHasValue());
							System.out.println("--Uom --> " + sosValue.getForProperty().getUom());
							
							SOSObservation tempObs = new SOSObservation();
							tempObs.setFeatureOfInterest(selectedFOIs.get(i).getFoi());
							tempObs.getFeatureOfInterest().setDefaultGeometry(obs.getFeatureOfInterest().getDefaultGeometry());
							SOSValue tempSOSValue = new SOSValue();
							tempSOSValue.setHasValue(sosValue.getHasValue());
							tempSOSValue.setForProperty(sosValue.getForProperty());
							SOSSensorOutput tempSOSSensorOutput = new SOSSensorOutput();
							ArrayList<SOSValue> tempALSOSValue = new ArrayList<SOSValue>();
							tempALSOSValue.add(tempSOSValue);
							tempSOSSensorOutput.setValue(tempALSOSValue);
							tempSOSSensorOutput.setSamplingTime(sosSensorOutput.getSamplingTime());
							ArrayList<SOSSensorOutput> tempALSOSSensorOutput = new ArrayList<SOSSensorOutput>();
							tempALSOSSensorOutput.add(tempSOSSensorOutput);
							tempObs.setSensorOutput(tempALSOSSensorOutput);
							tempSOSObservation.add(tempObs);
						}
					}
				}
				ons.add(tempSOSObservation);
				
				if (viz.toLowerCase().compareTo("color") == 0) {
					Color start = selectedFOIs.get(i).getProperty().getColors()[0];
					Color end = selectedFOIs.get(i).getProperty().getColors()[1];
					int rgbStart = new Color(start.getRed(), start.getGreen(), start.getBlue()).getRGB();
					int rgbEnd = new Color(end.getRed(), end.getGreen(), end.getBlue()).getRGB();
					ArrayList<Integer> colors = getUniqueColors(rgbStart, rgbEnd, tempSOSObservation.size());
					System.out.println(colors.size());
					colorramp.add(colors);
				} else {
					colorramp.add(new ArrayList<Integer>());
				}
				
			}
			
			slider.setMaximum(ons.get(0).size()-1);
			slider.setMinorTickSpacing(1);
			slider.setMajorTickSpacing(1);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.updateUI();
			
	        return 42;
	    }

	    protected void done()
	    {
	        try
	        {
	            ((StatusBarPanel) getMainFrame().getStatusBarPanel()).toggle(false);
	            btnPlay.setEnabled(true);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
}
