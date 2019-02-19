package com.yuuitanabe.webbrowser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;



public class ReadFile extends JFrame {
	
	public static final long serialVersionUID = -1L;
	private JTextField addressBar;
	private JEditorPane display;
	
	public ReadFile() {
	
	addressBar = new JTextField("Enter URL Here");
	addressBar.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					loadFile(event.getActionCommand());
				}
			}
		);
		add(addressBar, BorderLayout.NORTH);
		display = new JEditorPane();
		display.setEditable(false);
		display.addHyperlinkListener( 
			new HyperlinkListener() {
				public void hyperlinkUpdate(HyperlinkEvent event) {
					if(event.getEventType()==HyperlinkEvent.EventType.ACTIVATED) {
						loadFile(event.getURL().toString());
					}
				}
				
			}
		);
		add(new JScrollPane(display),BorderLayout.CENTER);
		setSize(500,300);
		setVisible(true);
	}
	// loadFile
	private void loadFile(String userText) {
		try {
			display.setPage(userText);
			addressBar.setText(userText);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
