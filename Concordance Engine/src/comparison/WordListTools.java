package comparison;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import structs.WordFrequency;

public final class WordListTools {
	int ListLimit;
	public WordListTools(int Limit){
		this.ListLimit = Limit;
	}
	
	public static Collection<String> NormalizeCollection(Collection<String> list) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		for(String str : list) {
			map.put(str, null);
		}
		return map.keySet();
	}
	
	public static Collection<String> TopItems(Collection<String> SubItems, int Limit)
	{
		HashMap<String, WordFrequency> frequency = new HashMap<String, WordFrequency>();
		WordFrequency updateentry = new WordFrequency();
		if(SubItems != null)
		{
			for(String subitem : SubItems)
			{
				if(!frequency.containsKey(subitem))
				{
					WordFrequency entry = new WordFrequency();
					entry.Word = subitem;
					entry.Frequency = 0;
					frequency.put(subitem, entry);
				}else
				{
					updateentry = frequency.get(subitem);
					frequency.remove(subitem);
					updateentry.Frequency++;
					frequency.put(subitem, updateentry);
				}
			}
		}
		int freqcount = 0;
		int minbound = Limit;
		String[] keyarray = null;
		while (frequency.keySet().size() > Limit)
		{
			keyarray = frequency.keySet().toArray(new String[frequency.keySet().size()]);
			for (String keyitem : keyarray)
			{
				if(frequency.keySet().size() <= minbound)
				break;
				if(frequency.get(keyitem).Frequency == freqcount)
					frequency.remove(keyitem);
			}
			freqcount++;
		}
		keyarray = frequency.keySet().toArray(new String[frequency.keySet().size()]);
		Vector<String> TopItems = new Vector<String>();
		for(String keyitem : keyarray)
		{
			TopItems.add(keyitem);
		}
		return TopItems;
	}
	public Collection<String> TopItems(Collection<String> SubItems)
	{
		HashMap<String, WordFrequency> frequency = new HashMap<String, WordFrequency>();
		WordFrequency updateentry = new WordFrequency();
		if(SubItems != null)
		{
			for(String subitem : SubItems)
			{
				if(!frequency.containsKey(subitem))
				{
					WordFrequency entry = new WordFrequency();
					entry.Word = subitem;
					entry.Frequency = 0;
					frequency.put(subitem, entry);
				}else
				{
					updateentry = frequency.get(subitem);
					frequency.remove(subitem);
					updateentry.Frequency++;
					frequency.put(subitem, updateentry);
				}
			}
		}
		int freqcount = 0;
		int minbound = this.ListLimit;
		String[] keyarray = null;
		while (frequency.keySet().size() > this.ListLimit)
		{
			keyarray = frequency.keySet().toArray(new String[frequency.keySet().size()]);
			for (String keyitem : keyarray)
			{
				if(frequency.keySet().size() <= minbound)
				break;
				if(frequency.get(keyitem).Frequency == freqcount)
					frequency.remove(keyitem);
			}
			freqcount++;
		}
		keyarray = frequency.keySet().toArray(new String[frequency.keySet().size()]);
		Vector<String> TopItems = new Vector<String>();
		for(String keyitem : keyarray)
		{
			TopItems.add(keyitem);
		}
		return TopItems;
	}
}
