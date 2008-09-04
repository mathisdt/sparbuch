package org.zephyrsoft.sparbuch.model;

public class Auszahlung extends Geldbewegung {
	
	private static final long serialVersionUID = 5931839542614942316L;
	
	public boolean isEinzahlung() {
		return false;
	}
	
}
