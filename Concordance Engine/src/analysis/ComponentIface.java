package analysis;

import java.util.ArrayList;

import structs.Document;

public interface ComponentIface {
	ArrayList<Document> TestList = null;
	String[] config = null;
	public void init(ArrayList<Document> Comparables, String[] configVals) throws Exception;
	public double runAnalysis();
}
