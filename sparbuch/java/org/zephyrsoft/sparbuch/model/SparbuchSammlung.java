package org.zephyrsoft.sparbuch.model;

import java.io.*;
import java.util.*;

public class SparbuchSammlung implements Serializable {
	
	private static final long serialVersionUID = 5931839542614942316L;
	
	private List<Sparbuch> sparbuchSammlung = new ArrayList<Sparbuch>();

	public boolean add(Sparbuch e) {
		return sparbuchSammlung.add(e);
	}

	public boolean addAll(Collection<Sparbuch> c) {
		return sparbuchSammlung.addAll(c);
	}

	public void clear() {
		sparbuchSammlung.clear();
	}

	public boolean contains(Sparbuch o) {
		return sparbuchSammlung.contains(o);
	}

	public boolean isEmpty() {
		return sparbuchSammlung.isEmpty();
	}

	public boolean remove(Sparbuch o) {
		return sparbuchSammlung.remove(o);
	}

	public boolean removeAll(Collection<Sparbuch> c) {
		return sparbuchSammlung.removeAll(c);
	}

	public int size() {
		return sparbuchSammlung.size();
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
}
