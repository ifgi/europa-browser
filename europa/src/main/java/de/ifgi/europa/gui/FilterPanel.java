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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
	private JButton btnUnselectAll;
	private JButton btnSelectAll;
	private JTextField txtSPARQLEndpoint;
	private Facade facade;
	private ArrayList<URI> graphs;
	private ArrayList<Properties> properties;
	private ArrayList<FeaturesOnTheGlobe> foisOnTheGlobe;
	final DefaultTableModel resultsTableModel;
	final JTable resultsTable;
	final DefaultTableModel propertiesTableModel;
	final JTable propertiesTable;
	final DefaultComboBoxModel cbModel;
	
	ImageIcon iconSpin, iconConnect, iconSelect, iconUnselect;
	
	public FilterPanel(MainFrame mF) {
		super(new GridLayout(2,1));
		this.setMainFrame(mF);
		this.setFacade(new Facade());
		
		properties = new ArrayList<Properties>();
		foisOnTheGlobe = new ArrayList<FilterPanel.FeaturesOnTheGlobe>();
		
		Image imgSpinner = Toolkit.getDefaultToolkit().createImage("spinner.gif");
		Image imgConnect = Toolkit.getDefaultToolkit().createImage("connect2.png");
		Image imgSelect = Toolkit.getDefaultToolkit().createImage("select.png");
		Image imgUnselect = Toolkit.getDefaultToolkit().createImage("unselect.png");
        iconSpin = new ImageIcon(imgSpinner);
        iconConnect = new ImageIcon(imgConnect.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH));
        iconSelect = new ImageIcon(imgSelect.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH));
        iconUnselect = new ImageIcon(imgUnselect.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH));
        
		/**
		 * Filter panel
		 */
	    this.setBorder(BorderFactory.createTitledBorder("Triple Store"));
	    
	    Object[][] data = {};
	    
	    //Setting up the TableModel for the properties table
	    propertiesTableModel = new DefaultTableModel(data, new String[] {"SOS Properties","Visualization","Visible"}) {
	
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
        
	    propertiesTable = new JTable(propertiesTableModel);
//	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(new MyRenderer());
//	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
//	    propertiesTable.setCellSelectionEnabled(true);
//	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
	    propertiesTable.setCellSelectionEnabled(true);
//	    final CustomRenderer renderer = new CustomRenderer();
//	    renderer.setToolTipText("Click for combo box");
//	    propertiesTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
	    TableColumn visualizationColumn = propertiesTable.getColumnModel().getColumn(1);
		final JComboBox comboBox = new JComboBox();
		comboBox.addItem("Color");
		comboBox.addItem("Width");
		comboBox.addItem("Height");
		visualizationColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		cbModel = new DefaultComboBoxModel();
		final JComboBox cbURI = new JComboBox(cbModel);
		
		btnQuery = new JButton();
		btnQuery.setEnabled(true);
		
		btnQuery.setIcon(iconConnect);
	    
		txtSPARQLEndpoint = new JTextField("http://giv-siidemo.uni-muenster.de:8081/parliament/sparql");
		txtSPARQLEndpoint.setColumns(20);
	    
	    JPanel pnlFilter = new JPanel(new BorderLayout());
	    JPanel pnlQuery = new JPanel(new FlowLayout());
	    JPanel pnlGraphs = new JPanel(new FlowLayout());
	    JPanel pnlTables = new JPanel(new BorderLayout());
	    cbURI.setPrototypeDisplayValue("                                                                             ");
		pnlQuery.add(new JLabel("SPARQL Endpoint:"),BorderLayout.PAGE_START);
		pnlQuery.add(txtSPARQLEndpoint,BorderLayout.CENTER);
		pnlQuery.add(btnQuery,BorderLayout.PAGE_END);
		pnlGraphs.add(new JLabel("Graphs:"),BorderLayout.PAGE_START);
		pnlGraphs.add(cbURI,BorderLayout.CENTER);
		pnlFilter.add(pnlQuery,BorderLayout.PAGE_START);
		pnlFilter.setBorder(BorderFactory.createTitledBorder("Query"));
        propertiesTable.setFillsViewportHeight(true);
        
        pnlTables.add(pnlGraphs, BorderLayout.PAGE_START);
        
        //Create the scroll pane and add the table to it.
//        JLayeredPane layeredPane = new JLayeredPane();
//        pnlTables.add(layeredPane,BorderLayout.CENTER);
//        JPanel pnlLayeredPaneTable = new JPanel(new BorderLayout());
//        JScrollPane scrollPanePropertiesTable = new JScrollPane(propertiesTable);
//        pnlLayeredPaneTable.add(scrollPanePropertiesTable, BorderLayout.CENTER);
//        pnlLayeredPaneTable.setBounds(50, 50, 200, 200);
//        pnlLayeredPaneTable.setOpaque(true);
//        
//        layeredPane.add(pnlLayeredPaneTable, new Integer(0), 0);
        
        
        JScrollPane scrollPanePropertiesTable = new JScrollPane(propertiesTable);
        pnlTables.add(scrollPanePropertiesTable,BorderLayout.CENTER);
        
        
		pnlFilter.add(pnlTables,BorderLayout.CENTER);
		this.add(pnlFilter);
		
		txtSPARQLEndpoint.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if (txtSPARQLEndpoint.getText().length() > 0) {
					btnQuery.setEnabled(true);
				} else {
					btnQuery.setEnabled(false);
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if (txtSPARQLEndpoint.getText().length() > 0) {
					btnQuery.setEnabled(true);
				} else {
					btnQuery.setEnabled(false);
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				System.out.println("changed");
			}
		});
		
		/**
		 * Result panel
		 */
		JPanel pnlResult = new JPanel(new BorderLayout());
		pnlResult.setBorder(BorderFactory.createTitledBorder("Results"));
		btnSelectAll = new JButton("Select all", iconSelect);
		btnUnselectAll = new JButton("Unselect all", iconUnselect);
		JPanel pnlTableOptions = new JPanel(new GridLayout(1,2));
		pnlTableOptions.add(btnSelectAll);
		pnlTableOptions.add(btnUnselectAll);
		pnlResult.add(pnlTableOptions,BorderLayout.PAGE_START);
		
		//Setting up TableModel for results table final DefaultTableModel 
		resultsTableModel = new DefaultTableModel(data, new String[] {"Features of interest","Show"}){
				
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
		
		resultsTable = new JTable(resultsTableModel);
		JScrollPane scrollPaneResultsTable = new JScrollPane(resultsTable);
		pnlResult.add(scrollPaneResultsTable,BorderLayout.CENTER);
		this.add(pnlResult);
		
		btnSelectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < resultsTable.getRowCount(); i++) {
					if (resultsTable.getModel().getValueAt(i, 1) == null || !(Boolean) resultsTable.getModel().getValueAt(i, 1)) {
						resultsTable.getModel().setValueAt(true, i, 1);
					}
				}
			}
		});
		
		btnUnselectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((MapPanel) getMainFrame().getMapPanel()).clearGlobe();
				for (int i = 0; i < resultsTable.getRowCount(); i++) {
					if ((Boolean) resultsTable.getModel().getValueAt(i, 1)) {
						resultsTable.getModel().setValueAt(false, i, 1);
					}
				}
				
			}
		});
		
		
		/**
		 * ActionListener for Query button. On click get all Graphs for
		 * the given SPARQL Endpoint.
		 */
		btnQuery.addActionListener(new ActionListener() {
				
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * Clear table after querying new endpoint
				 */
				btnQuery.setIcon(iconSpin);
				clearTable(propertiesTable);
				clearTable(resultsTable);
				
				//Delete everything from properties if the SPARQL Endpoint changes
				properties.clear();
				
				cbURI.removeAllItems();
				
				URI uri = null;
				
				try {
					uri = new URI(txtSPARQLEndpoint.getText());
					GlobalSettings.CurrentSPARQLEndpoint = txtSPARQLEndpoint.getText(); 
					setGraphs(getFacade().getListGraphs(URI.create(GlobalSettings.CurrentSPARQLEndpoint)));
					
					for (int i = 0; i < getGraphs().size(); i++) {
						cbModel.addElement(getGraphs().get(i).toString());
					}
				} catch (URISyntaxException e1) {
					System.out.println(e1.toString());
				}
				
				btnQuery.setIcon(iconConnect);
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
					((StatusBarPanel) getMainFrame().getStatusBarPanel()).toggle(true);
					
					new AnswerWorker().execute();
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
				
				String visualization = "";
				
				if (e.getColumn() != -1) {
					TableModel model = (TableModel)e.getSource();
			        String columnName = model.getColumnName(e.getColumn());
			        
			        //If something happens inside the 'Visible' column
					if (columnName == "Visible") {
						if ((Boolean) model.getValueAt(e.getFirstRow(), e.getColumn())) {
							
							if (propertiesTable.getValueAt(e.getFirstRow(), 1) != null) {
								visualization = propertiesTable.getValueAt(e.getFirstRow(), 1).toString();
							} else {
								visualization = "width";
							}			
							
							checkSelectedProperties(model.getValueAt(e.getFirstRow(), 0).toString(), visualization, null, true, 1);
						} else {
							
							if (propertiesTable.getValueAt(e.getFirstRow(), 1) != null) {
								visualization = propertiesTable.getValueAt(e.getFirstRow(), 1).toString();
							} else {
								visualization = "width";
							}
							
							checkSelectedProperties(model.getValueAt(e.getFirstRow(), 0).toString(), visualization, null, false, 2);
						}
					}
					//If something happens inside the 'Visualization' column
					if (columnName == "Visualization") {
						if (model.getValueAt(e.getFirstRow(), 1).toString().toLowerCase().compareTo("color") == 0) {
							ColorDialog cd = new ColorDialog(getMainFrame(), "Color range chooser");  
							cd.setVisible(true);
							Color[] colors = cd.getColors();
							cd.setVisible(false);
							checkSelectedProperties(model.getValueAt(e.getFirstRow(), 0).toString(), "color", colors, ((Boolean) model.getValueAt(e.getFirstRow(), 2)!=null) ? (Boolean) model.getValueAt(e.getFirstRow(), 2) : false, 0);
						} else {
							if (propertiesTable.getValueAt(e.getFirstRow(), 1) != null) {
								visualization = propertiesTable.getValueAt(e.getFirstRow(), 1).toString();
							} else {
								visualization = "width";
							}
							
							checkSelectedProperties(model.getValueAt(e.getFirstRow(), 0).toString(), visualization, null, ((Boolean) model.getValueAt(e.getFirstRow(), 2)!=null) ? (Boolean) model.getValueAt(e.getFirstRow(), 2) : false, 0);
						}
					}
				}
			}
			
			
			//DEPRECATED will be deleted
			/**
			 * Gets all depending FOIs of a selected property and adds these FOIs to the results table.
			 * @param property selected property
			 * @param visualization selected visualization
			 * @param flag 1=add, 2=delete
			 */
			private void updateResultsTable(String property, String visualization ,int flag) {
				if (flag == 1) {
//					for (int i = 0; i < getProperties().size(); i++) {
//						if (property.compareTo(getProperties().get(i).getUri().toString()) == 0) {
//							ArrayList<SOSFeatureOfInterest> availableFOIs = getFacade().listFeaturesOfInterest(getProperties().get(i));
//							for (int k = 0; k < availableFOIs.size(); k++) {
//								String name = availableFOIs.get(k).getUri().toString();
//								availableFOIs.get(k).setIdentifier(property.toLowerCase());
//								fois.add(availableFOIs.get(k));
//								resultsTableModel.addRow(new Object[] {name});
//							}
//						}
//					}
				} else if (flag == 2) {
					Boolean somethingVisible = false;
//					for(SelectedProperties prop : props) {
//						if (prop.isVisible()) {
//							somethingVisible = true;
//							break;
//						}
//					}
					if (!somethingVisible) {
						clearTable(resultsTable);
						((MapPanel) getMainFrame().getMapPanel()).clearGlobe();
					} else {
//						for (int i = 0; i < getProperties().size(); i++) {
//							if (property.compareTo(getProperties().get(i).getUri().toString()) == 0) {
//								ArrayList<SOSFeatureOfInterest> availableFOIs = getFacade().listFeaturesOfInterest(getProperties().get(i));
//								for (int j = 0; j < availableFOIs.size(); j++) {
//									for (int k = 0; k < fois.size(); k++) {
//										if (fois.get(k).getUri().toString().compareTo(availableFOIs.get(j).getUri().toString()) == 0) {
//											fois.remove(k);
//										}
//									}
//									for (int k = 0; k < resultsTable.getRowCount(); k++) {
//										if (resultsTable.getValueAt(k, 0).toString().compareTo(availableFOIs.get(j).getUri().toString()) == 0) {
//											((MapPanel) getMainFrame().getMapPanel()).updateGlobe(null, availableFOIs.get(j), null);
//											resultsTableModel.removeRow(k);
//											resultsTableModel.fireTableDataChanged();
//										}
//									}
//								}
//							}
//						}
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
					String selectedFOI = resultsTableModel.getValueAt(e.getFirstRow(), 0).toString();
					String[] arrSelectedFOI = selectedFOI.split("\\-");
					
					if ((Boolean) resultsTableModel.getValueAt(e.getFirstRow(), 1)) {	
						for (Properties prop : properties) {
							if (prop.getProperty().getUri().toString().toLowerCase().contains(arrSelectedFOI[0].toLowerCase())) {
								for (SOSFeatureOfInterest foi : prop.getProperty().getFoi()) {
									if (foi.getUri().toString().toLowerCase().contains(arrSelectedFOI[1].toLowerCase())) {
										SOSObservation observation = getFacade().getFOILastObservation(foi);
										((MapPanel) getMainFrame().getMapPanel()).updateGlobe(observation,foi,prop,selectedFOI);
										foisOnTheGlobe.add(new FeaturesOnTheGlobe(foi, prop));
										break;
									}
								}
							}
						}
						System.out.println(foisOnTheGlobe.size());
					} else {
						for (Properties prop : properties) {
							if (prop.getProperty().getUri().toString().toLowerCase().contains(arrSelectedFOI[0].toLowerCase())) {
								for (SOSFeatureOfInterest foi : prop.getProperty().getFoi()) {
									if (foi.getUri().toString().toLowerCase().contains(arrSelectedFOI[1].toLowerCase())) {
										((MapPanel) getMainFrame().getMapPanel()).updateGlobe(null,foi,prop,selectedFOI);
										for (int i = 0; i < foisOnTheGlobe.size(); i++) {
											if (foisOnTheGlobe.get(i).getFoi().getUri().toString().toLowerCase().compareTo(foi.getUri().toString().toLowerCase()) == 0) {
												foisOnTheGlobe.remove(i);
											}
										}
									}
								}
							}
						}
						System.out.println(foisOnTheGlobe.size());
					}	
				}
			}
		});
	}
	
	/**
	 * 
	 */
	public void checkSelectedProperties(String url, String viz, Color[] colors, boolean visible, int flag) {
		int found = 0;
		for(Properties prop : properties) {
			if (url.toLowerCase().compareTo(prop.getProperty().getUri().toString().toLowerCase()) == 0) {
				found = 1;
				prop.setVisualization(viz);
				if (colors != null) {
					prop.setColors(colors);
				}
				prop.setVisible(visible);
				
				if (flag == 1) {
					for (int k = 0; k < prop.getProperty().getFoi().size(); k++) {
						String name = prop.getProperty().getFoi().get(k).getUri().toString();
						String[] arrName = name.split("\\#");
						prop.getProperty().getFoi().get(k).setIdentifier(prop.getProperty().getUri().toString().toLowerCase());
						String tempUri = prop.getProperty().getUri().toString();
						String[] arrUri = tempUri.split("\\#");
						resultsTableModel.addRow(new Object[] {arrUri[1]+"-"+arrName[1]});
					}
				} else if(flag == 2) {
					Boolean someVisible = false;
					for(Properties tempProp : properties) {
						if (tempProp.getVisible()) {
							someVisible = true;
							break;
						}
					}
					if (!someVisible) {
						clearTable(resultsTable);
						((MapPanel) getMainFrame().getMapPanel()).clearGlobe();
					} else {
						String tempUri = prop.getProperty().getUri().toString();
						String[] arrUri = tempUri.split("\\#");
						System.out.println(resultsTable.getRowCount());
						System.out.println(resultsTableModel.getRowCount());
						//funzt noch nicht
						for (int k = 0; k < resultsTable.getRowCount(); k++) {
//								if (resultsTable.getValueAt(k, 0).toString().compareTo(availableFOIs.get(j).getUri().toString()) == 0) {
//									((MapPanel) getMainFrame().getMapPanel()).updateGlobe(null, availableFOIs.get(j), null);
//									resultsTableModel.removeRow(k);
//									resultsTableModel.fireTableDataChanged();
//								}
						}
					}
				}
			}
			if (found == 1) {
				break;
			}
		}
	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
	public ArrayList<FeaturesOnTheGlobe> getFoisOnTheGlobe() {
		return foisOnTheGlobe;
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
	
	/**
	 * Inner class for storing selected properties with their
	 * visualization type and if color, the selected colors.
	 * @author Matthias Pfeil
	 *
	 */
	public class Properties {
		
		SOSProperty property;
		String visualization;
		Color[] colors;
		Boolean visible;
		
		public Properties(SOSProperty prop, String viz, Color[] colors, Boolean visible) {
			this.property = prop;
			this.visualization = viz;
			this.colors = colors;
			this.visible = visible;
		}

		public SOSProperty getProperty() {
			return property;
		}

		public void setProperty(SOSProperty property) {
			this.property = property;
		}

		public String getVisualization() {
			return visualization;
		}

		public void setVisualization(String visualization) {
			this.visualization = visualization;
		}

		public Color[] getColors() {
			return colors;
		}

		public void setColors(Color[] colors) {
			this.colors = colors;
		}

		public Boolean getVisible() {
			return visible;
		}

		public void setVisible(Boolean visible) {
			this.visible = visible;
		}
		
		
		
	}

	
	public class FeaturesOnTheGlobe {
		
		private SOSFeatureOfInterest foi;
		private Properties property;
		
		public FeaturesOnTheGlobe(SOSFeatureOfInterest foi, Properties property) {
			this.foi = foi;
			this.property = property;
		}
		
		public SOSFeatureOfInterest getFoi() {
			return foi;
		}

		public void setFoi(SOSFeatureOfInterest foi) {
			this.foi = foi;
		}

		public Properties getProperty() {
			return property;
		}

		public void setProperty(Properties property) {
			this.property = property;
		}
	}
	
	class AnswerWorker extends SwingWorker<Integer, Integer>
	{
		
	    protected Integer doInBackground() throws Exception
	    {
	    	clearTable(propertiesTable);
			clearTable(resultsTable);
			properties.clear();	
			
			((MapPanel) getMainFrame().getMapPanel()).clearGlobe();
			
			GlobalSettings.CurrentNamedGraph = cbModel.getSelectedItem().toString();
			
			//Fill list with properties and add the fois to the property
			for(SOSProperty prop : getFacade().listProperties()){
				
				ArrayList<SOSFeatureOfInterest> foi = getFacade().listFeaturesOfInterest(prop);
				prop.setFoi(foi);
				properties.add(new Properties(prop, "", null, false));
				
				propertiesTableModel.addRow(new Object[] {prop.getUri()});
			}
			
	        return 42;
	    }

	    protected void done()
	    {
	        try
	        {
	            System.out.println("finished");
	            ((StatusBarPanel) getMainFrame().getStatusBarPanel()).toggle(false);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}
	
//	
//	/**
//	 * Custom TableCellRenderer to set background color of visualization cloumn if the user selects
//	 * color option.
//	 * @author Matthias Pfeil
//	 *
//	 */
//	public static class CustomRenderer extends DefaultTableCellRenderer {
//	    private boolean render = false;
//	    private Color color = null;
//	    
//	    public void renderNew(boolean render) {
//	    	this.render = render;
//	    }
//	    
//	    public void setColor(Color color) {
//	    	this.color = color;
//	    }
//	    
//	    public Color getColor() {
//	    	return this.color;
//	    }
//	    
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
//			setOpaque(true);
//		
//			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//			
//			
//			if (render) {
//				System.out.println(row +" "+column);
//				
//				if (value != null) {
//					
//					if (value.toString().compareTo("Color") == 0) {
////						if (row == table.getSelectedRow() && column == table.getSelectedColumn()) {
//							System.out.println(row + " "+ column);
//							setText(value.toString());
//							c.setBackground(getColor());
//							c.setForeground(Color.black);
////						} else {
////							System.out.println("Color but not selected row: "+row +" "+column);
//////							Color tempColor = c.getBackground();
//////							setText(value.toString());
//////							c.setBackground(tempColor);
//////							c.setForeground(color.black);
////						}
////						System.out.println("Row: "+row+" Column: "+column+" Color: "+c.getBackground());
////						setText(value.toString());
////						c.setBackground(getColor());
////						c.setForeground(Color.black);
//					} else {
//						System.out.println("no color row & column");
//						setText(value.toString());
//						c.setBackground(Color.white);
//						c.setForeground(Color.black);
//					}
//				} else {
//					c.setBackground(Color.white);
//					c.setForeground(Color.black);
//				}
//			}
//			
//	        return c;
//		}
//	}
}
