package wordlistProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class ThesaurusHandler
{
	private HashMap<String, Vector<String>> Thesarus = new HashMap<String, Vector<String>>();
	public ThesaurusHandler()
	{
			File file = new File("..\\Concordance\\Datafiles\\mthesaur.txt");
			Scanner scan;
			try {
				scan = new Scanner(file);
			
			String[] thesarusstr;
			scan.useDelimiter("\\r");
			Vector<String> syn = new Vector<String>();
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
				try{
					Vector<String> tempvect = syn;
					Thesarus.put(thesarusstr[0].replaceAll("\n", ""), syn);
					syn.clear();
				}catch (Exception e)
				{
				      System.err.println("out of Memory at " + thesarusstr[0].replaceAll("\n", ""));
			    }
			}
			scan.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			if (this.Thesarus.containsKey("zoom")){
				System.out.println("zoom exists");
				System.out.println(this.Thesarus.toString());
			}
		Vector<String> syn = this.Thesarus.get("zoom");
		String item = syn.toString();
			System.out.println(item);
	}
}
