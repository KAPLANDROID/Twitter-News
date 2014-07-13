package com.kaplandroid.twitternews.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kaplandroid.twitternews.AppData;
import com.kaplandroid.twitternews.R;

/**
 * 
 * Validates input keyword Then sends it to progress Activity
 * 
 * @author KAPLANDROID
 * 
 */
public class SearchKeywordActivity extends Activity implements OnClickListener {

	EditText etSearchKeyword;

	Button btnSearchGoRead;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_keyword);

		etSearchKeyword = (EditText) findViewById(R.id.etSearchKeyword);
		btnSearchGoRead = (Button) findViewById(R.id.btnSearchGoRead);

		//

		btnSearchGoRead.setOnClickListener(this);

		//

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_chart) {
			// TODO open chart activity

			Toast.makeText(SearchKeywordActivity.this, "TODO open chart activity", Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		if (v == btnSearchGoRead) {

			// TO.DO validate input

			if (!validateInput(etSearchKeyword.getText().toString())) {
				Toast.makeText(SearchKeywordActivity.this, "Your keyword must be at  3 - 500 character length.",
						Toast.LENGTH_LONG).show();
				return;
			}
			// Input Valid

			// TO.DO save keyword

			AppData.getInstance().setLastKeyword(etSearchKeyword.getText().toString());

			//

			// TO.DO Open progress activity

			startActivity(new Intent(SearchKeywordActivity.this, SearchProgressActivity.class));

			//

			//

		}

	}

	private boolean validateInput(String text) {

		if (text != null) {
			if (text.length() > 4) {
				return true;
			}
		}

		return false;
	}

}
