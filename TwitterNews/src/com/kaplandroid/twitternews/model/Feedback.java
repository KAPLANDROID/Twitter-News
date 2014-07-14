package com.kaplandroid.twitternews.model;

/**
 * 
 * @author KAPLANDROID
 * 
 */
public class Feedback {

	private int totalCount;
	private String feedbackName;

	public Feedback(int totalCount, String feedbackName) {
		super();
		this.totalCount = totalCount;
		this.feedbackName = feedbackName;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public String getFeedbackName() {
		return feedbackName;
	}

}
