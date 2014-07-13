package com.kaplandroid.twitternews.model;

import java.io.Serializable;

/**
 * 
 * Tweet Model for inserting DB
 * 
 * 
 * @author KAPLANDROID
 * 
 */
public class TweetForDB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5144101137410995610L;

	private int dbID;
	private String tweetID;
	private String weblink;

	public TweetForDB(String tweetID, String weblink) {
		super();
		this.tweetID = tweetID;
		this.weblink = weblink;
	}

	public TweetForDB() {
		super();
	}

	public String getTweetID() {
		return tweetID;
	}

	public void setTweetID(String tweetID) {
		this.tweetID = tweetID;
	}

	public String getWeblink() {
		return weblink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}

	public int getDbID() {
		return dbID;
	}

	public void setDbID(int dbID) {
		this.dbID = dbID;
	}

}
