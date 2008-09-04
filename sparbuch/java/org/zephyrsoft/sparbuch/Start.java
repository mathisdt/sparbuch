package org.zephyrsoft.sparbuch;

import java.io.*;

import org.zephyrsoft.sparbuch.gui.*;
import org.zephyrsoft.sparbuch.model.*;

public class Start {
	
	public static void main(String[] args) {
		if (args==null || args.length==0) {
			// keine Datei übergeben, Default verwenden
			SparbuchSammlung sammlung = SparbuchSammlung.loadFromFile(new File(Constants.DEFAULT_DATA_FILE));
			new GUI(sammlung, Constants.DEFAULT_DATA_FILE);
		} else {
			// übergebenen Dateinamen nehmen
			SparbuchSammlung sammlung = SparbuchSammlung.loadFromFile(new File(args[0]));
			new GUI(sammlung, args[0]);
		}
	}

}