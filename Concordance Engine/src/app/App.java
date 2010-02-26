package app;

import java.util.Vector;

import structs.Document;
import structs.ReconstructDocument;
import documentProcessor.*;

public class App
{
	private static WordWorkingSets EvaluateSets;
	private static StripCase file = new StripCase();
	@SuppressWarnings("unused")
	private static Vector<ReconstructDocument> SequencedDocument;
	private static Document ProcessedDocument = null;
	public static Document getProcessedDocument(){return ProcessedDocument;}
	//public static void main(String[] args) throws InterruptedException{}
	public App(){}
	public App(String Filename)
	{
		ProcessFile(Filename);
	}
	
	public static void ProcessFile(String Filename)
	{
		file.NormalizeWhitespace(true);
		file.ReadFile(Filename);
		file.FlattenString();
		ExtractDocumentData StripDocument = new ExtractDocumentData(file.GetWholeDocument());
		//System.out.println(file.GetWholeDocument());
		ProcessedDocument = StripDocument.getFinalDocument();
		//SequenceDocument Sequence = new SequenceDocument(StripDocument.getFinalDocument());
		//SequencedDocument = Sequence.getSequencedDocument();
		//DisplayGui display = new DisplayGui(SequencedDocument);
		EvaluateSets = new WordWorkingSets(file.GetWholeDocument());
		//@SuppressWarnings("unused")
		//ThesaurusHandler thesarus = new ThesaurusHandler();
		//thesarus.CorrelateThesaurusItems("zoom");
		//WordWorkingSets EvaluateSets = new WordWorkingSets(file.GetWholeDocument());
		//WordWorkingSets EvaluateSets2 = new WordWorkingSets(file.GetWholeDocument());
		//EvaluateSets.PrintUniqueWordStats();
		//threadNounWorkinSet();
		//EvaluateSets.BuildNounWorkingSet();  //5:32
		EvaluateSets.PrintNounWorkingSet();
	}

	@SuppressWarnings("unused")
	private static void threadNounWorkinSet() throws InterruptedException
	{
		//2:30
		int midpoint = EvaluateSets.getUniqueSize() / 2; // old time 3:13
		int midpoint2 = midpoint / 2;
		int midpoint3 = midpoint + midpoint2;
		EvaluateSets.setThreadStartStop(0, midpoint2);
		Thread first = new Thread(EvaluateSets);
		Thread second = new Thread(EvaluateSets);
		Thread third = new Thread (EvaluateSets);
		Thread fourth = new Thread (EvaluateSets);
		first.start();
			Thread.sleep(1000);
		EvaluateSets.setThreadStartStop(midpoint2, midpoint);
		second.start();
			Thread.sleep(1000);
		EvaluateSets.setThreadStartStop(midpoint, midpoint3);
		third.start();
			Thread.sleep(1000);
		EvaluateSets.setThreadStartStop(midpoint3, EvaluateSets.getUniqueSize());
		fourth.start();
			first.join();
			second.join();
			third.join();
			fourth.join();
		}
		//EvaluateSets.BuildNounWorkingSet();  //5:32
	}
	
