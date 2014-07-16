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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.kaplandroid.twitternews.R;
import com.kaplandroid.twitternews.db.MobilikeDBHelper;
import com.kaplandroid.twitternews.model.SourceForDB;
import com.kaplandroid.twitternews.ui.InfoActivity;
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

	MobilikeDBHelper dbHelper;

	boolean isFirstTime = true;

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

		dbHelper = new MobilikeDBHelper(this);

		tvTotalNewdRead.setText("" + dbHelper.getTotalFeedbackCount());

		rbSource.setChecked(true);

		ArrayAdapter<SourceForDB> adapter = new ArrayAdapter<SourceForDB>(StatisticMainActivity.this,
				android.R.layout.simple_spinner_dropdown_item, dbHelper.getAllSources());
		spnSource.setAdapter(adapter);

		spnSource.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if (!isFirstTime) {
					openFeedbackBySource(((SourceForDB) spnSource.getSelectedItem()).getSourceID());

					System.out.println(spnSource.getSelectedItem());

				}
				isFirstTime = false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_info) {
			startActivity(new Intent(this, InfoActivity.class));
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

	ChartFragment source = null;
	ChartFragment feedback = null;
	ChartFragment feedbackBySource = null;

	@SuppressWarnings("unused")
	@Override
	public void onCheckedChanged(CompoundButton v, boolean isChecked) {

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

				source = ChartFragment.newInstance(dbHelper.getSourceTotals());// TO.DO
				ft.replace(flID, source);

				ft.commitAllowingStateLoss();
			}

		} else if (v == rbFeedback) {
			if (isChecked) {
				spnSource.setVisibility(View.INVISIBLE);

				fm = getSupportFragmentManager();
				ft = fm.beginTransaction();

				feedback = ChartFragment.newInstance(dbHelper.getFeedbackTotals());// TO.DO
				ft.replace(flID, feedback);

				ft.commitAllowingStateLoss();
			}

		} else if (v == rbFeedbackBySource) {
			if (isChecked) {
				spnSource.setVisibility(View.VISIBLE);

				openFeedbackBySource(((SourceForDB) spnSource.getSelectedItem()).getSourceID()); // TO.DO

			}

		}

		//

		//

	}

	private void openFeedbackBySource(int sourceID) {

		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();

		feedbackBySource = ChartFragment.newInstance(dbHelper.getFeedbackTotalsBySourceID(sourceID));// TO.DO
		ft.replace(flID, feedbackBySource);

		ft.commitAllowingStateLoss();
	}
}
