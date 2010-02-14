package documentProcessor;

import java.util.Vector;

public class WordWorkingSets
{
	private String wholedoc;
	private Vector<String> uniqueworkingSet = new Vector<String>();

	public WordWorkingSets(String wholedoc)
	{
		this.wholedoc = wholedoc;
		this.uniqueworkingSet.addElement("the");
		this.BuildUniqueWordWorkingSet();
	}
	
	private void BuildUniqueWordWorkingSet()
	{
		String[] activeSet = this.wholedoc.split(" ");
		for (int i = 0; i < activeSet.length; i++)
		{
			if(!this.uniqueworkingSet.contains(activeSet[i]))
			{
				this.uniqueworkingSet.addElement(activeSet[i]);
			}
		}
	}
	
	public void PrintUniqueWordStats()
	{
		System.out.println("Out of the original "+this.wholedoc.split(" ").length+" words only "+this.uniqueworkingSet.size()+ " are unique words");
	}
	
}
