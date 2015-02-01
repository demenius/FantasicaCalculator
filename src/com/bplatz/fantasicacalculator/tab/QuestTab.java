package com.bplatz.fantasicacalculator.tab;

/*import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bplatz.fantasicacalculator.CardList;
import com.bplatz.fantasicacalculator.FantasicaCalculator;
import com.bplatz.fantasicacalculator.R;

public class QuestTab extends SherlockFragment implements OnItemSelectedListener
{
	private View questLayout;
	
	private Spinner episode;
	private Spinner quest;
	private Spinner allies;
	private EditText quest_exp;
	private EditText cooldown;
	private EditText monsters;
	private EditText num_runs;
	private EditText time_needed;	
	
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
	public void setUserVisibleHint(boolean hint)
	{
		super.setUserVisibleHint(hint);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		questLayout = inflater.inflate(R.layout.quest_tab_layout, container, false);
		
		final View content = questLayout.findViewById(R.id.quest_content);
		
		content.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{

			@Override
			public void onGlobalLayout()
			{
				int rootHeight = content.getRootView().getHeight();
				int contentHeight = content.getHeight();
				if(contentHeight < rootHeight/2)
				{
					((FantasicaCalculator)getSherlockActivity()).setBannerVisibility(View.GONE);
				} else
				{
					((FantasicaCalculator)getSherlockActivity()).setBannerVisibility(View.VISIBLE);
				}
			}
			
		});
		
		this.episode = (Spinner) questLayout.findViewById(R.id.episode);
		this.episode.setOnItemSelectedListener(this);

		this.quest = (Spinner) questLayout.findViewById(R.id.quest);
		this.quest.setOnItemSelectedListener(this);

		this.allies = (Spinner) questLayout.findViewById(R.id.allies);
		this.allies.setOnItemSelectedListener(this);
		
		
		
		quest_exp = (EditText) questLayout.findViewById(R.id.quest_exp);
		quest_exp.setKeyListener(null);
		quest_exp.setFocusable(false);

		cooldown = (EditText) questLayout.findViewById(R.id.cooldown);
		cooldown.setKeyListener(null);
		cooldown.setFocusable(false);
		
		monsters = (EditText) questLayout.findViewById(R.id.monsters);
		monsters.setKeyListener(null);
		monsters.setFocusable(false);

		num_runs = (EditText) questLayout.findViewById(R.id.num_runs);
		num_runs.setKeyListener(null);
		num_runs.setFocusable(false);
		
		time_needed = (EditText) questLayout.findViewById(R.id.time_needed);
		time_needed.setKeyListener(null);
		time_needed.setFocusable(false);
		
		
		return questLayout;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

	@Override
	public void onItemSelected(AdapterView<?> p1, View view, int pos, long id)
	{
		if (p1.getId() == R.id.episode)
		{
			episode.set
		}
	}
	
	public void onNothingSelected(AdapterView<?> p1){}
}
*/