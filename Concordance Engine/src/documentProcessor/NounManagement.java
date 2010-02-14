package documentProcessor;

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class NounManagement
{
	private Vector<String> NounList = new Vector<String>();
	
	public NounManagement()
	{
		this.LoadNounList();
	}
	
	private void LoadNounList()
	{
		try{
		File file = new File("..\\Concordance\\Datafiles\\noun.pnz");
		Scanner scan = new Scanner(file);
		String[] nounlist;
		scan.useDelimiter("\\n");
		while(scan.hasNext())
		{
			this.NounList.addElement(scan.next());
		}
		}catch (Exception e)
		{
		      System.err.println("Error: " + e.getMessage());
	    }
	}
	
	public boolean isNoun(String noun)
	{
		for(String testNoun : NounList)
		{
			if(testNoun.matches(noun))
			{
				return true;
			}
		}
		return false;
		
	}
}
