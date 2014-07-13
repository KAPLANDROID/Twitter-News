package com.kaplandroid.twitternews.twitterapi;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Context;

/**
 * 
 * API Call Methods for Twitter
 * 
 * @author KAPLANDROID
 * 
 */
public class TwitterMethods {

	public TwitterMethods() {
	}

	public static void postToTwitter(Context context, final Activity callingActivity, final String message,
			final TwitterCallback postResponse) {
		if (!TwitterLoginActivity.isActive(context)) {
			postResponse.onFinsihed(false);
			return;
		}

		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(TwitterConstant.TWITTER_CONSUMER_KEY);
		configurationBuilder.setOAuthConsumerSecret(TwitterConstant.TWITTER_CONSUMER_SECRET);
		configurationBuilder.setOAuthAccessToken(TwitterLoginActivity.getAccessToken((context)));
		configurationBuilder.setOAuthAccessTokenSecret(TwitterLoginActivity.getAccessTokenSecret(context));
		Configuration configuration = configurationBuilder.build();
		final Twitter twitter = new TwitterFactory(configuration).getInstance();

		new Thread(new Runnable() {

			private double x;

			@Override
			public void run() {
				boolean success = true;
				try {
					x = Math.random();
					twitter.updateStatus(message + " " + x);
				} catch (TwitterException e) {
					e.printStackTrace();
					success = false;
				}

				final boolean finalSuccess = success;

				callingActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						postResponse.onFinsihed(finalSuccess);
					}
				});

			}
		}).start();
	}

	public static void searchNews(Context context, final Activity callingActivity, final String keyword,
			final TwitterSearchCallback postResponse) {
		if (!TwitterLoginActivity.isActive(context)) {
			postResponse.onFinsihed(false, null);
			return;
		}

		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(TwitterConstant.TWITTER_CONSUMER_KEY);
		configurationBuilder.setOAuthConsumerSecret(TwitterConstant.TWITTER_CONSUMER_SECRET);
		configurationBuilder.setOAuthAccessToken(TwitterLoginActivity.getAccessToken((context)));
		configurationBuilder.setOAuthAccessTokenSecret(TwitterLoginActivity.getAccessTokenSecret(context));
		Configuration configuration = configurationBuilder.build();
		final Twitter twitter = new TwitterFactory(configuration).getInstance();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					Query query = new Query();
					query.count(100);
					query.setQuery(keyword);
					query.resultType("recent");

					// System.out.println(query);
					final QueryResult queryResult = twitter.search(query);

					callingActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							postResponse.onFinsihed(true, queryResult.getTweets());
						}
					});

				} catch (TwitterException e) {
					e.printStackTrace();
					callingActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							postResponse.onFinsihed(false, null);
						}
					});
				}

			}
		}).start();
	}

	public static abstract class TwitterCallback {
		public abstract void onFinsihed(Boolean success);
	}

	public static abstract class TwitterSearchCallback {
		public abstract void onFinsihed(Boolean success, List<Status> list);
	}
}
