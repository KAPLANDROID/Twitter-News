package com.kaplandroid.twitternews.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kaplandroid.twitternews.AppData;
import com.kaplandroid.twitternews.R;
import com.kaplandroid.twitternews.db.MobilikeDBHelper;
import com.kaplandroid.twitternews.twitterapi.TwitterLoginActivity;

/**
 * Main Activity
 * 
 * @author KAPLANDROID
 * 
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {

	public static final int REQUESTCODE_TWITTERLOGIN = 99;

	ImageButton ibLoginTwitter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ibLoginTwitter = (ImageButton) findViewById(R.id.ibLoginTwitter);

		ibLoginTwitter.setOnClickListener(this);

		setupSQLITE();

	}

	private void setupSQLITE() {
		MobilikeDBHelper dbHelper = new MobilikeDBHelper(getApplicationContext());

		SharedPreferences settings = getSharedPreferences("SQL", MODE_PRIVATE);
		boolean firstTime = settings.getBoolean("firstTime", true);

		if (firstTime) {

			dbHelper.insertFeedback(AppData.FEEDBACK_COOL, getString(R.string.cool));
			dbHelper.insertFeedback(AppData.FEEDBACK_BORING, getString(R.string.boring));

			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("firstTime", false);
			editor.commit();

		}
		System.out.println(dbHelper.getTotalTweetCount());

	}

	@Override
	public void onClick(View v) {

		if (v == ibLoginTwitter) {
			startActivityForResult(new Intent(MainActivity.this, TwitterLoginActivity.class), REQUESTCODE_TWITTERLOGIN);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUESTCODE_TWITTERLOGIN) {
			if (resultCode == TwitterLoginActivity.TWITTER_LOGIN_RESULT_CODE_SUCCESS) {

				Toast.makeText(
						MainActivity.this,
						"AccessToken: " + TwitterLoginActivity.getAccessToken((MainActivity.this))
								+ "\nAccessTokenSecret: "
								+ (TwitterLoginActivity.getAccessTokenSecret(MainActivity.this)), Toast.LENGTH_LONG)
						.show();

				startActivity(new Intent(MainActivity.this, SearchKeywordActivity.class));

			} else if (resultCode == TwitterLoginActivity.TWITTER_LOGIN_RESULT_CODE_FAILURE) {
				Toast.makeText(MainActivity.this, "An Error Occured", Toast.LENGTH_LONG).show();
			}

		}

	}

}
