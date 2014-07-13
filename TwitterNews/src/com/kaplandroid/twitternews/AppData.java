package com.kaplandroid.twitternews;

/**
 * 
 * @author KAPLANDROID
 * 
 *         Singleton Class for app settings
 * 
 */
public class AppData {

	private int lastNewsID;
	private String lastKeyword;

	public static final int FEEDBACK_COOL = 1;
	public static final int FEEDBACK_BORING = 2;

	private static AppData ref;

	private AppData() {

	}

	public static AppData getInstance() {
		if (ref == null)
			ref = new AppData();
		return ref;
	}

	public String getLastKeyword() {
		return lastKeyword;
	}

	public void setLastKeyword(String lastKeyword) {
		this.lastKeyword = lastKeyword;
	}

	public int getLastNewsID() {
		return lastNewsID;
	}

	public void setLastNewsID(int lastNewsID) {
		this.lastNewsID = lastNewsID;
	}

	public void increaseNewsIDBy1() {
		this.lastNewsID++;
	}

}
