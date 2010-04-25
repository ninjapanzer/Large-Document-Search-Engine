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
	private static Logger logger = Logger.getLogger(BayesianAnalysis.class);
	
	@Override
	public void init(ArrayList<Comparables> comparables, String[] configVals)
			throws Exception {
		this.TestList = comparables;
		this.config = configVals;
		this.TestResults = new ArrayList<PhraseAnaysisResults>();
		// TODO Auto-generated method stub
		
	}

	@Override
	public double runAnalysis() {
		// TODO Auto-generated method stub
		Vector<String> AllLOWords = new Vector<String>();
		ArrayList<String> Document = new ArrayList<String>();
		ArrayList<ArrayList<String>> DividedDocument = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> DividedLOWords = new ArrayList<ArrayList<String>>();
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
		SimpleBayesianListStorageEngine thing = new SimpleBayesianListStorageEngine();
		
		thing.addItemsToVocabulary(Document);
		thing.addItemsToVocabulary(AllLOWords);
		thing.addItemsToDeterminedVocabulary(DividedDocument.get(1));
		thing.addItemsToDeterminedVocabulary(DividedLOWords.get(1));
		thing.addItemsToDocument(DividedDocument.get(0));
		NaiveBayesianFilter filter = new NaiveBayesianFilter(thing);
		logger.trace("Analysis Completed with Value "+ filter.performFilter());
		return 0;
	}

}
