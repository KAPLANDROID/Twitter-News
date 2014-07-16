package com.kaplandroid.twitternews.ui.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kaplandroid.twitternews.R;
import com.kaplandroid.twitternews.db.MobilikeDBHelper;
import com.kaplandroid.twitternews.ui.search.SearchKeywordActivity;

/**
 * 
 * @author KAPLANDROID
 * 
 */
public class StatisticMainActivity extends ActionBarActivity implements OnCheckedChangeListener {

	TextView tvTotalNewdRead;

	RadioButton rbSource;
	RadioButton rbFeedback;
	RadioButton rbFeedbackBySource;

	Spinner spnSource;

	int flID = R.id.flChartRoot;

	FragmentTransaction ft;
	FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics_main);

		tvTotalNewdRead = (TextView) findViewById(R.id.tvTotalNewdRead);

		rbSource = (RadioButton) findViewById(R.id.rbSource);
		rbFeedback = (RadioButton) findViewById(R.id.rbFeedback);
		rbFeedbackBySource = (RadioButton) findViewById(R.id.rbFeedbackBySource);

		spnSource = (Spinner) findViewById(R.id.spnSource);

		rbSource.setOnCheckedChangeListener(this);
		rbFeedback.setOnCheckedChangeListener(this);
		rbFeedbackBySource.setOnCheckedChangeListener(this);

		MobilikeDBHelper dbHelper = new MobilikeDBHelper(this);

		tvTotalNewdRead.setText("" + dbHelper.getTotalFeedbackCount());

		rbSource.setChecked(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_info) {
			// TODO open info activity

			Toast.makeText(this, "TODO open info activity", Toast.LENGTH_LONG).show();
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
		inflater.inflate(R.menu.pen_info_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	String checkedColor = "#297ADD";
	String unCheckedColor = "#d2d2d2";

	@SuppressWarnings("unused")
	@Override
	public void onCheckedChanged(CompoundButton v, boolean isChecked) {

		ChartFragment source = null;
		ChartFragment feedback = null;
		ChartFragment feedbackBySource = null;

		rbSource.setBackgroundColor(rbSource.isChecked() ? Color.parseColor(checkedColor) : Color
				.parseColor(unCheckedColor));
		rbSource.setTextColor(rbSource.isChecked() ? Color.WHITE : Color.BLACK);
		rbFeedback.setBackgroundColor(rbFeedback.isChecked() ? Color.parseColor(checkedColor) : Color
				.parseColor(unCheckedColor));
		rbFeedback.setTextColor(rbFeedback.isChecked() ? Color.WHITE : Color.BLACK);
		rbFeedbackBySource.setBackgroundColor(rbFeedbackBySource.isChecked() ? Color.parseColor(checkedColor) : Color
				.parseColor(unCheckedColor));
		rbFeedbackBySource.setTextColor(rbFeedbackBySource.isChecked() ? Color.WHITE : Color.BLACK);

		if (v == rbSource) {
			if (isChecked) {
				spnSource.setVisibility(View.INVISIBLE);

				fm = getSupportFragmentManager();
				ft = fm.beginTransaction();

				if (feedback != null) {
					ft.hide(feedback);
				}
				if (feedbackBySource != null) {
					ft.hide(feedbackBySource);
				}

				if (source == null) {

					source = ChartFragment.newInstance(null);// TODO
					ft.add(flID, source);
				} else {
					ft.show(source);
				}

				ft.commitAllowingStateLoss();
			}

		} else if (v == rbFeedback) {
			if (isChecked) {
				spnSource.setVisibility(View.INVISIBLE);

				fm = getSupportFragmentManager();
				ft = fm.beginTransaction();

				if (source != null) {
					ft.hide(source);
				}
				if (feedbackBySource != null) {
					ft.hide(feedbackBySource);
				}

				if (feedback == null) {
					feedback = ChartFragment.newInstance(null);// TODO
					ft.add(flID, feedback);
				} else {
					ft.show(feedback);
				}

				ft.commitAllowingStateLoss();
			}

		} else if (v == rbFeedbackBySource) {
			if (isChecked) {
				spnSource.setVisibility(View.VISIBLE);

				fm = getSupportFragmentManager();
				ft = fm.beginTransaction();

				if (source != null) {
					ft.hide(source);
				}
				if (feedback != null) {
					ft.hide(feedback);
				}

				if (feedbackBySource == null) {
					feedbackBySource = ChartFragment.newInstance(null);// TODO
					ft.add(flID, feedbackBySource);
				} else {
					ft.show(feedbackBySource);
				}

				ft.commitAllowingStateLoss();
			}

		}

		//

		//

	}
}
