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
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * This class represents the starting point of the SOS LOD Viewer
 * @author Matthias Pfeil
 *
 */
public class MainFrame extends JFrame {

	private JPanel pnlMap;
	private JPanel pnlFilter;
	private JPanel pnlGraph;
	private JPanel pnlStatusBar;
	private JSplitPane splitPaneTop;
	private JSplitPane splitPaneBottom;
	
	private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
            	if (e.isAltDown()) {
            		if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
            			toggle(splitPaneTop, true);
    	            } else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
    	            	toggle(splitPaneTop, false);
    	            } else if (e.getKeyCode() == KeyEvent.VK_UP ) {
    	            	toggle(splitPaneBottom, false);
    	            } else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
    	            	toggle(splitPaneBottom, true);
    	            }
				}
            } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                
            } else if (e.getID() == KeyEvent.KEY_TYPED) {
                
            }
            return false;
        }
        
        public void toggle(JSplitPane sp, boolean collapse) {
            try {
                BasicSplitPaneDivider bspd = ((BasicSplitPaneUI) sp.getUI()).getDivider();
                Field buttonField = BasicSplitPaneDivider.class.
                        getDeclaredField(collapse ? "rightButton" : "leftButton");
                buttonField.setAccessible(true);
                JButton button = (JButton) buttonField.get(((BasicSplitPaneUI) sp.getUI()).getDivider());
                button.getActionListeners()[0].actionPerformed(new ActionEvent(bspd, MouseEvent.MOUSE_CLICKED,
                        "bum"));
                
                ((GraphPanel) getGraphPanel()).getViewer().getDefaultView().repaint();
                splitPaneBottom.repaint();
                splitPaneBottom.revalidate();
                splitPaneBottom.updateUI();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public MainFrame() {
		JPanel mainPanel = new JPanel(new BorderLayout());
	    
	    pnlFilter = new FilterPanel(this);
	    pnlMap = new MapPanel(this);
	    pnlGraph = new GraphPanel(this);
	    pnlStatusBar = new StatusBarPanel(this);
	    
	    splitPaneTop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnlFilter,pnlMap);
	    splitPaneTop.setOneTouchExpandable(true);
	    splitPaneTop.setBorder(null);
	    
	    splitPaneBottom = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneTop, pnlGraph);
	    splitPaneBottom.setOneTouchExpandable(true);
	    
	    mainPanel.add(splitPaneBottom, BorderLayout.CENTER);
	    mainPanel.add(pnlStatusBar, BorderLayout.PAGE_END);
	    
	    add(mainPanel,BorderLayout.CENTER);
	    
	    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 *  System Look and Feel
		 */
	    try {
		    // Set System L&F
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
	    
	    MainFrame mainFrame= new MainFrame();
	    mainFrame.setSize(800,600);
	    mainFrame.setLocation(0,0);
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainFrame.pack();
	    mainFrame.setVisible(true);
	}
	
	public JPanel getMapPanel() {
		return pnlMap;
	}

	public JPanel getFilterPanel() {
		return pnlFilter;
	}

	public JPanel getGraphPanel() {
		return pnlGraph;
	}

	public JPanel getStatusBarPanel(){
		return pnlStatusBar;
	}
}
