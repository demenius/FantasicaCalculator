package com.bplatz.fantasicacalculator.tab;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bplatz.fantasicacalculator.CardList;
import com.bplatz.fantasicacalculator.FantasicaCalculator;
import com.bplatz.fantasicacalculator.CardList.CardInfo;
import com.bplatz.fantasicacalculator.R;
import com.bplatz.fantasicacalculator.ViewPagerAdapter;

public class ExpTab extends SherlockFragment implements OnClickListener, OnItemSelectedListener, CompoundButton.OnCheckedChangeListener
{

	private View expLayout;

	private EditText current_level;
	private EditText current_exp_per;
	private EditText target_level;
	private EditText exp_needed;
	private EditText feeders_needed;

	private EditText level;
	private EditText number;
	private EditText exp_gain;
	private CheckBox type;
	private CheckBox bonus;
	private int stars = 0;
	
	private Button add;
	
	private Spinner star_spinner;
	
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
		if(!hint)
			return;
		CardInfo edit = CardList.getInstance().edit;
		if(edit != null)
		{
			level.setText(""+edit.level);
			type.setChecked(edit.sameType);
			number.setText(""+edit.number);
			star_spinner.setSelection(edit.stars);
			add.setText("Edit");
		} else if(add != null)
		{
			add.setText("Add");
		}
		if(CardList.getInstance().use_level_up_info)
		{
			final int[] level_up_info = new int[2];
			level_up_info[0] = CardList.getInstance().level_up_info[0];
			level_up_info[1] = CardList.getInstance().level_up_info[1];

			this.current_level.setText("" + level_up_info[0]);
			this.current_exp_per.setText("" + level_up_info[1]);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		expLayout = inflater.inflate(R.layout.exp_tab_layout, container, false);
		
		final View content = expLayout.findViewById(R.id.content);
		
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

		exp_needed = (EditText) expLayout.findViewById(R.id.exp_needed);
		exp_needed.setKeyListener(null);
		
		feeders_needed = (EditText) expLayout.findViewById(R.id.feeders_needed);
		feeders_needed.setKeyListener(null);
		
		TextWatcher watcher = new TextWatcher()
		{
			public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
			{
				// TODO: Implement this method
			}
			
			public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
			{
				setExpNeeded();
			}
			
			public void afterTextChanged(Editable p1)
			{
				// TODO: Implement this method
			}
		};
		
		current_level = (EditText) expLayout.findViewById(R.id.current_level);
		current_level.addTextChangedListener(watcher);
		
		current_exp_per = (EditText) expLayout.findViewById(R.id.current_exp);
		current_exp_per.addTextChangedListener(watcher);
		
		target_level = (EditText) expLayout.findViewById(R.id.target_level);
		target_level.addTextChangedListener(watcher);
		
		exp_gain = (EditText) expLayout.findViewById(R.id.exp_gain);
		exp_gain.setKeyListener(null);
		level = (EditText) expLayout.findViewById(R.id.feeder_level);
		level.addTextChangedListener(new TextWatcher()
		{
			
			public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
			{
				// TODO: Implement this method
			}
			
			public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
			{
				setGain();
			}
			
			public void afterTextChanged(Editable p1)
			{
				// TODO: Implement this method
			}
			
		});
		
		number = (EditText) expLayout.findViewById(R.id.card_num);
		
		star_spinner = (Spinner) expLayout.findViewById(R.id.stars);
		star_spinner.setOnItemSelectedListener(this);
		
		Spinner s2 = (Spinner) expLayout.findViewById(R.id.blessing);
		s2.setOnItemSelectedListener(this);
		
		type = (CheckBox) expLayout.findViewById(R.id.type);
		type.setOnCheckedChangeListener(this);
		
		bonus = (CheckBox) expLayout.findViewById(R.id.bonus);
		bonus.setOnCheckedChangeListener(this);
		
		add = (Button) expLayout.findViewById(R.id.add);
		add.setOnClickListener(this);

		exp_needed.setFocusable(false);
		feeders_needed.setFocusable(false);
		exp_gain.setFocusable(false);
		
		return expLayout;
	}
	
	private int getTargetLevel()
	{
		if(target_level == null)
			return -1;
		String tar_level = target_level.getText().toString();
		if(tar_level.length() == 0)
			return -1;
		return Integer.parseInt(tar_level);
	}
	
	private void setExpNeeded()
	{
		if (current_level == null || target_level == null || current_exp_per == null)
			return;
		String cur_level = current_level.getText().toString();
		String cur_exp_per = current_exp_per.getText().toString();
		String tar_level = target_level.getText().toString();
		int cur = -1;
		int exp_per = 0;
		int tar = -1;
		if (cur_level.length() > 0)
		{
			cur = Integer.parseInt(cur_level);
			if (cur > FantasicaCalculator.MAX_LEVEL)
			{
				current_level.setText(""+ FantasicaCalculator.MAX_LEVEL);
				return;
			}
			if (cur < 1)
			{
				current_level.setText("1");
				return;
			}
			CardList.getInstance().current_level = cur;
		} else
		{
			CardList.getInstance().current_level = 1;
			return;
		}
		
		if (cur_exp_per.length() > 0)
		{
			exp_per = Integer.parseInt(cur_exp_per);
			if (exp_per > 99)
			{
				current_exp_per.setText("99");
				return;
			}
			if (exp_per < 0)
			{
				current_exp_per.setText("0");
				return;
			}
			CardList.getInstance().current_exp = exp_per * CardList.expForLevel(CardList.getInstance().current_level) / 100;
		} else
		{
			CardList.getInstance().current_exp = 0;
		}

		setGain();
		
		if (tar_level.length() > 0)
		{
			tar = Integer.parseInt(tar_level);
			if (tar >  FantasicaCalculator.MAX_LEVEL)
			{
				target_level.setText("" + FantasicaCalculator.MAX_LEVEL);
				return;
			}
			if (tar < 1)
			{
				target_level.setText("1");
				return;
			}
		}
		
		exp_needed.setText("" + CardList.getInstance().getExpNeededToTarget(tar));
	}
	
	private void setGain()
	{
		String text = level.getText().toString();
		if (text.length() > 0)
		{
			int lvl = Integer.parseInt(level.getText().toString());
			if (lvl >  FantasicaCalculator.MAX_LEVEL)
			{
				level.setText("" + FantasicaCalculator.MAX_LEVEL);
				return;
			}
			int xp = CardList.base[stars] + CardList.extra[stars] * (lvl - 1);
			float xType = type.isChecked() ? 1.05f : 1.0f;
			float xBonus = bonus.isChecked() ? 1.50f : 1.0f;
			int exp = (int) (xp * CardList.getInstance().blessing * xType * xBonus);
			long exp_perc = CardList.getInstance().getExpPercent(exp);
			int[] info = CardList.getInstance().getLevelGainInfo(exp);
			String info_text = (info == null ? "" : "\n[Lvl: " + info[0] + " " + info[1] + "%]");
			
			exp_gain.setText(exp + "(" + exp_perc + "%)" + info_text);
			
			int feeders = CardList.getInstance().getFeedersNeeded(exp, getTargetLevel());
			feeders_needed.setText("" + feeders);
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> p1, View view, int pos, long id)
	{
		if (p1.getId() == R.id.stars)
			stars = pos+1;
		else if (p1.getId() == R.id.blessing)
			CardList.getInstance().blessing = 1.0f + (pos / 100.0f);
		setGain();
	}
	
	public void onNothingSelected(AdapterView<?> p1){}
	
	public void onClick(View p1)
	{
		if (p1.getId() == R.id.add)
		{
			String num_text = number.getText().toString();
			String level_text = level.getText().toString();
			if (num_text.length() < 1 || level_text.length() < 1)
				return;
			int num = Integer.parseInt(num_text);
			int level = Integer.parseInt(level_text);
			if (num < 1 || level < 1)
				return;
			
			CardList list = CardList.getInstance();

			CardInfo cardInfo = list.new CardInfo();
			cardInfo.number = num;
			cardInfo.level = level;
			cardInfo.stars = stars;
			cardInfo.sameType = type.isChecked();
			
			if(add.getText().toString().equalsIgnoreCase("Edit"))
			{
				list.editCards(cardInfo);
				((FantasicaCalculator)getSherlockActivity()).getCustomActionBar().setSelectedNavigationItem(ViewPagerAdapter.TAB_CARDS);
			}else
				list.addCards(cardInfo);
		}
	}
	
	public void onCheckedChanged(CompoundButton p1, boolean p2)
	{
		CardList.getInstance().setBonusExp(bonus.isChecked());
		setGain();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}
	
}