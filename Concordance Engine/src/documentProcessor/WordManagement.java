package documentProcessor;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class WordManagement
{
	//private Vector<String> NounListVect = new Vector<String>();
	private HashMap<String, Object> WordList = new HashMap<String, Object>();
	private File file = null;
	public WordManagement(String filename)
	{
		LoadList(filename);
	}
	
	private void LoadList(String filename){
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
			WordList.put(scan.next().replaceAll(" ", ""), null);
		}
		}catch (Exception e)
		{
		      System.err.println("Error: " + e.getMessage());
	    }
	}
	
	public boolean isInList(String word)
	{
		if(this.WordList.containsKey(word))
		{
			return true;
		}
		return false;	
		
	}
}
