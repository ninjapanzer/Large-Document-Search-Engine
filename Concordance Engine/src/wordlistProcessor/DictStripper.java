package wordlistProcessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictStripper {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try{
			File file = new File("..\\Concordance\\Datafiles\\index.noun");
			FileWriter fstream = new FileWriter("..\\Concordance\\Datafiles\\noun.pnz");
			BufferedWriter out = new BufferedWriter(fstream);
			Scanner scan = new Scanner(file);
			String[] nounlist;
			scan.useDelimiter("\\n");
			while(scan.hasNext())
			{
				nounlist = scan.next().split(" ");
				if(!nounlist[0].isEmpty())
				{
					if(!nounlist[0].contains(" "))
					nounlist[0] = nounlist[0].replaceAll("_", " ");
					out.write(nounlist[0] + "\n");
				}
			}
			scan.close();
			out.close();
		}catch (Exception e)
		{
		      System.err.println("Error: " + e.getMessage());
	    }
	}

}
