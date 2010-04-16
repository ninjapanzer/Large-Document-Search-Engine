package edu.stvincent.cis.ikminerd.examples;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class PhraseAnalysis implements DocumentAnalysisComponent {
	private static Logger logger = Logger.getLogger(PhraseAnalysis.class);
	
	private int[] defaultCategoryWeights = {};
	private ArrayList<String> list1 = new ArrayList<String>();
	private ArrayList<String> list2 = new ArrayList<String>();
	
	public PhraseAnalysis() {
		
	}
	
	public void init(ArrayList<String> l1, ArrayList<String> l2,String[] configVals) throws Exception {
		list1 = l1;
		list2 = l2;
		if(configVals.length == 0) {
			defaultCategoryWeights = new int[1];
			defaultCategoryWeights[0] = 100;
		} else {
			// Really should add other checks...
			defaultCategoryWeights = new int[configVals.length];
			for(int i=0;i<configVals.length;i++) {
				defaultCategoryWeights[i] = Integer.parseInt(configVals[i]);
			}
		}
		logger.trace("Phrase Analysis Config Loaded: "+defaultCategoryWeights[0]);
	}
	
	
	public double runAnalysis() {
		logger.trace("Running Phrase Comparison Analysis");
		Iterator<String> iter1 = list1.iterator(), iter2 = list2.iterator();
		// Run through each list of words and ascertain the frequency at each level
		// By the end, you should have something like this:
		// Phrase Length 	|	Attempts	|	Successes	| 	Weight
		// 		1			|		15		|		5		|	20
		//		2			|		14		|		1		|	40
		// ...etc
		
		// Based on this, you will be able to calculate:
		// 	(Successes[0] / Attempts[0]) * 20 +
		//		(Successes[1] / Attempts[1]) * 40 +
		//		(Successes[2] / Attempts[2] * 120
		//		...
		// Then divide the total from that by the total of all the weights
		return 0.0;
	}
}
