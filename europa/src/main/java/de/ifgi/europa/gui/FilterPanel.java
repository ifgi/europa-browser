package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.facade.Facade;
import de.ifgi.europa.settings.GlobalSettings;

/**
 * Represents the query and filter panel on the left side of the window.
 * @author Matthias Pfeil
 *
 */
public class FilterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JButton btnQuery;
	private JTextField textQuery;
	private Facade facade;
	private ArrayList<SOSProperty> properties;
	private ArrayList<SOSFeatureOfInterest> fois = new ArrayList<SOSFeatureOfInterest>();
	private ArrayList<URI> graphs;
	private ArrayList<String> selectedProperties = new ArrayList<String>();
	
	public FilterPanel(MainFrame mF) {
		super(new GridLayout(2,1));
		this.setMainFrame(mF);
		this.setFacade(new Facade());
		
		/**
		 * Filter panel
		 */
	    this.setBorder(BorderFactory.createTitledBorder("SOS"));
	    
	    Object[][] data = {};
	    
	    //Setting up the TableModel for the properties table
	    final DefaultTableModel propertiesTableModel = new DefaultTableModel(data, new String[] {"SOS Properties","Visualization","Visible"}) {
	
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0) {
					return false;
				} else {
					return true;
				}
			}
			
			@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
		};
        
	    final JTable propertiesTable = new JTable(propertiesTableModel);
//	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(new MyRenderer());
//	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
//	    propertiesTable.setCellSelectionEnabled(true);
//	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
	    propertiesTable.setCellSelectionEnabled(true);
	    final CustomRenderer renderer = new CustomRenderer();
	    renderer.setToolTipText("Click for combo box");
	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
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
	    JPanel pnlTables = new JPanel(new BorderLayout());
		pnlQuery.add(new JLabel("URL:"));
		pnlQuery.add(textQuery);
		pnlQuery.add(btnQuery);
		pnlFilter.add(pnlQuery,BorderLayout.PAGE_START);
		pnlFilter.setBorder(BorderFactory.createTitledBorder("Query"));
        propertiesTable.setFillsViewportHeight(true);
        
        pnlTables.add(cbURI, BorderLayout.PAGE_START);
        
        //Create the scroll pane and add the table to it.        
        JScrollPane scrollPanePropertiesTable = new JScrollPane(propertiesTable);
        pnlTables.add(scrollPanePropertiesTable,BorderLayout.CENTER);
        
		pnlFilter.add(pnlTables,BorderLayout.CENTER);
		this.add(pnlFilter);
		
		/**
		 * Result panel
		 */
		JPanel pnlResult = new JPanel(new BorderLayout());
		pnlResult.setBorder(BorderFactory.createTitledBorder("Results"));
		
		//Setting up TableModel for results table
		final DefaultTableModel resultsTableModel = new DefaultTableModel(data, new String[] {"Features of interest","Show"}){

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0) {
					return false;
				} else {
					return true;
				}
			}
			
			@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
		};
		
		final JTable resultsTable = new JTable(resultsTableModel);
		JScrollPane scrollPaneResultsTable = new JScrollPane(resultsTable);
		pnlResult.add(scrollPaneResultsTable);
		this.add(pnlResult);
		
		/**
		 * ActionListener for Query button. On click get all Graphs for
		 * the given SPARQL Endpoint.
		 */
		btnQuery.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * Clear table after querying new endpoint
				 */
				clearTable(propertiesTable);
				clearTable(resultsTable);
				
				cbURI.removeAllItems();
				
				URI uri = null;
				
				try {
					uri = new URI(textQuery.getText());
					GlobalSettings.CurrentSPARQLEndpoint = textQuery.getText(); 
					setGraphs(getFacade().getListGraphs(URI.create(GlobalSettings.CurrentSPARQLEndpoint)));
					
					for (int i = 0; i < getGraphs().size(); i++) {
						cbModel.addElement(getGraphs().get(i).toString());
					}	
				} catch (URISyntaxException e1) {
					System.out.println(e1.toString());
				}
			}
		});
		
		/**
		 * ItemListener for combobox containing all available graphs. On change
		 * clear all tables (properties & results tables) and clear all renderables and
		 * add all available properties to properties table.
		 */
		cbURI.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					clearTable(propertiesTable);
					clearTable(resultsTable);
					((MapPanel) getMainFrame().getMapPanel()).clearGlobe();
					
					GlobalSettings.CurrentNamedGraph = cbModel.getSelectedItem().toString();
					
					setProperties(getFacade().listProperties());
					
					for (int i = 0; i < getProperties().size(); i++) {
						propertiesTableModel.addRow(new Object[] {getProperties().get(i).getUri()});
					}
				}
			}
		});
		
		/**
		 * TableModelListener for properties table. Handles all user interaction inside the
		 * properties table.
		 */
		propertiesTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				
				if (e.getColumn() != -1) {
					TableModel model = (TableModel)e.getSource();
			        String columnName = model.getColumnName(e.getColumn());
			        
			        //If something happens inside the 'Visible' column
					if (columnName == "Visible") {
						if ((Boolean) model.getValueAt(e.getFirstRow(), e.getColumn())) {
							selectedProperties.add(propertiesTable.getValueAt(e.getFirstRow(), 0).toString());
							
							String visualization = "";
							if (propertiesTable.getValueAt(e.getFirstRow(), 1) != null) {
								visualization = propertiesTable.getValueAt(e.getFirstRow(), 1).toString();
							} else {
								visualization = "width";
							}
							
							//Update and add depending FOIs to results table
							updateResultsTable(propertiesTable.getValueAt(e.getFirstRow(), 0).toString(), visualization ,1);
						} else {
							for (int i = 0; i < selectedProperties.size(); i++) {
								if (selectedProperties.get(i).compareTo(propertiesTable.getValueAt(e.getFirstRow(), 0).toString()) == 0) {
									selectedProperties.remove(i);
								}
							}
							
							//Update and remove depending FOIs from results table
							updateResultsTable(propertiesTable.getValueAt(e.getFirstRow(), 0).toString(), "",2);
						}
					}
					//If something happens inside the 'Visualization' column
					if (columnName == "Visualization") {
						if (model.getValueAt(e.getFirstRow(), 2) != null && (Boolean) model.getValueAt(e.getFirstRow(), 2)) {
							//TODO Update resultstabel and updateglobe
//							for (int i = 0; i < resultsTable.getRowCount(); i++) {
//								if ((Boolean) resultsTable.getValueAt(i, 1)) {
//									System.out.println("selected");
//								} else {
//									System.out.println("not selected");
//								}
//							}
//							updateResultsTable(propertiesTable.getValueAt(e.getFirstRow(), 0).toString(), "",2);
//							String visualization = "";
//							if (propertiesTable.getValueAt(e.getFirstRow(), 1) != null) {
//								visualization = propertiesTable.getValueAt(e.getFirstRow(), 1).toString();
//							} else {
//								visualization = "width";
//							}
//							updateResultsTable(propertiesTable.getValueAt(e.getFirstRow(), 0).toString(), visualization ,1);
						}
						if (model.getValueAt(e.getFirstRow(), 1).toString().toLowerCase().compareTo("color") == 0) {
							Color bgColor = JColorChooser.showDialog(getMainFrame(), "Color chooser", getBackground());
							renderer.renderNew(true);
							renderer.setColor(bgColor);
							propertiesTable.repaint();
						}
					}
				}
			}

			/**
			 * Gets all depending FOIs of a selected property and adds these FOIs to the results table.
			 * @param property selected property
			 * @param visualization selected visualization
			 * @param flag 1=add, 2=delete
			 */
			private void updateResultsTable(String property, String visualization ,int flag) {
				if (flag == 1) {
					for (int i = 0; i < getProperties().size(); i++) {
						if (property.compareTo(getProperties().get(i).getUri().toString()) == 0) {
							ArrayList<SOSFeatureOfInterest> availableFOIs = getFacade().listFeaturesOfInterest(getProperties().get(i));
							for (int k = 0; k < availableFOIs.size(); k++) {
								String name = availableFOIs.get(k).getUri().toString();
								availableFOIs.get(k).setLabel(visualization);
								fois.add(availableFOIs.get(k));
								resultsTableModel.addRow(new Object[] {name});
							}
						}
					}
				} else if (flag == 2) {
					if (selectedProperties.size() == 0) {
						((MapPanel) getMainFrame().getMapPanel()).updateGlobe(null, resultsTableModel.getValueAt(0, 0).toString(),"");
						clearTable(resultsTable);
					} else {
						for (int i = 0; i < getProperties().size(); i++) {
							if (property.compareTo(getProperties().get(i).getUri().toString()) == 0) {
								ArrayList<SOSFeatureOfInterest> availableFOIs = getFacade().listFeaturesOfInterest(getProperties().get(i));
								for (int j = 0; j < availableFOIs.size(); j++) {
									for (int k = 0; k < fois.size(); k++) {
										if (fois.get(k).getUri().toString().compareTo(availableFOIs.get(j).getUri().toString()) == 0) {
											fois.remove(k);
										}
									}
									for (int k = 0; k < resultsTable.getRowCount(); k++) {
										if (resultsTable.getValueAt(k, 0).toString().compareTo(availableFOIs.get(j).getUri().toString()) == 0) {
											((MapPanel) getMainFrame().getMapPanel()).updateGlobe(null, resultsTableModel.getValueAt(k, 0).toString(),"");
											resultsTableModel.removeRow(k);
											resultsTableModel.fireTableDataChanged();
										}
									}
								}
							}
						}
					}
				} 
			}
		});
		
		/**
		 * TableModelListener for results table. Handles all user interaction inside the
		 * results table and updates the WorldWindowGLCanvas
		 */
		resultsTable.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() != -1) {
					if ((Boolean) resultsTableModel.getValueAt(e.getFirstRow(), 1)) {
						for (int i = 0; i < fois.size(); i++) {
							if (fois.get(i).getUri().toString().compareTo(resultsTableModel.getValueAt(e.getFirstRow(), 0).toString()) == 0) {
								SOSFeatureOfInterest foi = fois.get(i);
								SOSObservation observation = getFacade().getFOILastObservation(foi);
								observation.getFeatureOfInterest().setName(resultsTableModel.getValueAt(e.getFirstRow(), 0).toString());
								((MapPanel) getMainFrame().getMapPanel()).updateGlobe(observation,"",fois.get(i).getLabel().toLowerCase());
							}
						}
					} else {
						((MapPanel) getMainFrame().getMapPanel()).updateGlobe(null, resultsTableModel.getValueAt(e.getFirstRow(), 0).toString(),"");
					}	
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

	public ArrayList<SOSFeatureOfInterest> getFOIs() {
		return this.fois;
		
	}
	
	/**
	 * Custom TableCellRenderer to set background color of visualization cloumn if the user selects
	 * color option.
	 * @author Matthias Pfeil
	 *
	 */
	public static class CustomRenderer extends DefaultTableCellRenderer {
	    private boolean render = false;
	    private Color color = null;
	    
	    public void renderNew(boolean render) {
	    	this.render = render;
	    }
	    
	    public void setColor(Color color) {
	    	this.color = color;
	    }
	    
	    public Color getColor() {
	    	return this.color;
	    }
	    
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			setOpaque(true);
		
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			
			if (render) {
				System.out.println(row +" "+column);
				
				if (value != null) {
					
					if (value.toString().compareTo("Color") == 0) {
//						if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
							System.out.println(row + " "+ column);
							setText(value.toString());
							c.setBackground(getColor());
							c.setForeground(Color.black);
//						} else {
//							System.out.println("Color but not selected row: "+row +" "+column);
////							Color tempColor = c.getBackground();
////							setText(value.toString());
////							c.setBackground(tempColor);
////							c.setForeground(color.black);
//						}
//						System.out.println("Row: "+row+" Column: "+column+" Color: "+c.getBackground());
//						setText(value.toString());
//						c.setBackground(getColor());
//						c.setForeground(Color.black);
					} else {
						System.out.println("no color row & column");
						setText(value.toString());
						c.setBackground(Color.white);
						c.setForeground(Color.black);
					}
				} else {
					c.setBackground(Color.white);
					c.setForeground(Color.black);
				}
			}
			
	        return c;
		}
	}
}