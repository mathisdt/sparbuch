package org.zephyrsoft.sparbuch;

import java.text.*;

public abstract class Constants {
	
	public static final String VERSION = "1.0";
	public static final String DEFAULT_DATA_FILE = "sparbuchsammlung.sbs";
	public static final String FILE_EXTENSION = ".sbs";
	
	public static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();
	public static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
	public static final DateFormat DATE_FORMAT = DateFormat.getDateInstance(DateFormat.SHORT);
	
	static {
		NUMBER_FORMAT.setMaximumFractionDigits(2);
		NUMBER_FORMAT.setMinimumFractionDigits(2);
		CURRENCY_FORMAT.setMaximumFractionDigits(2);
		CURRENCY_FORMAT.setMinimumFractionDigits(2);
	}
}
