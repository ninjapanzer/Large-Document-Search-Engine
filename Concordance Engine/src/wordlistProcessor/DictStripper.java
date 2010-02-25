package wordlistProcessor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class DictStripper {
	
	public static void main(String[] args)
	{
		try{
			File filedirectory = new File("..\\Concordance\\Datafiles\\dictionary source");
			for(String index : filedirectory.list())
			{
				//File file = null;
				if(index.contains("index"))
				{
					File file = new File("..\\Concordance\\Datafiles\\dictionary source\\"+index);
					System.out.println("..\\Concordance\\Datafiles\\dictionary processed\\"+index.split("\\.")[1].toString()+".pnz");
					FileWriter fstream = new FileWriter("..\\Concordance\\Datafiles\\dictionary processed\\"+index.split("\\.")[1].toString()+".pnz");
					BufferedWriter out = new BufferedWriter(fstream);
					Scanner scan = new Scanner(file);
					String[] nounlist;
					scan.useDelimiter("\\n");
					while(scan.hasNext())
					{
						nounlist = scan.next().split(" ");
						if(!nounlist[0].isEmpty())
						{
							if(!nounlist[0].contains("_"))
							{
								out.write(nounlist[0] + "\n");
							}
						}
					}
					scan.close();
					out.close();
				}
			}
		}catch (Exception e)
		{
		      System.err.println("Error: " + e.getMessage());
	    }
	}

}
