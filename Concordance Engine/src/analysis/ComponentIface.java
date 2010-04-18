package analysis;

import java.util.ArrayList;

import structs.Comparables;
import structs.Document;

public interface ComponentIface {
	ArrayList<Document> TestList = null;
	String[] config = null;
	public void init(ArrayList<Comparables> comparables, String[] configVals) throws Exception;
	public double runAnalysis();
}
