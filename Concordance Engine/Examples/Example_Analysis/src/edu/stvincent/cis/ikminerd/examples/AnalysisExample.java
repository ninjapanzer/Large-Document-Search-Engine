package edu.stvincent.cis.ikminerd.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.codingcatholic.util.CustomProperties;

public class AnalysisExample {
	private static Logger logger = Logger.getLogger(AnalysisExample.class);
	// The overall architecture does not take much optimization into account
	//		We can deal with that later
	
	// Also, some of this code is still messy
	public static void main(String[] args) throws Exception {
		String[] multiProps = {"analysis_item","analysis_item_config","analysis_item_weight"};
		CustomProperties props = new CustomProperties("analysis_config.properties");
		HashMap<String,ArrayList<String>> analysisItems = props.getMultiProperties(multiProps);
		Iterator<String> aIIter = analysisItems.get(multiProps[0]).iterator();
		Iterator<String> aICIter = analysisItems.get(multiProps[1]).iterator();
		Iterator<String> aIWIter = analysisItems.get(multiProps[2]).iterator();
		int totalWeight = 0;
		double successValue = 0.0;
		
		ArrayList<String> wordList1 = new ArrayList<String>();
		ArrayList<String> wordList2 = new ArrayList<String>();
		
		while(aIIter.hasNext()) {
			String analysisItem = aIIter.next();
			String analysisItemConfig = aICIter.next();
			DocumentAnalysisComponent comp =  (DocumentAnalysisComponent) Class.forName(analysisItem).newInstance();
			comp.init(wordList1, wordList2, analysisItemConfig.split("\\|") );
			int weight = Integer.parseInt(aIWIter.next());
			totalWeight += weight;
			successValue += weight * comp.runAnalysis();
		}
		// finalValue = successValue / totalWeight;
	}
}
