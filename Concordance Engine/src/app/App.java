package app;

import java.util.Vector;

import structs.Document;
import structs.ReconstructDocument;
import documentProcessor.*;

import org.apache.log4j.Logger;

public class App
{
	private boolean _done = false;
	private Logger logger = Logger.getLogger(App.class);  //  @jve:decl-index=0:
	private WordWorkingSets EvaluateSets;
	private StripCase file = new StripCase();
	@SuppressWarnings("unused")
	private static Vector<ReconstructDocument> SequencedDocument;
	private Document ProcessedDocument = null;
	private int threadID;
	public Document getProcessedDocument(){return this.ProcessedDocument;}
	//public static void main(String[] args) throws InterruptedException{}
	public App(){}
	public App(String Filename)
	{
		ProcessFile(Filename);
	}
	public boolean isDone() {
		return this._done;
	}
	public void setThreadID(int ID) {
		this.threadID = ID;
	}
	public int getThreadID() {
		return this.threadID;
	}
	public void setisDone(boolean _done) {this._done = _done;}
	public void ProcessFile(String Filename)
	{
		logger.debug("Thread Started");
		this.file.NormalizeWhitespace(true);
		this.file.ReadFile(Filename);
		this.file.FlattenString();
		ExtractDocumentData StripDocument = new ExtractDocumentData(this.file.GetWholeDocument());
		//System.out.println(file.GetWholeDocument());
		this.ProcessedDocument = StripDocument.getFinalDocument();
		//SequenceDocument Sequence = new SequenceDocument(StripDocument.getFinalDocument());
		//SequencedDocument = Sequence.getSequencedDocument();
		//DisplayGui display = new DisplayGui(SequencedDocument);
		this.EvaluateSets = new WordWorkingSets(this.file.GetWholeDocument());
		//@SuppressWarnings("unused")
		//ThesaurusHandler thesarus = new ThesaurusHandler();
		//thesarus.CorrelateThesaurusItems("zoom");
		//WordWorkingSets EvaluateSets = new WordWorkingSets(file.GetWholeDocument());
		//WordWorkingSets EvaluateSets2 = new WordWorkingSets(file.GetWholeDocument());
		//EvaluateSets.PrintUniqueWordStats();
		//threadNounWorkinSet();
		//EvaluateSets.BuildNounWorkingSet();  //5:32
		this.EvaluateSets.PrintNounWorkingSet();
		logger.debug("Thread Completed");
		this._done = true;
	}
	//legacy multi thread per file
	/*
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
		*/
	}
	
