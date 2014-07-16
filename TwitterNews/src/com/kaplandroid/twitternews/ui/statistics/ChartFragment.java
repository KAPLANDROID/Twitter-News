package com.kaplandroid.twitternews.ui.statistics;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kaplandroid.twitternews.R;
import com.kaplandroid.twitternews.model.ChartModel;

/**
 * 
 * @author KAPLANDROID
 * 
 */
public class ChartFragment extends Fragment {

	/** Colors to be used for the pie slices. */
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN };
	/** The main series that will include all the data. */
	private CategorySeries mSeries = new CategorySeries("");
	/** The main renderer for the main dataset. */
	private DefaultRenderer mRenderer = new DefaultRenderer();
	/** The chart view that displays the data. */
	private GraphicalView mChartView;

	View fragmentContent;
	ArrayList<ChartModel> list;

	public static ChartFragment newInstance(ArrayList<ChartModel> list) {
		ChartFragment fr = new ChartFragment();
		fr.list = list;
		return fr;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragmentContent = inflater.inflate(R.layout.fragment_chart, container, false);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setStartAngle(180);
		mRenderer.setDisplayValues(true);

		for (ChartModel item : list) {
			addNew(item.getName(), item.getValue());
		}

		return fragmentContent;
	}

	private void addNew(String name, double value) {
		mSeries.add(name, value);
		SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
		renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
		mRenderer.addSeriesRenderer(renderer);
		mChartView.repaint();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mChartView == null) {
			LinearLayout layout = (LinearLayout) fragmentContent.findViewById(R.id.chart);
			mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
			mRenderer.setClickEnabled(true);
			mChartView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
					if (seriesSelection == null) {
						Toast.makeText(getActivity(), "No chart element selected", Toast.LENGTH_SHORT).show();
					} else {
						for (int i = 0; i < mSeries.getItemCount(); i++) {
							mRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
						}
						mChartView.repaint();
						Toast.makeText(
								getActivity(),
								"Chart data point index " + seriesSelection.getPointIndex() + " selected"
										+ " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
					}
				}
			});
			layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			mChartView.repaint();
		}
	}
}
