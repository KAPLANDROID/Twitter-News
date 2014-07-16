package com.kaplandroid.twitternews.ui.search;

import java.util.List;

import twitter4j.Status;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kaplandroid.twitternews.AppData;
import com.kaplandroid.twitternews.R;
import com.kaplandroid.twitternews.db.MobilikeDBHelper;
import com.kaplandroid.twitternews.model.TweetForDB;
import com.kaplandroid.twitternews.twitterapi.TwitterMethods;
import com.kaplandroid.twitternews.twitterapi.TwitterMethods.TwitterSearchCallback;
import com.kaplandroid.twitternews.ui.reader.ReadNewsActivity;

/**
 * 
 * 
 * Calls Twitter API for input keyword. If there is news found redirects to
 * ReadNewsActivity
 * 
 * @author KAPLANDROID
 * 
 */
public class SearchProgressActivity extends Activity {

	ProgressBar pbTwitterSearch;

	TextView tvTwitterSearchReady;
	TextView tvTwitterSearchInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_progress);

		pbTwitterSearch = (ProgressBar) findViewById(R.id.pbTwitterSearch);
		tvTwitterSearchInfo = (TextView) findViewById(R.id.tvTwitterSearchInfo);
		tvTwitterSearchReady = (TextView) findViewById(R.id.tvTwitterSearchReady);
		//
		tvTwitterSearchReady.setVisibility(View.GONE);
		tvTwitterSearchInfo.setVisibility(View.VISIBLE);

		final MobilikeDBHelper dbHelper = new MobilikeDBHelper(SearchProgressActivity.this);
		//

		// Clears tweet table but keeps statistics on other tables
		dbHelper.clearTweets();
		// Source, Feedback and relation tables stays same.

		TwitterMethods.searchNews(SearchProgressActivity.this, SearchProgressActivity.this, AppData.getInstance()
				.getLastKeyword(), AppData.getInstance().getLastSinceId(), new TwitterSearchCallback() {

			@Override
			public void onFinsihed(Boolean success, final List<Status> list) {
				// System.out.println(list);

				new Thread(new Runnable() {

					@Override
					public void run() {

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								pbTwitterSearch.setMax(list.size());
							}
						});

						try {
							AppData.getInstance().setLastSinceId(list.get(0).getId());
						} catch (Exception e) {
						}

						for (int i = 0; i < list.size(); i++) {
							Status currentTweet = list.get(i);
							final int progress = i;
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									pbTwitterSearch.setProgress(progress);
								}
							});
							if (currentTweet.getURLEntities().length != 0) {
								System.out.println(true);

								TweetForDB tweet = new TweetForDB(currentTweet.getId() + "", currentTweet
										.getURLEntities()[0].getURL());

								dbHelper.insertTweet(tweet);
								int dbID = dbHelper.getTweetDBIDByTweetID(currentTweet.getId() + "");
								dbHelper.insertSource(currentTweet.getUser().getScreenName());
								int sourceID = dbHelper.getSourceIDBySourceName(currentTweet.getUser().getScreenName());

								dbHelper.insertSourceToTweet(dbID, sourceID);

								// System.out.println(dbHelper.getTotalTweetCount());
								// System.out.println(currentTweet.getUser().getScreenName());
							} else {
								System.out.println(false);
							}
							// insert process for new tweets done
						}

						if (dbHelper.getTotalTweetCount() != 0) {

							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									tvTwitterSearchReady.setVisibility(View.VISIBLE);
									tvTwitterSearchInfo.setVisibility(View.GONE);
									pbTwitterSearch.setVisibility(View.GONE);

								}
							});

							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
							}

							// Start News from begining
							AppData.getInstance().setLastNewsID(0);
							startActivity(new Intent(SearchProgressActivity.this, ReadNewsActivity.class));
							finish();

						} else {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(SearchProgressActivity.this, "No avaible news for this keyword",
											Toast.LENGTH_LONG).show();

								}
							});
							finish();
						}
					}
				}).start();
			}
		});

		//

	}
}
