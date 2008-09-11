package org.zephyrsoft.sparbuch.model;

import java.io.*;
import java.util.*;

public class Buchung implements Serializable, Comparable<Buchung> {
	
	private static final long serialVersionUID = 5931839542614942316L;
	
	/** Ein- oder Auszahlungsdatum */
	private Calendar datum = null;
	/** Einzahler oder Auszahlungsempfänger */
	private String text = null;
	/** Summe der Ein- oder Auszahlung */
	private double summe = 0.0;
	/** true wenn dies eine Einzahlung ist */
	private boolean isEinzahlung;
	
	/**
	 * Default-Konstruktor. Setzt keine Felder.
	 */
	public Buchung() {
		this.datum = new GregorianCalendar();
		this.summe = 0.0;
		this.text = "";
	}
	
	/**
	 * Konstruktor, der das momentane Datum verwendet.
	 * @param summe
	 * @param text
	 */
	public Buchung(double summe, String text) {
		this.datum = new GregorianCalendar();
		this.summe = summe;
		this.text = text;
	}
	
	/** 
	 * Konstruktor, der alle Felder setzt.
	 * @param datum
	 * @param summe
	 * @param text
	 */
	public Buchung(Calendar datum, double summe, String text) {
		this.datum = datum;
		this.summe = summe;
		this.text = text;
	}

	public boolean isEinzahlung() {
		return isEinzahlung;
	}
	
	public void setEinzahlung(boolean isEinzahlung) {
		this.isEinzahlung = isEinzahlung;
	}

	public Calendar getDatum() {
		return datum;
	}

	public void setDatum(Calendar datum) {
		this.datum = datum;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getSumme() {
		return summe;
	}

	public void setSumme(double summe) {
		this.summe = summe;
	}

	/**
	 * Strenge eguals-Methode, die absolute Gleichheit (==) fordert, damit auch mehrfache Buchungen mit derselben Summe möglich sind!
	 */
	public boolean equals(Object obj) {
		return (this==obj);
	}

	public int compareTo(Buchung o) {
		if (o==null) {
			throw new IllegalArgumentException("null ist hier nicht erlaubt");
		}
		if (o.getDatum()==null) {
			// anderes Datum ist null: dies Objekt kommt nach dem anderen (null ganz vorne)
			return 1;
		} else if (getDatum()==null) {
			// eigenes Datum ist null: dies Objekt kommt vor dem anderen (null ganz vorne)
			return -1;
		} else {
			// vergleiche Daten
			return getDatum().compareTo(o.getDatum());
		}
	}
	
	
	
}
