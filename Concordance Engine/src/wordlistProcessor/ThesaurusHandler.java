package wordlistProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ThesaurusHandler
{
	private HashMap<String, ArrayList<String>> Thesarus = new HashMap<String, ArrayList<String>>();
	public ThesaurusHandler()
	{
			File file = new File("..\\Concordance\\Datafiles\\mthesaur.txt");
			//FileWriter fstream = new FileWriter("..\\Concordance\\Datafiles\\noun.pnz");
			//BufferedWriter out = new BufferedWriter(fstream);
			Scanner scan;
			try {
				scan = new Scanner(file);
			
			String[] thesarusstr;
			scan.useDelimiter("\\r");
			ArrayList<String> syn = new ArrayList<String>();
			while(scan.hasNext())
			{
				thesarusstr = scan.next().split(",");
				for(int i = 1; i < thesarusstr.length; i++)
				{
					try{
						syn.add(thesarusstr[i]);
					}catch (Exception e)
					{
					      System.err.println("out of Memory at index " + i);
				    }
				}
				System.out.println(thesarusstr[0].replaceAll("\n", ""));
				Thesarus.put(thesarusstr[0].replaceAll("\n", ""), syn);
				syn.clear();
			}
			scan.close();
			//out.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		ArrayList<String> syn = this.Thesarus.get("aboriginal");
		for (String i : syn)
		{
			System.out.println(i);
		}
	}
}
