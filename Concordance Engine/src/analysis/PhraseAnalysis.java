package analysis;

import java.util.ArrayList;

import structs.Document;

public class PhraseAnalysis implements ComponentIface {
	ArrayList<Document> TestList = null;
	String[] config = null;
	ArrayList<ArrayList<String>> TestResults = null;
	@Override
	public void init(ArrayList<Document> Comparables, String[] configVals)
			throws Exception {
		this.TestList = Comparables;
		this.config = configVals;
		this.TestResults = new ArrayList<ArrayList<String>>();
		// TODO Auto-generated method stub
	}
	@Override
	public double runAnalysis() {
		// TODO Auto-generated method stub
		for(Document item : this.TestList){
			TestResults.add(this.findwordPairs(item));
		}
		return 0;
	}
	private ArrayList<String> findwordPairs(Document Source){
		System.out.println("your mom");
		return null;
	}
}
