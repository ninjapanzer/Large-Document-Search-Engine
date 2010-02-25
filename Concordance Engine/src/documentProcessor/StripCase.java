package documentProcessor;

import java.io.File;
import java.util.Scanner;

public class StripCase
{
	private String WholeDocument ="";
	private boolean NormalizeWhitespaceFlag = false;
	
	public StripCase(){}
	
	public String GetWholeDocument(){return this.WholeDocument;}
	
	public void FlattenString()
	{
		this.WholeDocument = this.WholeDocument.toLowerCase();
	}
	
	public void NormalizeWhitespace(boolean flag)
	{
		this.NormalizeWhitespaceFlag = flag;
	}
	
	public void ReadFile(String filesource)
	{
		try{
			File file = new File(filesource);
			Scanner scan = new Scanner(file);
			String wholedoc;
			scan.useDelimiter("[.]");
			char[] whitespacetemp;
			while(scan.hasNext())
			{
				wholedoc = scan.next();
				wholedoc = wholedoc.replaceAll("[\\?\\*\\(\\)\\_\\[\\]\\{\\}\\^\\=\\|]", "");
				whitespacetemp = wholedoc.toCharArray();
				if(this.NormalizeWhitespaceFlag)
				{
					wholedoc = "";
					boolean spaceflag = false;
					boolean paragraphflag = false;
					try{
						if(whitespacetemp.length > 0)
						{
							if (whitespacetemp[0] == '\n' || whitespacetemp[0] == '\r')
							{
								paragraphflag = true;
							}
						}
					}catch (Exception e) {
						//System.err.println(whitespacetemp[0]);
						e.fillInStackTrace();
				    	System.err.println("Error: " + e.getMessage()+" "+e.getCause()+" in file StripCase.java");
				    	e.printStackTrace();
					}
					for (int i = 0; i < whitespacetemp.length; i++)
					{
						if (Character.isWhitespace(whitespacetemp[i]) && !spaceflag)
						{
							wholedoc += " ";
			                spaceflag = true;
						}else if (!Character.isWhitespace(whitespacetemp[i]) )
						{
							wholedoc += whitespacetemp[i];
							spaceflag = false;
						}
					}
					if (paragraphflag)
					{
						wholedoc = '\t' + wholedoc;
					}
				}
				
				wholedoc += ".";
				//whitespacetemp = wholedoc.toCharArray();
				this.WholeDocument += wholedoc;
			}
			scan.close();
		    }catch (Exception e){//Catch exception if any
		    	e.fillInStackTrace();
		    	System.err.println("Error: " + e.getMessage()+" "+e.getCause()+" in file StripCase.java");
		    	e.printStackTrace();
		    }
	}
	
}
