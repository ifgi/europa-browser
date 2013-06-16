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
import java.awt.Frame;  
import java.awt.GridLayout;
import java.awt.event.ActionListener;  
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;  
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author Matthias Pfeil
 *
 */
public class ColorDialog extends JDialog {  
   private Color[] colors;
   
   public ColorDialog(Frame owner, String title) {  
      super(owner, title, true);
      
      colors = new Color[2];
      
      JPanel pnlBase = new JPanel(new BorderLayout());
      JPanel pnlInput = new JPanel(new GridLayout(2, 1));
     
      JPanel pnlInputStartColor = new JPanel();
      pnlInputStartColor.setBorder(BorderFactory.createTitledBorder("Start color"));
      final JColorChooser jccStart = new JColorChooser();
      jccStart.setPreviewPanel(new JPanel());
      
      pnlInputStartColor.add(jccStart);
      
      JPanel pnlInputEndColor = new JPanel();
      pnlInputEndColor.setBorder(BorderFactory.createTitledBorder("End color"));
      final JColorChooser jccEnd = new JColorChooser();
      jccEnd.setPreviewPanel(new JPanel());
      pnlInputEndColor.add(jccEnd);
      
      JPanel pnlButton = new JPanel();
      
      pnlInput.add(pnlInputStartColor);
      pnlInput.add(pnlInputEndColor);
      
      JLabel labelDescription = new JLabel("Please select a start and end color.");

      final JButton okBtn = new JButton("Accept");
      okBtn.setEnabled(false);
      JButton noBtn = new JButton("Cancel");
      pnlButton.add(okBtn);  
      pnlButton.add(noBtn);
      
      pnlBase.add(labelDescription, BorderLayout.NORTH);
      pnlBase.add(pnlInput, BorderLayout.CENTER);
      pnlBase.add(pnlButton, BorderLayout.SOUTH);
      
      ChangeListener jccChangeListener = new ChangeListener() {
		
			@Override
			public void stateChanged(ChangeEvent e) {
				if(jccStart.getSelectionModel() == (DefaultColorSelectionModel) e.getSource()) {
					colors[0] = ((DefaultColorSelectionModel) e.getSource()).getSelectedColor();
				} else if(jccEnd.getSelectionModel() == (DefaultColorSelectionModel) e.getSource()){
					colors[1] = ((DefaultColorSelectionModel) e.getSource()).getSelectedColor();
				}
				
				if (colors[0] != null && colors[1] != null) {
					okBtn.setEnabled(true);
				} else {
					okBtn.setEnabled(false);
				}
				
			}
      };
      
      jccStart.getSelectionModel().addChangeListener(jccChangeListener);
      jccEnd.getSelectionModel().addChangeListener(jccChangeListener);
      
      okBtn.addActionListener(new ActionListener() {  
         public void actionPerformed(java.awt.event.ActionEvent ae) {  
            okButton();  
         }  
      });  
      
      noBtn.addActionListener(new ActionListener() {  
         public void actionPerformed(java.awt.event.ActionEvent ae) {  
            noButton();  
         }  
      });
      
      getContentPane().add(pnlBase, BorderLayout.CENTER);    
      pack();  
   }
   
   public Color[] getColors() {
	   return this.colors;
   }
  
   private void okButton() {    
      setVisible(false);  
   }  
  
   private void noButton() {  
	   this.colors = null;
	   setVisible(false);
   }  
} 