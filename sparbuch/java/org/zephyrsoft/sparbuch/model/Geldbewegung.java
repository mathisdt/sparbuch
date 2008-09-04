package org.zephyrsoft.sparbuch.model;

import java.io.*;
import java.util.*;

public abstract class Geldbewegung implements Serializable, Comparable<Geldbewegung> {
	
	private static final long serialVersionUID = 5931839542614942316L;
	
	/** Ein- oder Auszahlungsdatum */
	private Calendar datum = null;
	/** Einzahler oder Auszahlungsempfänger */
	private String text = null;
	/** Summe der Ein- oder Auszahlung */
	private double summe = 0.0;
	
	/**
	 * Default-Konstruktor. Setzt keine Felder.
	 */
	public Geldbewegung() {
	}
	
	/**
	 * Konstruktor, der das momentane Datum verwendet.
	 * @param summe
	 * @param text
	 */
	public Geldbewegung(double summe, String text) {
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
	public Geldbewegung(Calendar datum, double summe, String text) {
		this.datum = datum;
		this.summe = summe;
		this.text = text;
	}

	public abstract boolean isEinzahlung();
	
	public boolean isAuszahlung() {
		return !isEinzahlung();
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

	public int compareTo(Geldbewegung o) {
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
