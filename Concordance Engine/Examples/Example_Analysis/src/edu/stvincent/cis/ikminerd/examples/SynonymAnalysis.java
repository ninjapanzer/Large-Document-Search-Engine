package edu.stvincent.cis.ikminerd.examples;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class SynonymAnalysis implements DocumentAnalysisComponent {
	private static Logger logger = Logger.getLogger(SynonymAnalysis.class);
	public void init(ArrayList<String> l1, ArrayList<String> l2,
			String[] configVals) throws Exception {
		// TODO Auto-generated method stub
		logger.trace("Synonym Analysis Config Loaded");
	}

	public double runAnalysis() {
		// TODO Auto-generated method stub
		logger.trace("Doing Synonym Analysis");
		return 0;
	}

}
