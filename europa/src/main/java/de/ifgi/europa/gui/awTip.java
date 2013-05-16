package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePicker;

public class awTip extends JPanel {
//	Image imgPlay = null;
    public awTip()
    {
    	super(true);
		this.setLayout(new BorderLayout());
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		final JButton btnPlay = new JButton("Play");
//		try {
//			Image imgPlay = ImageIO.read(getClass().getResource("resources/play.png"));
//			btnPlay.setIcon(new ImageIcon(imgPlay.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		JDatePicker dpFrom;
		dpFrom = JDateComponentFactory.createJDatePicker();
		dpFrom.setTextEditable(true);
		dpFrom.setShowYearButtons(true);
		
		JDatePicker dpUntil;
		dpUntil = JDateComponentFactory.createJDatePicker();
		dpUntil.setTextEditable(true);
		dpUntil.setShowYearButtons(true);
		
		JPanel pnlTime = new JPanel(new GridLayout(2, 1));
		
		slider.setMinorTickSpacing(2);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
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
}
