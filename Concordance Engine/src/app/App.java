package app;

import documentProcessor.*;

public class App
{
	private static StripCase file = new StripCase();
	public static void main(String[] args)
	{
		file.NormalizeWhitespace(true);
		file.ReadFile("..\\Concordance\\Datafiles\\8ethc10.txt");
		file.FlattenString();
		ExtractDocumentData StripDocument = new ExtractDocumentData(file.GetWholeDocument());
		//System.out.println(file.GetWholeDocument());
		WordWorkingSet EvaluateSets = new WordWorkingSet(file.GetWholeDocument());
		EvaluateSets.PrintUniqueWordStats();
	}

}
