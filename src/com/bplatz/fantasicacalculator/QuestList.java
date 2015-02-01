package com.bplatz.fantasicacalculator;

public class QuestList
{
	private static QuestList INSTANCE;
	
	public Episode[] episodes;
	
	private QuestList()
	{
		episodes = new Episode[41];
		
		episodes[0] = new Episode1();
		
	}
	
	public static QuestList getInstance()
	{
		if (INSTANCE == null)
			INSTANCE = new QuestList();
		return INSTANCE;
	}
	
	public class Episode
	{
		public Quest[] quests;
		
		public Episode(int episode)
		{
			this(episode, 10);
		}
		
		public Episode(int episode, int max_quests)
		{
			quests = new Quest[max_quests];
			
			for (int i = 0; i < max_quests; i++)
			{
				quests[i] = new Quest();
				quests[i].episode = episode;
				quests[i].quest = i + 1;
				
				if (i < 6)
				{
					quests[i].cooldown = "5m";
				} else if (i == 6)
				{
					quests[i].cooldown = "60m";
					quests[i].max_allies = 4;
				} else
				{
					quests[i].cooldown = "15m";
					if (i == 7)
					{
						quests[7].base_quest_exp = 10 + (i + 3) * 5;
						quests[i].max_allies = 0;
					} else if (i == 8)
					{
						quests[8].base_quest_exp = 0;
						quests[i].max_allies = 5;
					} else if (i == 9)
						quests[i].max_allies = 0;
				}
			}
			
			// TODO EPISODE 2
			if (episode == 2)
			{
				for (int i = 0; i < 10; i++)
				{
					quests[i].max_allies = 3;
					quests[i].cooldown = "1m";
					quests[i].base_quest_exp = 120 + (i * 10);
				}
				
				quests[0].monster_count = 12;
				quests[1].monster_count = 16;
				quests[2].monster_count = 13;
				quests[3].monster_count = 19;
				quests[4].monster_count = 19;
				quests[5].monster_count = 19;
				quests[6].monster_count = 12;
				quests[7].monster_count = 15;
				quests[8].monster_count = 28;
				quests[9].monster_count = 30;
				
				quests[6].base_quest_exp = 220;
				quests[9].base_quest_exp = 200;
				
				quests[0].luna = 500;
				quests[1].luna = 550;
				quests[2].luna = 550;
				quests[3].luna = 600;
				quests[4].luna = 600;
				quests[5].luna = 650;
				quests[6].luna = 900;
				quests[7].luna = 550;
				quests[8].luna = 600;
				quests[9].luna = 900;
			}
			
			// TODO Episode 3
			if (episode == 3)
			{
				for (int i = 0; i < 6; i++)
				{
					quests[i].max_allies = 3;
					quests[i].cooldown = "1m";
					quests[i].base_quest_exp = 130 + (i * 10);
				}
				
				quests[6].max_allies = 3;
				
				quests[0].monster_count = 7;
				quests[1].monster_count = 12;
				quests[2].monster_count = 16;
				quests[3].monster_count = 13;
				quests[4].monster_count = 20;
				quests[5].monster_count = 18;
				quests[6].monster_count = 14;
				quests[7].monster_count = 18;
				quests[8].monster_count = 22;
				quests[9].monster_count = 23;
				
				quests[6].base_quest_exp = 230;
				quests[9].base_quest_exp = 180;
				
				quests[0].luna = 500;
				quests[1].luna = 550;
				quests[2].luna = 550;
				quests[3].luna = 600;
				quests[4].luna = 600;
				quests[5].luna = 650;
				quests[6].luna = 900;
				quests[7].luna = 550;
				quests[8].luna = 600;
				quests[9].luna = 900;
			}
			
			// TODO Episode 4
			if (episode == 4)
			{
				for (int i = 0; i < 6; i++)
				{
					quests[i].max_allies = 3;
					quests[i].cooldown = "1m";
					quests[i].base_quest_exp = 130 + (i * 10);
				}
				
				quests[6].max_allies = 3;
				
				quests[0].monster_count = 7;
				quests[1].monster_count = 12;
				quests[2].monster_count = 16;
				quests[3].monster_count = 13;
				quests[4].monster_count = 20;
				quests[5].monster_count = 18;
				quests[6].monster_count = 14;
				quests[7].monster_count = 18;
				quests[8].monster_count = 22;
				quests[9].monster_count = 23;
				
				quests[6].base_quest_exp = 230;
				quests[9].base_quest_exp = 180;
				
				quests[0].luna = 500;
				quests[1].luna = 550;
				quests[2].luna = 550;
				quests[3].luna = 600;
				quests[4].luna = 600;
				quests[5].luna = 650;
				quests[6].luna = 900;
				quests[7].luna = 550;
				quests[8].luna = 600;
				quests[9].luna = 900;
			}
			
		}
	}
	
	private class Episode1 extends Episode
	{
		
		public Episode1()
		{
			super(1, 5);
			
			for (int i = 0; i < 4; i++)
			{
				quests[i].max_allies = 2;
				quests[i].monster_count = 7;
				quests[i].cooldown = "1m";
				quests[i].base_quest_exp = 100 + (i * 10);
			}
			
			quests[3].max_allies = 3;
			quests[4].max_allies = 3;
			
			quests[2].monster_count = 10;
			quests[3].monster_count = 13;
			quests[4].monster_count = 8;
			
			quests[4].cooldown = "60m";
			quests[4].base_quest_exp = 180;
			
			quests[0].luna = 500;
			quests[1].luna = 550;
			quests[2].luna = 550;
			quests[3].luna = 600;
			quests[4].luna = 750;
		}
	}
	
	public class Quest
	{
		public int episode;
		public int quest;
		
		public int base_quest_exp;
		public int luna;
		public int max_allies;
		public int monster_count;
		public String cooldown;
	}
}
