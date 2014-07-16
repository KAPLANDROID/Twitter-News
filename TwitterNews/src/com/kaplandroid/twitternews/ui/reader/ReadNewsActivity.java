package com.kaplandroid.twitternews.ui.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.kaplandroid.twitternews.AppData;
import com.kaplandroid.twitternews.R;
import com.kaplandroid.twitternews.db.MobilikeDBHelper;
import com.kaplandroid.twitternews.ui.search.SearchKeywordActivity;

/**
 * 
 * News Read Activity. Reads web url using lastNewsID stored in singleton
 * 
 * 
 * @author KAPLANDROID
 * 
 */
public class ReadNewsActivity extends Activity implements OnClickListener {

	WebView wvNewsRead;

	Button btnNewsReadCool;
	Button btnNewsReadBoring;

	MobilikeDBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_read);

		wvNewsRead = (WebView) findViewById(R.id.wvNewsRead);
		btnNewsReadCool = (Button) findViewById(R.id.btnNewsReadCool);
		btnNewsReadBoring = (Button) findViewById(R.id.btnNewsReadBoring);

		btnNewsReadCool.setOnClickListener(this);
		btnNewsReadBoring.setOnClickListener(this);

		dbHelper = new MobilikeDBHelper(ReadNewsActivity.this);

		AppData.getInstance().increaseNewsIDBy1();
		loadNewPage();

	}

	private void loadNewPage() {
		WebSettings webSettings = wvNewsRead.getSettings();
		webSettings.setBuiltInZoomControls(true);

		wvNewsRead.setWebViewClient(new Callback());

		String currentURL = dbHelper.getWeblinkBydbIDIfAvaible(AppData.getInstance().getLastNewsID());

		// if empty string found, that means we read all of news.
		if (currentURL.length() != 0) {

			wvNewsRead.loadUrl(currentURL);

			Toast.makeText(ReadNewsActivity.this, "Tweet ID: " + AppData.getInstance().getLastNewsID(),
					Toast.LENGTH_SHORT).show();
		} else {

			// TO.DO Open 2.1 Screen

			startActivity(new Intent(ReadNewsActivity.this, EndOfNewsActivity.class));
			finish();

		}
	}

	private class Callback extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return (false);
		}

	}

	@Override
	public void onClick(View v) {

		if (v == btnNewsReadBoring) {
			setFeedback(AppData.FEEDBACK_BORING);
		} else if (v == btnNewsReadCool) {
			setFeedback(AppData.FEEDBACK_COOL);
		}

	}

	private void setFeedback(int id) {
		dbHelper.insertFeedbackToTweet(AppData.getInstance().getLastNewsID(), id);

		AppData.getInstance().increaseNewsIDBy1();
		loadNewPage();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_chart) {
			// TODO open chart activity

			Toast.makeText(ReadNewsActivity.this, "TODO open chart activity", Toast.LENGTH_LONG).show();
		} else if (item.getItemId() == R.id.action_pen) {
			Intent i = new Intent(this, SearchKeywordActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chart_pen_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
