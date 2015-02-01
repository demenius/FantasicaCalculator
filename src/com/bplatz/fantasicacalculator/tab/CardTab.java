package com.bplatz.fantasicacalculator.tab;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bplatz.fantasicacalculator.CardList;
import com.bplatz.fantasicacalculator.FantasicaCalculator;
import com.bplatz.fantasicacalculator.ObservableScrollView;
import com.bplatz.fantasicacalculator.R;
import com.bplatz.fantasicacalculator.CardList.CardInfo;
import com.bplatz.fantasicacalculator.ObservableScrollView.ScrollViewListener;
import com.bplatz.fantasicacalculator.ViewPagerAdapter;

public class CardTab extends SherlockFragment implements OnClickListener, ScrollViewListener
{
	private ObservableScrollView scroll_view;
	private LinearLayout card_view;
	private TextView exp;
	private View arrow_up;
	private View arrow_down;
	
	@Override
	public SherlockFragmentActivity getSherlockActivity()
	{
		return super.getSherlockActivity();
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Get the view from fragmenttab2.xml
		View view = inflater.inflate(R.layout.card_tab_layout, container, false);
		card_view = (LinearLayout) view.findViewById(R.id.cards);
		exp = (TextView) view.findViewById(R.id.total_exp_value);
		scroll_view = (ObservableScrollView) view.findViewById(R.id.card_tab_scroll_view);
		scroll_view.setScrollViewListener(this);
		
		arrow_up = view.findViewById(R.id.arrow_up);
		arrow_down = view.findViewById(R.id.arrow_down);
		
		((Button) view.findViewById(R.id.reset)).setOnClickListener(this);
		((Button) view.findViewById(R.id.use)).setOnClickListener(this);
		listCards(inflater);
		return view;
	}
	
	@Override
	public void setUserVisibleHint(boolean hint)
	{
		super.setUserVisibleHint(hint);
		if (!hint)
			return;
		if (getSherlockActivity() != null)
		{
			CardList.getInstance().edit = null;
			listCards(LayoutInflater.from(getSherlockActivity()));
		}
		CardList.getInstance().use_level_up_info = false;
	}
	
	public void listCards(LayoutInflater inflater)
	{
		card_view.removeAllViews();
		View v;
		TextView text;
		
		int color[] = new int[FantasicaCalculator.MAX_STARS];
		color[0] = Color.parseColor("#945C24");
		color[1] = Color.parseColor("#BE9D7B");
		color[2] = Color.parseColor("#D9D9D9");
		color[3] = Color.parseColor("#E3E0A9");
		color[4] = Color.parseColor("#DBDCB4");
		color[5] = Color.parseColor("#FFE246");
		
		for (int i = 1; i <= FantasicaCalculator.MAX_STARS; i++)
		{
			final CardInfo[] list = CardList.getInstance().getList(i);
			
			if (list.length > 0)
			{
				v = inflater.inflate(R.layout.separator, null);
				text = (TextView) v.findViewById(R.id.star_string);
				text.setText(i + " Star Cards");
				text.setTextColor(color[i - 1]);
				card_view.addView(v);
			}
			
			for (int j = 0; j < list.length; j++)
			{
				if (list[j] == null)
					continue;
				v = inflater.inflate(R.layout.card_layout, null);
				text = (TextView) v.findViewById(R.id.card_info);
				
				final int list_num = j;
				OnClickListener listener = new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						if (v.getId() == R.id.remove)
						{
							CardList.getInstance().remove(list[list_num]);
							listCards(LayoutInflater.from(getSherlockActivity()));
						} else if (v.getId() == R.id.edit)
						{
							CardList.getInstance().edit = list[list_num];
							((FantasicaCalculator) getSherlockActivity()).getCustomActionBar().setSelectedNavigationItem(ViewPagerAdapter.TAB_INFO);
						}
					}
				};
				
				((Button) v.findViewById(R.id.remove)).setOnClickListener(listener);
				((Button) v.findViewById(R.id.edit)).setOnClickListener(listener);
				
				text.setText(list[list_num].getInfoString(false));
				card_view.addView(v);
			}
		}
		
		int total_exp = CardList.getInstance().getTotalExp();
		long perc_gain = CardList.getInstance().getExpPercent(total_exp);
		int[] info = CardList.getInstance().getLevelGainInfo(total_exp);
		String info_text = (info == null ? "" : "\n[Lvl: " + info[0] + " " + info[1] + "%]");
		
		exp.setText("Total Exp: " + total_exp + "(" + perc_gain + "%)" + info_text);
		
		ViewTreeObserver vto = card_view.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{
			@Override
			public void onGlobalLayout()
			{
				card_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				checkForScrolling(scroll_view);
			}
			
		});
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	
	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.reset)
		{
			CardList.getInstance().reset();
			listCards(LayoutInflater.from(getSherlockActivity()));
		} else if (v.getId() == R.id.use)
		{
			CardList.getInstance().use_level_up_info = true;
			((FantasicaCalculator) getSherlockActivity()).getCustomActionBar().setSelectedNavigationItem(ViewPagerAdapter.TAB_INFO);
		}
	}
	
	private void checkForScrolling(ObservableScrollView pScrollView)
	{
		arrow_up.setVisibility(ViewCompat.canScrollVertically(pScrollView, ObservableScrollView.UP) ? View.VISIBLE : View.INVISIBLE);
		arrow_down.setVisibility(ViewCompat.canScrollVertically(pScrollView, ObservableScrollView.DOWN) ? View.VISIBLE : View.INVISIBLE);
	}
	
	@Override
	public void onScrollChanged(ObservableScrollView pScrollView, int x, int y, int oldx, int oldy)
	{
		checkForScrolling(pScrollView);
	}
}