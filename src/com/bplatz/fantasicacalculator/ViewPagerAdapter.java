package com.bplatz.fantasicacalculator;

import com.bplatz.fantasicacalculator.tab.CardTab;
import com.bplatz.fantasicacalculator.tab.ExpTab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
	
	// Declare the number of ViewPager pages
	public final int PAGE_COUNT = 2;
	
	public final static int TAB_CARDS = 1;
	public final static int TAB_INFO = 0;
	public final static int TAB_QUEST = 0;
	
	public ViewPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}
	
	@Override
	public Fragment getItem(int arg0)
	{
		switch (arg0)
		{
			//case TAB_QUEST:
				//QuestTab fragmenttab0 = new QuestTab();
				//return fragmenttab0;
		
			case TAB_INFO:
				ExpTab fragmenttab1 = new ExpTab();
				return fragmenttab1;
				
			case TAB_CARDS:
				CardTab fragmenttab2 = new CardTab();
				return fragmenttab2;
		}
		return null;
	}
	
	@Override
	public int getCount()
	{
		return PAGE_COUNT;
	}
	
}