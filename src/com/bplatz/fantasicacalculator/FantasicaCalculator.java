package com.bplatz.fantasicacalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.appflood.AppFlood;

public class FantasicaCalculator extends SherlockFragmentActivity
{
	
	// Declare Variables
	private ActionBar mActionBar;
	private ViewPager mPager;
	private View banner;
	
	int baseHeight = -1;

	public static int MAX_STARS = 6;
	public static int MAX_LEVEL = 140;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		AppFlood.initialize(this, this.getString(R.string.app_key), this.getString(R.string.app_key_2), AppFlood.AD_ALL);
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get the view from activity_main.xml
		setContentView(R.layout.activity_fantasica_calculator);
		
		//AFBannerView is a subclass of view. You could put it in a layout like a view
		
		//AFBannerView afBannerView = new AFBannerView(this);
		//customize the position and size of the ad view
		//LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		// Activate Navigation Mode Tabs
		mActionBar = getSupportActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

		banner = findViewById(R.id.banner);
		/*banner.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				startActivity(new Intent(Intent.ACTION_VIEW, 
						Uri.parse("market://details?id=com.bplatz.balloonza")));
			}
			
		});*/
		
		// Locate ViewPager in activity_main.xml
		mPager = (ViewPager) findViewById(R.id.pager);
		
		
		// Activate Fragment Manager
		FragmentManager fm = getSupportFragmentManager();
		
		// Capture ViewPager page swipes
		ViewPager.SimpleOnPageChangeListener ViewPagerListener = new ViewPager.SimpleOnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				super.onPageSelected(position);
				// Find the ViewPager Position
				InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.hideSoftInputFromWindow(mPager.getWindowToken(), 0);
				mActionBar.setSelectedNavigationItem(position);
			}
		};
		
		mPager.setOnPageChangeListener(ViewPagerListener);
		// Locate the adapter class called ViewPagerAdapter.java
		ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(fm);
		// Set the View Pager Adapter into ViewPager
		mPager.setAdapter(viewpageradapter);
		
		// Capture tab button clicks
		ActionBar.TabListener tabListener = new ActionBar.TabListener()
		{
			
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft)
			{
				// Pass the position on tab click to ViewPager
				mPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft)
			{
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft)
			{
				
			}
		};
		
		//Tab tab = mActionBar.newTab().setText("Quests").setTabListener(tabListener);
		//mActionBar.addTab(tab);
		
		Tab tab = mActionBar.newTab().setText("Info").setTabListener(tabListener);
		mActionBar.addTab(tab);
		
		tab = mActionBar.newTab().setText("Cards").setTabListener(tabListener);
		mActionBar.addTab(tab);
		
		mActionBar.setSelectedNavigationItem(ViewPagerAdapter.TAB_INFO);
		
		
		
	}
	
	@Override
	public void onDestroy()
	{
		AppFlood.destroy();
		super.onDestroy();
	}


	public ActionBar getCustomActionBar()
	{
		return mActionBar;
	}


	public void setBannerVisibility(int visibility)
	{
		this.banner.setVisibility(visibility);
	}
	
}