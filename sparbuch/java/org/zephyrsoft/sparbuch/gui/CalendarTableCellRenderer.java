package org.zephyrsoft.sparbuch.gui;

import java.text.DateFormat;
import java.util.Calendar;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer für Calendar-Objekte
 * @author Mathis Dirksen-Thedens
 */
public class CalendarTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	private DateFormat sdf = DateFormat.getDateInstance(DateFormat.SHORT);
	
	protected void setValue(Object value) {
		if (!(value instanceof Calendar)) {
			throw new IllegalArgumentException("object of type Calendar expected");
		}
		super.setValue(sdf.format(((Calendar)value).getTime()));
	}
	
}
