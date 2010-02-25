package wordlistProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import structs.WordFrequency;

public class ThesaurusHandler
{
	private HashMap<String, ArrayList<String>> Thesarus = new HashMap<String, ArrayList<String>>();
	private int LOWordSize = 50;
	public ThesaurusHandler()
	{
			File file = new File("..\\Concordance\\Datafiles\\mthesaur.txt");
			Scanner scan;
			try {
				scan = new Scanner(file);
			
			String[] thesarusstr;
			scan.useDelimiter("\\r");
			while(scan.hasNext())
			{
				thesarusstr = scan.next().split(",");
				ArrayList<String> syn = new ArrayList<String>();
				for(int i = 1; i < thesarusstr.length; i++)
				{
					try{
						syn.add(thesarusstr[i]);
					}catch (Exception e)
					{
					      System.err.println("out of Memory at index " + i);
				    }
				}
				Thesarus.put(thesarusstr[0].replaceAll("\n", ""), syn);
			}
			scan.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	private ArrayList<String> ThesaurusLookup(String key)
	{
		ArrayList<String> ThesaurusItems = new ArrayList<String>();
		if(this.Thesarus.containsKey(key))
		{
			ThesaurusItems = this.Thesarus.get(key);
		}else
		{
			ThesaurusItems = null;
		}
		return ThesaurusItems;
	}
	public Vector<String> ReduceSynonyms(Vector<String> SubItems)
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
		int minbound = this.LOWordSize;
		String[] keyarray = null;
		while (frequency.keySet().size() > this.LOWordSize)
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
	public Vector<String> CorrelateThesaurusItems(String key)
	{
		WordFrequency updateentry = new WordFrequency();
		HashMap<String,WordFrequency> frequency = new HashMap<String,WordFrequency>(); 
		ArrayList<String> Words = new ArrayList<String>();
		ArrayList<String> ThesaurusItems = ThesaurusLookup(key);
		if(ThesaurusItems != null)
		{
		for(String item : ThesaurusItems)
		{
			ArrayList<String> SubItems = ThesaurusLookup(item);
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
		}
		//if(frequency.containsKey(key))
			//frequency.remove(key);
		String[] keyarray = null;
		int freqcount = 0;
		int minbound = this.LOWordSize;
		while (frequency.keySet().size() > this.LOWordSize)
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
		return null;
	}
}
