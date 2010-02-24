package wordlistProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class ThesaurusHandler
{
	private HashMap<String, ArrayList<String>> Thesarus = new HashMap<String, ArrayList<String>>();
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
			
		}
		return ThesaurusItems;
	}
}
