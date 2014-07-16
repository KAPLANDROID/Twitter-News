package com.kaplandroid.twitternews.model;

/**
 * 
 * @author KAPLANDROID
 * 
 */
public class SourceForDB {

	private int sourceID;
	private String sourceName;

	public SourceForDB(int sourceID, String sourceName) {
		this.sourceID = sourceID;
		this.sourceName = sourceName;
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Override
	public String toString() {
		return sourceName;
	}

}
