package org.zephyrsoft.sparbuch.gui;

import java.awt.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * Renderer für Calendar-Objekte
 * 
 * @author Mathis Dirksen-Thedens
 *
 */
public class CalendarTableCellRenderer extends DefaultTableCellRenderer {

	private DateFormat sdf = DateFormat.getDateInstance(DateFormat.SHORT);
	
	protected void setValue(Object value) {
		if (!(value instanceof Calendar)) {
			throw new IllegalArgumentException("object of type Calendar expected");
		}
		super.setValue(sdf.format(((Calendar)value).getTime()));
	}
	
}
