package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.facade.Facade;

public class FilterPanel extends JPanel {
	
	private MainFrame mainFrame;
	private JButton btnQuery;
	private JTextField textQuery;
	private Facade facade;
	private ArrayList<SOSProperty> properties;
	private ArrayList<SOSFeatureOfInterest> fois;
	
	public FilterPanel(MainFrame mF) {
		super(new GridLayout(2,1));
		this.setMainFrame(mF);
		this.setFacade(new Facade());
		
		/**
		 * Filter panel
		 */
	    this.setBorder(BorderFactory.createTitledBorder("SOS"));
	    
	    Object[][] data = {};
	    
	    final DefaultTableModel propertiesTableModel = new DefaultTableModel(data, new String[] {"SOS Properties","Visualization"}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0) {
					return false;
				} else {
					return true;
				}
			}
		};
	    final JTable propertiesTable = new JTable(propertiesTableModel);
	    
	    TableColumn visualizationColumn = propertiesTable.getColumnModel().getColumn(1);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Color");
		comboBox.addItem("Width");
		comboBox.addItem("Height");
		visualizationColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
	    btnQuery = new JButton("GO!");
	    textQuery = new JTextField("");
	    textQuery.setColumns(20);
		JPanel pnlFilter = new JPanel(new BorderLayout());
		JPanel pnlQuery = new JPanel(new FlowLayout());
		pnlQuery.add(new JLabel("URL:"));
		pnlQuery.add(textQuery);
		pnlQuery.add(btnQuery);
		pnlFilter.add(pnlQuery,BorderLayout.PAGE_START);
		pnlFilter.setBorder(BorderFactory.createTitledBorder("Query"));
        propertiesTable.setFillsViewportHeight(true);
                 
        //Create the scroll pane and add the table to it.        
        JScrollPane scrollPanePropertiesTable = new JScrollPane(propertiesTable);
        pnlFilter.add(scrollPanePropertiesTable);
		this.add(pnlFilter);

		/**
		 * Result panel
		 */
		JPanel pnlResult = new JPanel(new BorderLayout());
		pnlResult.setBorder(BorderFactory.createTitledBorder("Results"));
		final DefaultTableModel resultsTableModel = new DefaultTableModel(data, new String[] {"Features of interest"}){
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0) {
					return false;
				} else {
					return true;
				}
			}
		};
		final JTable resultsTable = new JTable(resultsTableModel);
		JScrollPane scrollPaneResultsTable = new JScrollPane(resultsTable);
		pnlResult.add(scrollPaneResultsTable);
		this.add(pnlResult);
		
		btnQuery.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * Clear table after querying new endpoint
				 */
				clearTable(propertiesTable);
				
				URI uri = null;
				
				try {
					uri = new URI(textQuery.getText());
				} catch (URISyntaxException e1) {
					System.out.println(e1.toString());
				}
				setProperties(getFacade().listProperties());
				
				for (int i = 0; i < getProperties().size(); i++) {
					propertiesTableModel.addRow(new Object[] {getProperties().get(i).getUri()});
				}
				
			}
		});
		
		propertiesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				/**
				 * Clear table after changes in table of properties
				 */
				clearTable(resultsTable);
				
				String selected = propertiesTable.getValueAt(propertiesTable.getSelectedRow(), 0).toString();
				
				for (int i = 0; i < getProperties().size(); i++) {
					if(getProperties().get(i).getUri().toString().compareTo(selected) == 0) {
						fois = getFacade().listFeaturesOfInterest(getProperties().get(i));
						for (int j = 0; j < fois.size(); j++) {
							String name = fois.get(j).getName();
							resultsTableModel.addRow(new Object[] {name});
						}
					}
				}
			}
		});
		
		resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				for(int row : resultsTable.getSelectedRows()) {
					SOSFeatureOfInterest foi = fois.get(row);
					foi.setUri(URI.create(foi.getName()));
					SOSObservation observation = getFacade().getFOILastObservation(foi);
					((MapPanel) mainFrame.getMapPanel()).updateGlobe(observation);
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

	/**
	 * @return the facade
	 */
	public Facade getFacade() {
		return facade;
	}

	/**
	 * @param facade the facade to set
	 */
	public void setFacade(Facade facade) {
		this.facade = facade;
	}

	/**
	 * @return the properties
	 */
	public ArrayList<SOSProperty> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(ArrayList<SOSProperty> properties) {
		this.properties = properties;
	}
	
	/**
	 * Clears all table entries.
	 * @param table table to clear
	 */
	public void clearTable(JTable table) {
		for (int i = table.getModel().getRowCount() - 1; i >= 0 ; i--) {
			((DefaultTableModel) table.getModel()).removeRow(i);
		}
	}

}
