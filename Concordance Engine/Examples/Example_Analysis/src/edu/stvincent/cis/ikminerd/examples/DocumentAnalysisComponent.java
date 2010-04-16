package edu.stvincent.cis.ikminerd.examples;

import java.util.ArrayList;

public interface DocumentAnalysisComponent {
	public void init(ArrayList<String> l1, ArrayList<String> l2, String[] configVals) throws Exception;
	public double runAnalysis();
}
