package com.kaplandroid.twitternews.ui.reader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.kaplandroid.twitternews.R;
import com.kaplandroid.twitternews.ui.search.SearchKeywordActivity;
import com.kaplandroid.twitternews.ui.search.SearchProgressActivity;

/**
 * 
 * decision activity for end of news
 * 
 * @author KAPLANDROID
 * 
 */
public class EndOfNewsActivity extends Activity implements OnClickListener {

	Button btnEONYes;
	Button btnEONTry;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_of_news);

		btnEONYes = (Button) findViewById(R.id.btnEONYes);
		btnEONTry = (Button) findViewById(R.id.btnEONTry);

		btnEONYes.setOnClickListener(this);
		btnEONTry.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (v == btnEONTry) {
			Intent i = new Intent(EndOfNewsActivity.this, SearchKeywordActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);

		} else if (v == btnEONYes) {

			startActivity(new Intent(EndOfNewsActivity.this, SearchProgressActivity.class));

		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_chart) {
			// TODO open chart activity

			Toast.makeText(this, "TODO open chart activity", Toast.LENGTH_LONG).show();
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
