package wordlistProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ThesarusHandler
{
	private HashMap<String, ArrayList<String>> Thesarus = new HashMap<String, ArrayList<String>>();
	public ThesarusHandler()
	{
		try{
			File file = new File("..\\Concordance\\Datafiles\\mthesaur.txt");
			//FileWriter fstream = new FileWriter("..\\Concordance\\Datafiles\\noun.pnz");
			//BufferedWriter out = new BufferedWriter(fstream);
			Scanner scan = new Scanner(file);
			String[] thesarusstr;
			scan.useDelimiter("\\r");
			while(scan.hasNext())
			{
				thesarusstr = scan.next().split(",");
				ArrayList<String> syn = new ArrayList<String>();
				for(int i = 1; i < thesarusstr.length; i++)
				{
					syn.add(thesarusstr[i]);
				}
				Thesarus.put(thesarusstr[0], syn);
				syn.clear();
			}
			scan.close();
			//out.close();
		}catch (Exception e)
		{
		      System.err.println("Error: " + e.getMessage());
	    }
		ArrayList<String> syn = this.Thesarus.get("aboriginal");
		for (String i : syn)
		{
			System.out.println(i);
		}
	}
}
