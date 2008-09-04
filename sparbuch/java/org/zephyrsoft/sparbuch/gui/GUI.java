package org.zephyrsoft.sparbuch.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.zephyrsoft.sparbuch.model.*;

import com.jeta.forms.components.panel.*;

public class GUI extends JFrame {
	
	private SparbuchSammlung sammlung = null;
	private String sammlungLoadedFrom = null;
	
	private JList comp_sparbuecher;
	private JButton comp_sparbuecher_neu;
	private JButton comp_sparbuecher_aendern;
	private JButton comp_sparbuecher_loeschen;
	private JTable comp_buchungen;
	private JButton comp_buchungen_neu;
	private JButton comp_buchungen_aendern;
	private JButton comp_buchungen_loeschen;
	private JLabel comp_aktuellerstand;
	private JButton comp_allesspeichern;
	
	private JLabel label_sparbuecher;
	private JLabel label_buchungen;
	private JLabel label_aktuellerstand;
	
	public GUI(SparbuchSammlung sammlung, String sammlungLoadedFrom) {
		this.sammlung = sammlung;
		this.sammlungLoadedFrom = sammlungLoadedFrom;
		if (this.sammlung==null) {
			// es wurde keine bestehende Datei geladen
			this.sammlung = new SparbuchSammlung();
		}
		createGUI();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// TODO sicherstellen dass gespeichert wird falls gewünscht
				
				dispose();
				System.exit(0);
			}
		});
		setTitle("Sparbücher-Verwaltung");
		setSize(800, 600);
		GraphicsDevice[] dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		setLocation((int)(dev[0].getDefaultConfiguration().getBounds().getWidth()/2)-(int)(getSize().getWidth()/2), (int)(dev[0].getDefaultConfiguration().getBounds().getHeight()/2)-(int)(getSize().getHeight()/2));
		setVisible(true);
	}
	
	private void createGUI() {
		FormPanel panel = new FormPanel("org/zephyrsoft/sparbuch/gui/GUI.jfrm");
		add(panel);
		comp_sparbuecher = panel.getList("sparbuecher");
		comp_sparbuecher_neu = (JButton)panel.getButton("sparbuecher_neu");
		comp_sparbuecher_aendern = (JButton)panel.getButton("sparbuecher_aendern");
		comp_sparbuecher_loeschen = (JButton)panel.getButton("sparbuecher_loeschen");
		comp_buchungen = panel.getTable("buchungen");
		comp_buchungen_neu = (JButton)panel.getButton("buchungen_neu");
		comp_buchungen_aendern = (JButton)panel.getButton("buchungen_aendern");
		comp_buchungen_loeschen = (JButton)panel.getButton("buchungen_loeschen");
		comp_aktuellerstand = panel.getLabel("aktuellerstand");
		comp_allesspeichern = (JButton)panel.getButton("allesspeichern");
		label_sparbuecher = panel.getLabel("label_sparbuecher");
		label_buchungen = panel.getLabel("label_buchungen");
		label_aktuellerstand = panel.getLabel("label_aktuellerstand");
		
		// TODO
		
	}
	
}
