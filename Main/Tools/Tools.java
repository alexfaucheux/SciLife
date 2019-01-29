package Tools;

import Tools.StacksQueues.*;

public class Tools
{
	//Converts String to UpperCase
	public static String Upper(String name)
	{
		String[] split = name.trim().split(" ");
		name = "";
		for(int i=0; i<split.length; i++)
			name += split[i].substring(0, 1).toUpperCase() + split[i].substring(1) + " ";
		return name;
	}

	//Sorts list alphabetically
	public static void insertionSort(String[] list)
	{
		//sorts names alphabetically by last name
		for(int i=1; i <= list.length - 1; i++)
		{
			int k = i - 1;
			while(k >= 0)
			{
				String name1 = list[i];
				String name2 = list[k];

				if(name1.compareTo(name2) >= 0) {break;}
				else
				{
					list[k] = name1;
					list[i] = name2;
					k -= 1;
					i -= 1;
				}
			}
		}
	}
}