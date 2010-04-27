package analysis;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import bayesian.*;

import structs.Comparables;
import structs.PhraseAnaysisResults;
import structs.ReconstructDocument;

public class BayesianAnalysis implements ComponentIface {

	ArrayList<Comparables> TestList = null;
	String[] config = null;
	ArrayList<PhraseAnaysisResults> TestResults = null;
	private static Logger logger = Logger.getLogger("analysis.AnalysisLoader.CompareDocumentThread");
	
	@Override
	public void init(ArrayList<Comparables> comparables, String[] configVals)
			throws Exception {
		this.TestList = comparables;
		this.config = configVals;
		this.TestResults = new ArrayList<PhraseAnaysisResults>();
		// TODO Auto-generated method stub
		
	}

	private NaiveBayesianFilter doStuff(Comparables document1, Comparables document2) {
		SimpleBayesianListStorageEngine thing = new SimpleBayesianListStorageEngine();
		NaiveBayesianFilter filter = new NaiveBayesianFilter(thing);
		for(ReconstructDocument subitem : document1.SequencedDocument) {
			thing.addItemToVocabulary(subitem.wordcontent);
			thing.addItemToDocument(subitem.wordcontent);
		}
		for(ReconstructDocument subitem : document2.SequencedDocument) {
			thing.addItemToVocabulary(subitem.wordcontent);
			thing.addItemToDeterminedVocabulary(subitem.wordcontent);
			thing.addItemsToVocabulary(subitem.LOWords);
		}
		return filter;
	}
	@Override
	public double runAnalysis() {
		// TODO Auto-generated method stub
		Vector<String> AllLOWords = new Vector<String>();
		ArrayList<String> Document = new ArrayList<String>();
		ArrayList<ArrayList<String>> DividedDocument = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> DividedLOWords = new ArrayList<ArrayList<String>>();
		Comparables document1 = this.TestList.get(0);
		Comparables document2 = this.TestList.get(1);
		
		
		
		
		//thing.normalizeAllContents();
		/*
		
		for(Comparables item : this.TestList){
			ArrayList<String> DocumentObject = new ArrayList<String>();
			ArrayList<String> LOWordsObject = new ArrayList<String>();
			logger.trace("Extracting Elements from "+item.Filename);
			for(ReconstructDocument subitem : item.SequencedDocument){
				if(subitem.WordType == "Noun"){
				AllLOWords.addAll(subitem.LOWords);
				LOWordsObject.addAll(subitem.LOWords);
				}
				DocumentObject.add(subitem.wordcontent);
				Document.add(subitem.wordcontent);
			}
			logger.trace("Total LOWords "+LOWordsObject.size());
			logger.trace("Total Words "+DocumentObject.size());
			logger.trace("Completed "+item.Filename);
			DividedLOWords.add(LOWordsObject);
			DividedDocument.add(DocumentObject);
			
		}
		logger.trace("Total Vocabulary "+(long)(AllLOWords.size()+Document.size()));
		logger.trace("Element Extraction Complete Starting Analysis");
		
		thing.addItemsToVocabulary(Document);
		thing.addItemsToVocabulary(AllLOWords);
		thing.addItemsToDeterminedVocabulary(DividedDocument.get(1));
		thing.addItemsToDeterminedVocabulary(DividedLOWords.get(1));
		thing.addItemsToDocument(DividedDocument.get(0));
		*/
		//logger.trace("Total Vocabulary "+thing.getVocabulary().size());
		//logger.trace("Total Document Vocabulary "+thing.getDocument().size());
		//logger.trace("Total Determined Vocabulary "+thing.getDeterminedVocabulary().size());
		//logger.trace("Element Extraction Complete Starting Analysis");
		
	
		
//		logger.trace("Analysis Completed with Value "+ filter.performFilter());
		logger.trace("Analysis Completed with Value "+ doStuff(document1,document2).performFilter());
		logger.trace("Analysis Completed with Value "+ doStuff(document2,document1).performFilter());

		return 0;
		
	}

}
