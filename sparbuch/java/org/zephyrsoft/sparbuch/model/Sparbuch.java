package org.zephyrsoft.sparbuch.model;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.text.*;

import org.zephyrsoft.sparbuch.*;

public class Sparbuch implements TableModel, Serializable {
	
	private transient List<TableModelListener> listeners = new ArrayList<TableModelListener>();
	
	private static final long serialVersionUID = 5931839542614942316L;
	
	private List<Buchung> geldbewegungen = new ArrayList<Buchung>();
	private double sparbuchStand = 0.0;
	private String name = null;
	
	public void notifyListeners() {
		TableModelEvent tme = new TableModelEvent(this);
		for (TableModelListener l : listeners) {
			l.tableChanged(tme);
		}
	}
	
	public Sparbuch() {
	}
	
	public Sparbuch(String name) {
		this();
		this.name = name;
	}
	
	public double getSparbuchStand() {
		return sparbuchStand;
	}
	
	public void addGeldbewegung(Buchung e) {
		if (e.isEinzahlung()) {
			sparbuchStand += e.getSumme();
		} else {
			sparbuchStand -= e.getSumme();
		}
		geldbewegungen.add(e);
		Collections.sort(geldbewegungen);
	}

	public void addAllGeldbewegungen(Collection<? extends Buchung> c) {
		for (Buchung e : c) {
			addGeldbewegung(e);
		}
	}

	public void clearGeldbewegungen() {
		geldbewegungen.clear();
	}

	public boolean containsGeldbewegung(Buchung o) {
		return geldbewegungen.contains(o);
	}

	public boolean isGeldbewegungenEmpty() {
		return geldbewegungen.isEmpty();
	}

	public void removeGeldbewegung(Buchung e) {
		if (e.isEinzahlung()) {
			sparbuchStand -= e.getSumme();
		} else {
			sparbuchStand += e.getSumme();
		}
		geldbewegungen.remove(e);
	}

	public void removeAllGeldbewegungen(Collection<? extends Buchung> c) {
		for (Buchung e : c) {
			removeGeldbewegung(e);
		}
	}

	public int sizeGeldbewegungen() {
		return geldbewegungen.size();
	}
	
	public Buchung getGeldbewegungAt(int index) {
		return geldbewegungen.get(index);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return getName();
	}

	public void addTableModelListener(TableModelListener l) {
		if (listeners==null) {
			listeners = new ArrayList<TableModelListener>();
		}
		listeners.add(l);
	}

	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			// Datum
			return Calendar.class;
		case 1:
			// Text
			return String.class;
		case 2:
			// Ein- oder Auszahlung
			return String.class;
		case 3:
			// Summe, bereits formatiert (daher String)
			return String.class;
		}
		return null;
	}
	
	public int getColumnCount() {
		return 4;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			// Datum
			return "Datum";
		case 1:
			// Text
			return "Text";
		case 2:
			// Ein- oder Auszahlung
			return "Ein-/Auszahlung";
		case 3:
			// Summe
			return "Summe";
		}
		return null;
	}

	public int getRowCount() {
		return geldbewegungen.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			// Datum
			return geldbewegungen.get(rowIndex).getDatum();
		case 1:
			// Text
			return geldbewegungen.get(rowIndex).getText();
		case 2:
			// Ein- oder Auszahlung
			return (geldbewegungen.get(rowIndex).isEinzahlung() ? "Einzahlung" : "Auszahlung");
		case 3:
			// Summe als String, z.B. "+ 15,00 €" oder "- 4,99 €"
			return MessageFormat.format("{0} {1}", (geldbewegungen.get(rowIndex).isEinzahlung() ? "+" : "-"), Constants.CURRENCY_FORMAT.format(geldbewegungen.get(rowIndex).getSumme()));
		}
		return null;
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void removeTableModelListener(TableModelListener l) {
		if (listeners==null) {
			listeners = new ArrayList<TableModelListener>();
		}
		listeners.remove(l);
	}

	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		throw new IllegalArgumentException("Tabelle nicht direkt änderbar!");
	}
}
