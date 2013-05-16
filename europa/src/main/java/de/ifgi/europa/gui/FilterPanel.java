package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
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
import de.ifgi.europa.settings.GlobalSettings;

public class FilterPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JButton btnQuery;
	private JTextField textQuery;
	private Facade facade;
	private ArrayList<SOSProperty> properties;
	private ArrayList<SOSFeatureOfInterest> fois = new ArrayList<SOSFeatureOfInterest>();
	private ArrayList<URI> graphs;
	
	public FilterPanel(MainFrame mF) {
		super(new GridLayout(2,1));
		this.setMainFrame(mF);
		this.setFacade(new Facade());
		
		/**
		 * Filter panel
		 */
	    this.setBorder(BorderFactory.createTitledBorder("SOS"));
	    
	    Object[][] data = {};
	    
//	    final DefaultTableModel graphsTableModel = new DefaultTableModel(data, new String[] {"Graphs"}) {
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public boolean isCellEditable(int row, int column) {
//				if(column == 0) {
//					return false;
//				} else {
//					return true;
//				}
//			}
//		};
//	    final JTable graphsTable = new JTable(graphsTableModel);
	    
	    final DefaultTableModel propertiesTableModel = new DefaultTableModel(data, new String[] {"SOS Properties","Visualization"}) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		final JComboBox comboBox = new JComboBox();
		comboBox.addItem("Color");
		comboBox.addItem("Width");
		comboBox.addItem("Height");
		visualizationColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		final DefaultComboBoxModel cbModel = new DefaultComboBoxModel();
		final JComboBox cbURI = new JComboBox(cbModel);
		
	    btnQuery = new JButton("GO!");
	    textQuery = new JTextField("");
	    textQuery.setColumns(25);
	    JPanel pnlFilter = new JPanel(new BorderLayout());
	    JPanel pnlQuery = new JPanel(new FlowLayout());
//	    JPanel pnlTables = new JPanel(new GridLayout(2, 1));
	    JPanel pnlTables = new JPanel(new BorderLayout());
		pnlQuery.add(new JLabel("URL:"));
		pnlQuery.add(textQuery);
		pnlQuery.add(btnQuery);
		pnlFilter.add(pnlQuery,BorderLayout.PAGE_START);
		pnlFilter.setBorder(BorderFactory.createTitledBorder("Query"));
        propertiesTable.setFillsViewportHeight(true);
        
        pnlTables.add(cbURI, BorderLayout.PAGE_START);
        
//        JScrollPane scrollPaneGraphTable = new JScrollPane(graphsTable);
//		pnlFilter.add(scrollPaneGraphTable);
//        pnlTables.add(scrollPaneGraphTable);
        
        //Create the scroll pane and add the table to it.        
        JScrollPane scrollPanePropertiesTable = new JScrollPane(propertiesTable);
//        pnlFilter.add(scrollPanePropertiesTable);
//        pnlTables.add(scrollPanePropertiesTable);
        pnlTables.add(scrollPanePropertiesTable,BorderLayout.CENTER);
		pnlFilter.add(pnlTables,BorderLayout.CENTER);
		this.add(pnlFilter);
		
		/**
		 * Result panel
		 */
		JPanel pnlResult = new JPanel(new BorderLayout());
		pnlResult.setBorder(BorderFactory.createTitledBorder("Results"));
		final DefaultTableModel resultsTableModel = new DefaultTableModel(data, new String[] {"Features of interest"}){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
	//				clearTable(graphsTable);
					clearTable(propertiesTable);
					clearTable(resultsTable);
					
					cbURI.removeAllItems();
					
					URI uri = null;
					
					try {
						uri = new URI(textQuery.getText());
						GlobalSettings.CurrentSPARQLEndpoint = textQuery.getText(); 
						setGraphs(getFacade().getListGraphs(URI.create(GlobalSettings.CurrentSPARQLEndpoint)));
						
						for (int i = 0; i < getGraphs().size(); i++) {
	//						graphsTableModel.addRow(new Object[] {getGraphs().get(i).toString()});
							
							cbModel.addElement(getGraphs().get(i).toString());
						}
						
						
						
					} catch (URISyntaxException e1) {
						System.out.println(e1.toString());
					}
				}
			});
		
		cbURI.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					clearTable(propertiesTable);
					
					System.out.println(cbModel.getSelectedItem().toString());
					GlobalSettings.CurrentNamedGraph = cbModel.getSelectedItem().toString();
					
					setProperties(getFacade().listProperties());
					
					for (int i = 0; i < getProperties().size(); i++) {
						propertiesTableModel.addRow(new Object[] {getProperties().get(i).getUri()});
					}
				}
				
			}
		});
		
//	graphsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//		
//		@Override
//		public void valueChanged(ListSelectionEvent arg0) {
//			
//			clearTable(propertiesTable);
//			GlobalSettings.CurrentNamedGraph = graphsTable.getValueAt(graphsTable.getSelectedRow(), 0).toString();
//			setProperties(getFacade().listProperties());
//			
//			for (int i = 0; i < getProperties().size(); i++) {
//				propertiesTableModel.addRow(new Object[] {getProperties().get(i).getUri()});
//			}
//			
//		}
//	});
		
		propertiesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				/**
				 * Clear table after changes in table of properties
				 */
				clearTable(resultsTable);
				
				ArrayList<String> selection = new ArrayList<String>();
				fois.clear();
				for(int row : propertiesTable.getSelectedRows()) {
					selection.add(propertiesTable.getValueAt(row, 0).toString());
				}
				
				for (int i = 0; i < selection.size(); i++) {
					for (int j = 0; j < getProperties().size(); j++) {
						if (selection.get(i).compareTo(getProperties().get(j).getUri().toString()) == 0) {
							ArrayList<SOSFeatureOfInterest> availableFOIs = getFacade().listFeaturesOfInterest(getProperties().get(j));
							for (int k = 0; k < availableFOIs.size(); k++) {
								String name = availableFOIs.get(k).getUri().toString();
								fois.add(availableFOIs.get(k));
								resultsTableModel.addRow(new Object[] {name});
							}
						}
					}
				}
			}
		});
		
		resultsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				((MapPanel) mainFrame.getMapPanel()).clearMarkers();
				
				for(int row : resultsTable.getSelectedRows()) {
					SOSFeatureOfInterest foi = fois.get(row);
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
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		dm.getDataVector().removeAllElements();
		dm.fireTableDataChanged();
	}

	/**
	 * @return the graphs
	 */
	public ArrayList<URI> getGraphs() {
		return graphs;
	}

	/**
	 * @param graphs the graphs to set
	 */
	public void setGraphs(ArrayList<URI> graphs) {
		this.graphs = graphs;
	}

}
