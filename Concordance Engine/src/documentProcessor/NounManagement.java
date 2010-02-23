package documentProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class NounManagement
{
	private Vector<String> NounListVect = new Vector<String>();
	private HashMap<String, Object> NounList = new HashMap<String, Object>();
	
	public NounManagement(String filename)
	{
		this.LoadNounList(filename);
	}
	
	private void LoadNounList(String filename)
	{
		
		try{
		File file = new File(filename);
		Scanner scan = new Scanner(file);
		//String[] nounlist;
		scan.useDelimiter("\\n");
		while(scan.hasNext())
		{
			this.NounListVect.addElement(scan.next());
			NounList.put(scan.next(), null);
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
