package analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import analysis.ComponentIface;
import app.App;

import org.apache.log4j.Logger;

import structs.Comparables;
import structs.Comparisons;
import structs.SelectionItems;

import com.codingcatholic.util.CustomProperties;

public class AnalysisLoader {
	private ArrayList<SelectionItems> Selection = null;
	private Vector<App> AnaylzedDocuments = null;
	private ArrayList<Comparables> Comparables = null;
	private ArrayList<Comparisons> DocumentComparison = null;
	private static Logger logger = Logger.getLogger(AnalysisLoader.class);
	// The overall architecture does not take much optimization into account
	//		We can deal with that later
	
	// Also, some of this code is still messy
	
	public AnalysisLoader(ArrayList<SelectionItems> Selection, Vector<App> AnaylzedDocuments) throws Exception{
		this.Selection = Selection;
		this.AnaylzedDocuments = AnaylzedDocuments;
		this.DocumentComparison = new ArrayList<Comparisons>();
		this.extractComparisonList();
		String[] multiProps = {"analysis_item","analysis_item_config","analysis_item_weight"};
		CustomProperties props = new CustomProperties("analysis_config.properties");
		HashMap<String,ArrayList<String>> analysisItems = props.getMultiProperties(multiProps);
		Iterator<String> aIIter = analysisItems.get(multiProps[0]).iterator();
		Iterator<String> aICIter = analysisItems.get(multiProps[1]).iterator();
		Iterator<String> aIWIter = analysisItems.get(multiProps[2]).iterator();
		int totalWeight = 0;
		double successValue = 0.0;
		
		while(aIIter.hasNext()) {
			String analysisItem = aIIter.next();
			String analysisItemConfig = aICIter.next();
			logger.trace(analysisItem);
			ComponentIface comp =  (ComponentIface) Class.forName(analysisItem).newInstance();
			comp.init(this.Comparables, analysisItemConfig.split("\\|"));
			int weight = Integer.parseInt(aIWIter.next());
			totalWeight += weight;
			successValue += weight * comp.runAnalysis();
			System.out.println(successValue);
		}
	}
	private void extractComparisonList() {
		this.Comparables = new ArrayList<Comparables>();
		for(int i = 0; i<AnaylzedDocuments.size(); i++) {
			for(int j = 0; j<Selection.size(); j++) {
				if(AnaylzedDocuments.elementAt(i).getThreadID() == Selection.get(j).ID) {
					logger.trace("File Added: "+Selection.get(j).Filename+" ID: "+Selection.get(j).ID);
					Comparables cmpItem = new Comparables();
					cmpItem.Filename = Selection.get(j).Filename;
					cmpItem.ID = Selection.get(j).ID;
					cmpItem.NestedDocument = AnaylzedDocuments.elementAt(i).getProcessedDocument();
					cmpItem.SequencedDocument = AnaylzedDocuments.elementAt(i).getSequencedDocument();
					this.Comparables.add(cmpItem);
				}
			}
		}
	}
}
