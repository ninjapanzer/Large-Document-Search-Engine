package analysis;

import java.util.ArrayList;
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
		Comparables document1 = this.TestList.get(0);
		Comparables document2 = this.TestList.get(1);
		logger.trace("Analysis Completed with Value "+ doStuff(document1,document2).performFilter());
		logger.trace("Analysis Completed with Value "+ doStuff(document2,document1).performFilter());

		return 0;
		
	}

}
