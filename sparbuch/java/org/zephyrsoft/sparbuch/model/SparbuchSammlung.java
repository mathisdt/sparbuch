package org.zephyrsoft.sparbuch.model;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

/**
 * Sammlung von Sparbüchern (Konten)
 * @author Mathis Dirksen-Thedens
 */
public class SparbuchSammlung implements Serializable, ListModel {
	
	private static final long serialVersionUID = 5931839542614942316L;
	
	private List<Sparbuch> sparbuchSammlung = new ArrayList<Sparbuch>();
	
	private transient List<ListDataListener> listeners = new ArrayList<ListDataListener>();
	
	public void notifyListeners() {
		ListDataEvent lde = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize()-1);
		for (ListDataListener l : listeners) {
			l.contentsChanged(lde);
		}
	}

	public boolean add(Sparbuch e) {
		boolean ret = sparbuchSammlung.add(e);
		notifyListeners();
		return ret;
	}

	public boolean addAll(Collection<Sparbuch> c) {
		boolean ret = sparbuchSammlung.addAll(c);
		notifyListeners();
		return ret;
	}

	public void clear() {
		sparbuchSammlung.clear();
		notifyListeners();
	}

	public boolean contains(Sparbuch o) {
		return sparbuchSammlung.contains(o);
	}

	public boolean isEmpty() {
		return sparbuchSammlung.isEmpty();
	}

	public boolean remove(Sparbuch o) {
		boolean ret = sparbuchSammlung.remove(o);
		notifyListeners();
		return ret;
	}

	public boolean removeAll(Collection<Sparbuch> c) {
		boolean ret = sparbuchSammlung.removeAll(c);
		notifyListeners();
		return ret;
	}

	public int size() {
		return sparbuchSammlung.size();
	}
	
	public Sparbuch get(int index) {
		return sparbuchSammlung.get(index);
	}

	/**
	 * Lädt die SparbuchSammlung aus einer Datei.
	 *
	 * @param file  Datei
	 */
	public static SparbuchSammlung loadFromFile(File file) {
		SparbuchSammlung ret = null;
		if (file!=null && file.exists() && file.isFile() && file.canRead()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
				ret = (SparbuchSammlung)in.readObject();
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ret;
	}
	
	/**
	 * Speichert die SparbuchSammlung in eine Datei.
	 *
	 * @param file  Datei
	 * @return true wenn das Speichern erfolgreich war
	 */
	public static boolean saveToFile(SparbuchSammlung sparbuchSammlung, File file) {
		try {
			if (file==null) {
				throw new IllegalArgumentException("file ist null");
			}
			if (sparbuchSammlung==null) {
				throw new IllegalArgumentException("sparbuchSammlung ist null");
			}
			if (file.exists()) {
				if (file.isFile()) {
					file.delete();
				} else {
					throw new IllegalArgumentException("file existiert und ist ein Verzeichnis");
				}
			}
			file.createNewFile();
			if (!file.canWrite()) {
				throw new IllegalArgumentException("kann nicht in file schreiben");
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(sparbuchSammlung);
			out.flush();
			out.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void addListDataListener(ListDataListener l) {
		if (listeners==null) {
			listeners = new ArrayList<ListDataListener>();
		}
		listeners.add(l);
	}

	public Object getElementAt(int index) {
		return get(index);
	}

	public int getSize() {
		return size();
	}

	public void removeListDataListener(ListDataListener l) {
		if (listeners==null) {
			listeners = new ArrayList<ListDataListener>();
		}
		listeners.remove(l);
	}
}
