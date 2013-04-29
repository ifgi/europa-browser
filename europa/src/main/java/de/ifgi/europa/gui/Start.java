package de.ifgi.europa.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 *  System Look and Feel
		 */
	    try {
		    // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
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
	    
	    new MainFrame();
	}

}
