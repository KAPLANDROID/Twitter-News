package com.kaplandroid.twitternews.model;

/**
 * 
 * @author KAPLANDROID
 * 
 */
public class ChartModel {

	private int totalCount;
	private String feedbackName;

	public ChartModel(int totalCount, String feedbackName) {
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
