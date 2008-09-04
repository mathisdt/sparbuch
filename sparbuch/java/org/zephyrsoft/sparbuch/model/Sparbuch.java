package org.zephyrsoft.sparbuch.model;

import java.io.*;
import java.util.*;

public class Sparbuch implements Serializable {
	
	private static final long serialVersionUID = 5931839542614942316L;
	
	private SortedSet<Geldbewegung> geldbewegungen = new TreeSet<Geldbewegung>();
	private double sparbuchStand = 0.0;
	private String name = null;
	
	public Sparbuch() {
	}
	
	public Sparbuch(String name) {
		this.name = name;
	}
	
	public double getSparbuchStand() {
		return sparbuchStand;
	}
	
	public void addGeldbewegung(Geldbewegung e) {
		if (e.isEinzahlung()) {
			sparbuchStand += e.getSumme();
		} else {
			sparbuchStand -= e.getSumme();
		}
		geldbewegungen.add(e);
	}

	public void addAllGeldbewegungen(Collection<? extends Geldbewegung> c) {
		for (Geldbewegung e : c) {
			addGeldbewegung(e);
		}
	}

	public void clearGeldbewegungen() {
		geldbewegungen.clear();
	}

	public boolean containsGeldbewegung(Geldbewegung o) {
		return geldbewegungen.contains(o);
	}

	public boolean isGeldbewegungenEmpty() {
		return geldbewegungen.isEmpty();
	}

	public void removeGeldbewegung(Geldbewegung e) {
		if (e.isEinzahlung()) {
			sparbuchStand -= e.getSumme();
		} else {
			sparbuchStand += e.getSumme();
		}
		geldbewegungen.remove(e);
	}

	public void removeAllGeldbewegungen(Collection<? extends Geldbewegung> c) {
		for (Geldbewegung e : c) {
			removeGeldbewegung(e);
		}
	}

	public int sizeGeldbewegungen() {
		return geldbewegungen.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
