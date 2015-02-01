package com.bplatz.fantasicacalculator;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;

public class CardList
{
	private static CardList INSTANCE;
	
	// Map<Star, <sameType, <Level, CardInfo>>>
	
	private Map<Integer, Map<Boolean, Map<Integer, CardInfo>>> cards;
	
	public CardInfo edit = null;
	
	public boolean bonusExp = false;
	
	public int current_level = 1;
	public int current_exp = 0;
	public int[] level_up_info;
	public boolean use_level_up_info;
	public float blessing = 1.0f;
	public static final int[] base = { 0, 150, 450, 900, 1350, 2250, 4500 };
	public static final int[] extra = { 0, 50, 150, 300, 450, 750, 1500 };
	
	public static final int[] exp = { 50, 60, 70, 100, 150, 325 };
	public static final int[] bexp = { 100, 12600, 47200, 107900, 202900, 339700 };
	public static final int[] lvl_break = { 1, 22, 42, 62, 82, 101 };
	
	@SuppressLint("UseSparseArrays")
	private CardList()
	{
		cards = new HashMap<Integer, Map<Boolean, Map<Integer, CardInfo>>>();
		for (int i = 1; i <= FantasicaCalculator.MAX_STARS; i++) // Add Stars 1
																	// To
																	// MAX_STARS
		{
			cards.put(i, new HashMap<Boolean, Map<Integer, CardInfo>>());
			cards.get(i).put(true, new HashMap<Integer, CardInfo>());
			cards.get(i).put(false, new HashMap<Integer, CardInfo>());
		}
		
		level_up_info = new int[2];
	}
	
	public void reset()
	{
		for (int i = 1; i <= FantasicaCalculator.MAX_STARS; i++)
		{
			cards.get(i).get(true).clear();
			cards.get(i).get(false).clear();
		}
	}
	
	public void remove(CardInfo cardInfo)
	{
		cards.get(cardInfo.stars).get(cardInfo.sameType).remove(cardInfo.level);
	}
	
	public void editCards(CardInfo cardInfo)
	{
		remove(edit);
		addCards(cardInfo);
	}
	
	public void addCards(CardInfo cardInfo)
	{
		if (cards.get(cardInfo.stars).get(cardInfo.sameType).containsKey(cardInfo.level))
			cardInfo.number += cards.get(cardInfo.stars).get(cardInfo.sameType).get(cardInfo.level).number;
		cards.get(cardInfo.stars).get(cardInfo.sameType).put(cardInfo.level, cardInfo);
	}
	
	public CardInfo[] getList(int stars)
	{
		
		Map<Integer, CardInfo> same = cards.get(stars).get(true);
		Map<Integer, CardInfo> different = cards.get(stars).get(false);
		
		CardInfo[] list = new CardInfo[same.size() + different.size()];
		int i = 0;
		for (Integer level : same.keySet())
		{
			list[i] = same.get(level);
			i++;
		}
		for (Integer level : different.keySet())
		{
			list[i] = different.get(level);
			i++;
		}
		
		return list;
	}
	
	public int getTotalExp()
	{
		int exp = 0;
		for (int i = 1; i <= FantasicaCalculator.MAX_STARS; i++)
		{
			exp += getExpForGroup(cards.get(i).get(true));
			exp += getExpForGroup(cards.get(i).get(false));
		}
		
		return exp;
	}
	
	private int getExpForGroup(Map<Integer, CardInfo> map)
	{
		int exp = 0;
		
		for (Integer level : map.keySet())
		{
			exp += getExpForLevelSet(map.get(level));
		}
		
		return exp;
	}
	
	private int getExpForLevelSet(CardInfo cardInfo)
	{
		int xp = base[cardInfo.stars] + extra[cardInfo.stars] * (cardInfo.level - 1);
		float xType = cardInfo.sameType ? 1.05f : 1.0f;
		float xBonus = bonusExp ? 1.50f : 1.0f;
		int exp = (int) (xp * blessing * xType * xBonus);
		return exp * cardInfo.number;
	}
	
	public void setBonusExp(boolean flag)
	{
		bonusExp = flag;
	}
	
	public long getExpPercent(int exp_gained)
	{
		int next_level = current_level;
		int exp_needed = expForLevel(next_level);
		double per = 0;
		while (exp_gained > exp_needed)
		{
			per += 100;
			exp_gained -= exp_needed;
			next_level++;
			exp_needed = expForLevel(next_level);
		}
		// Rest Of Exp_Gained Is Less Than A Level
		// Calculate Percent Of Level
		per += (exp_gained * 100.0 / exp_needed);
		return Math.round(per);
	}
	
	public int[] getLevelGainInfo(int exp)
	{
		int total_exp = exp + this.current_exp;
		
		long total_percent = this.getExpPercent(total_exp);
		
		level_up_info[0] = this.current_level;
		while (total_percent >= 100)
		{
			level_up_info[0]++;
			total_percent -= 100;
		}
		
		level_up_info[1] = (int) total_percent;
		
		return level_up_info;
	}
	
	public static int expForLevel(int level)
	{
		return expToLevel(level + 1) - expToLevel(level);
	}
	
	public static int expToLevel(int target)
	{
		
		/*return (int) ((93.75 * target * target) - (12043.75 * target) + 558725 + 
				Math.abs(target - 22) * ((2.5 * target) - 52.5) + 
				Math.abs(target - 42) * ((2.5 * target) - 102.5) +
				Math.abs(target - 62) * ((7.5 * target) - 457.5) +
				Math.abs(target - 82) * ((12.5 * target) - 1012.5) +
				Math.abs(target - 101) * ((43.75 * target) - 4375));*/
		
		int xp = 0;
		for(int i = 1; i <= target; i++)
			xp += xpForLevel(i);
		return xp;
	}
	
	private static int xpForLevel(final int level)
	{
		if (level == 1)
			return 0;
		int exp = level * 50;
		if (level > 22) // Need 60 Exp, Level > 22, Have 50 Need 10 More
			exp += (level - 22) * 10;
		if (level > 42) // Need 70 Exp, Level > 42, Have 60 Need 10 More
			exp += (level - 42) * 10;
		if (level > 62) // Need 100 Exp, Level > 62, Have 70 Need 30 More
			exp += (level - 62) * 30;
		if (level > 82) // Need 150 Exp, Level > 82, Have 100 Need 50 More
			exp += (level - 82) * 50;
		if (level > 102) // Need 300 Exp, Level > 102, Have 150 Need 150 More
			exp += (level - 102) * 150;
		if (level > 122) // Need 400 Exp, Level > 122, Have 300 Need 100 More
			exp += (level - 122) * 100;
		
		return exp;
	}
	
	public int getFeedersNeeded(int exp_per_card, int target)
	{
		if (target == -1)
			return 0;
		int expToTarget = this.getExpNeededToTarget(target);
		int feeders = 0;
		while (expToTarget > 0)
		{
			feeders++;
			expToTarget -= exp_per_card;
		}
		return feeders;
	}
	
	public int getExpNeededToTarget(int target)
	{
		if (target > this.current_level)
			return expToLevel(target) - expToLevel(this.current_level) - this.current_exp;
		return 0;
	}
	
	public static CardList getInstance()
	{
		if (INSTANCE == null)
			INSTANCE = new CardList();
		return INSTANCE;
	}
	
	public class CardInfo
	{
		public int number;
		public int level;
		public int stars;
		
		public boolean sameType;
		
		public String getInfoString(boolean showStar)
		{
			return "Level " + level + ", " + number + " Card" + (number > 1 ? "s " : " ") + (showStar ? (stars + 1) + " Star Cards" : "")
					+ (sameType ? "\nSame Type" : "\nDifferent Type");
		}
	}
}
