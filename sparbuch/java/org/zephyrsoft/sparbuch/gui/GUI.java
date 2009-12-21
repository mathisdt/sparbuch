package org.zephyrsoft.sparbuch.gui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import org.zephyrsoft.sparbuch.Constants;
import org.zephyrsoft.sparbuch.model.Buchung;
import org.zephyrsoft.sparbuch.model.Sparbuch;
import org.zephyrsoft.sparbuch.model.SparbuchSammlung;

import com.jeta.forms.components.panel.FormPanel;

/**
 * Benutzeroberfläche des Programms (Hauptfenster)
 * @author Mathis Dirksen-Thedens
 */
public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private boolean dirty = false;
	
	private SparbuchSammlung sammlung = null;
	private String sammlungLoadedFrom = null;
	
	private static Sparbuch EMPTY_SPARBUCH = new Sparbuch();
	private GraphicsDevice[] dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
	
	private Sparbuch sparbuchSelected = null;
	private Buchung buchungSelected = null;
	private Buchung buchungInBuchungDialog = null;
	
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
	private JButton comp_allesladen;
	private JButton comp_allesneu;
	@SuppressWarnings("unused")
	private JLabel label_sparbuecher;
	@SuppressWarnings("unused")
	private JLabel label_buchungen;
	@SuppressWarnings("unused")
	private JLabel label_aktuellerstand;
	
	private JDialog buchung_dialog;
	@SuppressWarnings("unused")
	private JLabel b_label_datum;
	@SuppressWarnings("unused")
	private JLabel b_label_text;
	@SuppressWarnings("unused")
	private JLabel b_label_summe;
	private JTextField b_datum;
	private JTextField b_text;
	private JTextField b_summe;
	private JRadioButton b_einzahlung;
	private JRadioButton b_auszahlung;
	private JButton b_ok;
	private JButton b_abbrechen;
	
	public GUI(SparbuchSammlung sammlung, String sammlungLoadedFrom) {
		this.sammlung = sammlung;
		this.sammlungLoadedFrom = sammlungLoadedFrom;
		if (this.sammlung==null) {
			// es wurde keine bestehende Datei geladen
			this.sammlung = new SparbuchSammlung();
		}
		createGUI();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// sicherstellen dass gespeichert wird falls gewünscht
				boolean successful = saveIfNecessary();
				if (successful) {
					dispose();
					System.exit(0);
				}
			}
		});
		setTitle("Sparbücher-Verwaltung");
		setSize(800, 600);
		setLocation((int)(dev[0].getDefaultConfiguration().getBounds().getWidth()/2)-(int)(getSize().getWidth()/2), (int)(dev[0].getDefaultConfiguration().getBounds().getHeight()/2)-(int)(getSize().getHeight()/2));
		setVisible(true);
	}
	
	private void createGUI() {
		FormPanel panel = new FormPanel("org/zephyrsoft/sparbuch/gui/GUI.jfrm");
		FormPanel buchungPanel = new FormPanel("org/zephyrsoft/sparbuch/gui/Buchung.jfrm");
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
		comp_allesladen = (JButton)panel.getButton("allesladen");
		comp_allesneu = (JButton)panel.getButton("allesneu");
		label_sparbuecher = panel.getLabel("label_sparbuecher");
		label_buchungen = panel.getLabel("label_buchungen");
		label_aktuellerstand = panel.getLabel("label_aktuellerstand");
		
		b_datum = buchungPanel.getTextField("datum");
		b_text = buchungPanel.getTextField("text");
		b_summe = buchungPanel.getTextField("summe");
		b_einzahlung = buchungPanel.getRadioButton("einzahlung");
		b_auszahlung = buchungPanel.getRadioButton("auszahlung");
		b_ok = (JButton)buchungPanel.getButton("ok");
		b_abbrechen = (JButton)buchungPanel.getButton("abbrechen");
		b_label_datum = buchungPanel.getLabel("label_datum");
		b_label_text = buchungPanel.getLabel("label_text");
		b_label_summe = buchungPanel.getLabel("label_summe");
		buchung_dialog = new JDialog(this, "Buchung", true);
		buchung_dialog.add(buchungPanel);
		
		// Sparbuch-Liste
		comp_sparbuecher.setModel(sammlung);
		comp_sparbuecher.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		comp_sparbuecher.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				sparbuchSelected = (Sparbuch)comp_sparbuecher.getSelectedValue();
				triggerSparbuchChanged();
			}
		});
		
		// Sparbuch-Buttons
		comp_sparbuecher_neu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = JOptionPane.showInputDialog(GUI.this, "Bitte den Namen des neuen Sparbuchs eingeben:", "Sparbuch neu anlegen", JOptionPane.QUESTION_MESSAGE);
				if (newName!=null && !"".equals(newName)) {
					Sparbuch newSparbuch = new Sparbuch(newName);
					sammlung.add(newSparbuch);
					setDirty(true);
				}
			}
		});
		comp_sparbuecher_aendern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newName = JOptionPane.showInputDialog(GUI.this, "Bitte den neuen Namen des Sparbuchs \"" + sparbuchSelected.getName() + "\" eingeben:", "Sparbuch ändern", JOptionPane.QUESTION_MESSAGE);
				if (newName!=null && !"".equals(newName)) {
					sparbuchSelected.setName(newName);
					sammlung.notifyListeners();
					setDirty(true);
				}
			}
		});
		comp_sparbuecher_loeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(GUI.this, "Soll das Sparbuch \"" + sparbuchSelected.getName() + "\" wirklich gelöscht werden?", "Sparbuch löschen", JOptionPane.YES_NO_OPTION);
				if (answer==JOptionPane.YES_OPTION) {
					sammlung.remove(sparbuchSelected);
					sparbuchSelected = null;
					triggerSparbuchChanged();
					setDirty(true);
				}
			}
		});
		
		// Buchungen-Tabelle
		comp_buchungen.setDefaultRenderer(Calendar.class, new CalendarTableCellRenderer());
		comp_buchungen.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				buchungSelected = (comp_buchungen.getSelectedRow()==-1 ? null : (Buchung)sparbuchSelected.getGeldbewegungAt(comp_buchungen.getSelectedRow()));
				triggerBuchungChanged();
			}
		});
		comp_buchungen.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount()>=2) {
					ActionEvent ev = new ActionEvent(comp_buchungen, 0, "");
					for(ActionListener a : comp_buchungen_aendern.getActionListeners()) {
						a.actionPerformed(ev);
					}
				}
			}
		});
		
		// Buchungen-Buttons
		comp_buchungen_neu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBuchungDialogFor(new Buchung());
			}
		});
		comp_buchungen_aendern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBuchungDialogFor(buchungSelected);
			}
		});
		comp_buchungen_loeschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int answer = JOptionPane.showConfirmDialog(GUI.this, "Soll die markierte Buchung wirklich gelöscht werden?", "Buchung löschen", JOptionPane.YES_NO_OPTION);
				if (answer==JOptionPane.YES_OPTION) {
					sparbuchSelected.removeGeldbewegung(buchungSelected);
					buchungSelected = null;
					triggerBuchungChanged();
					setDirty(true);
				}
			}
		});
		
		// Buchungsdialog-Felder
		KeyListener enterListener = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					buchungsDialogOkAction();
				}
			}
		};
		FocusListener selectAllListener = new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				((JTextComponent)e.getComponent()).selectAll();
			}
		};
		b_datum.addKeyListener(enterListener);
		b_datum.addFocusListener(selectAllListener);
		b_text.addKeyListener(enterListener);
		b_text.addFocusListener(selectAllListener);
		b_summe.addKeyListener(enterListener);
		b_summe.addFocusListener(selectAllListener);
		b_einzahlung.addKeyListener(enterListener);
		b_auszahlung.addKeyListener(enterListener);
		
		// Buchungsdialog-Buttons
		b_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buchungsDialogOkAction();
			}
		});
		b_abbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buchung_dialog.setVisible(false);
			}
		});
		
		// Laden-Button
		comp_allesladen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		
		// Neu-Button
		comp_allesneu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				beginNew();
			}
		});
		
		// Speichern-Button
		comp_allesspeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		
		triggerSparbuchChanged();
	}
	
	private boolean saveIfNecessary() {
		boolean successful = true;
		if (isDirty() && JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(GUI.this, "Sollen die Änderungen gespeichert werden?", "Änderungen speichern", JOptionPane.YES_NO_OPTION)) {
			successful = save();
		}
		return successful;
	}
	
	private void buchungsDialogOkAction() {
		changeBuchung();
		buchung_dialog.setVisible(false);
	}
	
	/**
	 * Speichert die Saprbuch-Sammlung.
	 * @return false wenn das Speichern aus irgendeinem Grund nicht durchgeführt werden konnte
	 */
	private boolean save() {
		if (sammlungLoadedFrom==null || sammlungLoadedFrom.isEmpty()) {
			// Datei aussuchen
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					if (f.isDirectory()) {
						return true;
					}
					return f.getName().toLowerCase().endsWith(Constants.FILE_EXTENSION);
				}

				public String getDescription() {
					return "Sparbuch-Sammlungen (*" + Constants.FILE_EXTENSION + ")";
				}
			});
			chooser.setMultiSelectionEnabled(false);
			if (chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION && chooser.getSelectedFile()!=null) {
				sammlungLoadedFrom = chooser.getSelectedFile().getAbsolutePath().trim();
				if (!sammlungLoadedFrom.endsWith(Constants.FILE_EXTENSION)) {
					sammlungLoadedFrom += Constants.FILE_EXTENSION;
				}
			}
		}
		if (sammlungLoadedFrom!=null && !sammlungLoadedFrom.isEmpty()) {
			boolean success = SparbuchSammlung.saveToFile(sammlung, new File(sammlungLoadedFrom));
			if (success) {
				// Erfolg
				setDirty(false);
				return true;
			} else {
				// Fehler
				JOptionPane.showMessageDialog(this, "Beim Speichern ist ein Fehler aufgetreten!", "Fehler", JOptionPane.ERROR_MESSAGE);
				sammlungLoadedFrom = null;
				return save();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Der Dateiname ist ungültig oder das Speichern wurde abgebrochen!", "Fehler", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	private boolean load() {
		// Speichern falls nötig
		saveIfNecessary();
		
		// Datei aussuchen
		JFileChooser chooser = new JFileChooser();
		chooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				return f.getName().toLowerCase().endsWith(Constants.FILE_EXTENSION);
			}

			public String getDescription() {
				return "Sparbuch-Sammlungen (*" + Constants.FILE_EXTENSION + ")";
			}
		});
		chooser.setMultiSelectionEnabled(false);
		String sbsName = null;
		if (chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION && chooser.getSelectedFile()!=null) {
			sbsName = chooser.getSelectedFile().getAbsolutePath();
		}
		
		if (sbsName!=null && !sbsName.isEmpty()) {
			SparbuchSammlung sbs = SparbuchSammlung.loadFromFile(new File(sbsName));
			if (sbs != null) {
				// Erfolg
				sammlungLoadedFrom = sbsName;
				sammlung = sbs;
				comp_sparbuecher.setModel(sammlung);
				triggerSparbuchChanged();
				setDirty(false);
				return true;
			} else {
				// Fehler
				JOptionPane.showMessageDialog(this, "Beim Laden ist ein Fehler aufgetreten!", "Fehler", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Der Dateiname ist ungültig oder das Laden wurde abgebrochen!", "Fehler", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	private boolean beginNew() {
		// Speichern falls nötig
		saveIfNecessary();
		
		// neue Sparbuchsammlung beginnen
		sammlungLoadedFrom = null;
		sammlung = new SparbuchSammlung();
		comp_sparbuecher.setModel(sammlung);
		triggerSparbuchChanged();
		setDirty(false);
		return true;
	}
	
	private void showBuchungDialogFor(Buchung b) {
		buchungInBuchungDialog = b;
		b_datum.setText(Constants.DATE_FORMAT.format(b.getDatum().getTime()));
		b_text.setText(b.getText());
		b_summe.setText(Constants.CURRENCY_FORMAT.format(b.getSumme()));
		b_einzahlung.setSelected(b.isEinzahlung());
		b_auszahlung.setSelected(!b.isEinzahlung());
		buchung_dialog.setModal(true);
		buchung_dialog.setSize(300, 200);
		buchung_dialog.setLocation((int)(dev[0].getDefaultConfiguration().getBounds().getWidth()/2)-(int)(getSize().getWidth()/2), (int)(dev[0].getDefaultConfiguration().getBounds().getHeight()/2)-(int)(getSize().getHeight()/2));
		b_datum.requestFocusInWindow();
		buchung_dialog.setVisible(true);
	}
	
	protected void changeBuchung() {
		Calendar cal = new GregorianCalendar();
		try {
			cal.setTime(Constants.DATE_FORMAT.parse(b_datum.getText().trim()));
		} catch (ParseException e1) {
			// egal
			System.out.println("ParseException: Datum");
		}
		buchungInBuchungDialog.setDatum(cal);
		buchungInBuchungDialog.setText(b_text.getText());
		double summe = 0.0;
		try {
			summe = Constants.CURRENCY_FORMAT.parse(b_summe.getText().trim()).doubleValue();
		} catch (ParseException e1) {
			try {
				// anders parsen (ohne Währungs-Zeichen versuchen)
				summe = Constants.NUMBER_FORMAT.parse(b_summe.getText().trim()).doubleValue();
			} catch(ParseException e2) {
				// egal
				System.out.println("ParseException: Summe");
			}
		}
		buchungInBuchungDialog.setSumme(summe);
		buchungInBuchungDialog.setEinzahlung(b_einzahlung.isSelected());
		if (!sparbuchSelected.containsGeldbewegung(buchungInBuchungDialog)) {
			sparbuchSelected.addGeldbewegung(buchungInBuchungDialog);
		}
		triggerBuchungChanged();
		sparbuchSelected.notifyListeners();
		setDirty(true);
	}
	
	private void triggerSparbuchChanged() {
		comp_sparbuecher_aendern.setEnabled(sparbuchSelected!=null);
		comp_sparbuecher_loeschen.setEnabled(sparbuchSelected!=null);
		comp_buchungen.setModel((sparbuchSelected==null ? EMPTY_SPARBUCH : sparbuchSelected));
		comp_buchungen.getSelectionModel().clearSelection();
		comp_buchungen_neu.setEnabled(sparbuchSelected!=null);
		triggerBuchungChanged();
		updateAktuellerStand();
		// damit die JTable sich aktualisiert:
		this.invalidate();
		this.repaint();
	}
	
	private void triggerBuchungChanged() {
		comp_buchungen_aendern.setEnabled(buchungSelected!=null);
		comp_buchungen_loeschen.setEnabled(buchungSelected!=null);
		updateAktuellerStand();
	}
	
	private void updateAktuellerStand() {
		comp_aktuellerstand.setText((sparbuchSelected!=null ? Constants.CURRENCY_FORMAT.format(sparbuchSelected.getSparbuchStand()) : "0,00 €"));
	}
	
	@SuppressWarnings("unused")
	private void setSparbuchButtonsEnabled(boolean enabled) {
		comp_sparbuecher_neu.setEnabled(enabled);
		comp_sparbuecher_aendern.setEnabled(enabled);
		comp_sparbuecher_loeschen.setEnabled(enabled);
	}
	
	@SuppressWarnings("unused")
	private void setBuchungenButtonsEnabled(boolean enabled) {
		comp_buchungen_neu.setEnabled(enabled);
		comp_buchungen_aendern.setEnabled(enabled);
		comp_buchungen_loeschen.setEnabled(enabled);
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
