package com.kaplandroid.twitternews.ui;

import android.app.Activity;
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
		wvNewsRead.loadUrl(dbHelper.getWeblinkBydbID(AppData.getInstance().getLastNewsID()));

		Toast.makeText(ReadNewsActivity.this, "Tweet ID: " + AppData.getInstance().getLastNewsID(), Toast.LENGTH_SHORT)
				.show();
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
			dbHelper.insertFeedbackToTweet(AppData.getInstance().getLastNewsID(), AppData.FEEDBACK_BORING);
			AppData.getInstance().increaseNewsIDBy1();
			loadNewPage();
		} else if (v == btnNewsReadCool) {
			dbHelper.insertFeedbackToTweet(AppData.getInstance().getLastNewsID(), AppData.FEEDBACK_COOL);
			AppData.getInstance().increaseNewsIDBy1();
			loadNewPage();
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_chart) {
			// TODO open chart activity

			Toast.makeText(ReadNewsActivity.this, "TODO open chart activity", Toast.LENGTH_LONG).show();
		} else if (item.getItemId() == R.id.action_pen) {
			finish();
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chat_pen_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
