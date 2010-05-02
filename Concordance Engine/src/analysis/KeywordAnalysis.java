package analysis;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import structs.Comparables;
import structs.Comparisons;
import structs.Document;

public class KeywordAnalysis implements ComponentIface {
	private Logger logger = Logger.getLogger("analysis.AnalysisLoader.CompareDocumentThread");
	private ArrayList<Comparables> TestDocuments = null;
	private ArrayList<Comparisons> DocumentComparison = null;
	@Override
	public void init(ArrayList<Comparables> comparables, String[] configVals)
			throws Exception {
		this.TestDocuments = comparables;
		this.DocumentComparison = new ArrayList<Comparisons>();
	}

	@Override
	public double runAnalysis() {
		// TODO Auto-generated method stub
		return 0;
	}
}
