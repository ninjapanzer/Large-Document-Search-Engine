package documentProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public final class NounManagement
{
	//private Vector<String> NounListVect = new Vector<String>();
	private HashMap<String, Object> NounList = new HashMap<String, Object>();
	private File file = null;
	public NounManagement(String filename)
	{
		LoadNounList(filename);
	}
	
	private void LoadNounList(String filename)
	{
		
		try{
			if (file == null)
			{
				file = new File(filename);
			}
		Scanner scan = new Scanner(file);
		//System.out.println("\\"+System.getProperty("line.separator"));
		scan.useDelimiter("\n");
		while(scan.hasNext())
		{
			NounList.put(scan.next().replaceAll(" ", ""), null);
		}
		}catch (Exception e)
		{
		      System.err.println("Error: " + e.getMessage());
	    }
	}
	
	public boolean isNoun(String noun)
	{
		if(this.NounList.containsKey(noun))
		{
			return true;
		}
		return false;	
		
	}
}
