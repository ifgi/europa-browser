package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import de.ifgi.europa.facade.Facade;

public class FilterPanel extends JPanel {
	
	private MainFrame mainFrame;
	private JButton btnQuery;
	private JTextField textQuery;
	
	public FilterPanel(MainFrame mF) {
		super(new GridLayout(2,1));
		this.setMainFrame(mF);
	    this.setBorder(BorderFactory.createTitledBorder("SOS"));
	    
	    String[] columnNames = {"SOS Properties"};
	    
	    Object[][] data = {
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			    {"Temperature"},
			    {"Watertemperature"},
			};
		
		JTable table = new JTable(data, columnNames);
	    btnQuery = new JButton("GO!");
	    textQuery = new JTextField("asdasdasd");
	    textQuery.setColumns(20);
		JPanel pnlFilter = new JPanel(new BorderLayout());
		JPanel pnlQuery = new JPanel(new FlowLayout());
		pnlQuery.add(new JLabel("URL:"));
		pnlQuery.add(textQuery);
		pnlQuery.add(btnQuery);
		pnlFilter.add(pnlQuery,BorderLayout.PAGE_START);
		pnlFilter.setBorder(BorderFactory.createTitledBorder("Query"));
        table.setFillsViewportHeight(true);
                 
        //Create the scroll pane and add the table to it.        
        JScrollPane scrollPaneTable = new JScrollPane(table);
        pnlFilter.add(scrollPaneTable);
		
//		JPanel pnlExtent = new JPanel(new BorderLayout());
//		pnlExtent.add(new JTextField(),BorderLayout.PAGE_START);
//	    pnlExtent.add(new JTextField(),BorderLayout.LINE_START);
//	    pnlExtent.add(new JTextField(),BorderLayout.LINE_END);
//	    pnlExtent.add(new JTextField(),BorderLayout.PAGE_END);
//		pnlFilter.add(pnlExtent,BorderLayout.CENTER);
		
		this.add(pnlFilter);
//		this.add(scrollPaneTable);
		
		JPanel pnlResult = new JPanel(new BorderLayout());
		pnlResult.setBorder(BorderFactory.createTitledBorder("Results"));
		this.add(pnlResult);
		
		btnQuery.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println(textQuery.getText());
				Facade fac = new Facade();
				URI uri = null;
				try {
					uri = new URI(textQuery.getText());
				} catch (URISyntaxException e1) {
					System.out.println(e1.toString());
				}
//				fac.getResourceAttributes(uri);
//				fac.listProperties();
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
