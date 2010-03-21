package comparison;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import app.App;

import structs.Document;
import structs.SelectionItems;

public class CompareDocuments {
	private ArrayList<SelectionItems> Selection = null;
	private Logger logger = Logger.getLogger(comparison.CompareDocuments.class);
	private Vector<App> AnaylzedDocuments = null;
	private ArrayList<Document> Comparables = null;
	public CompareDocuments(ArrayList<SelectionItems> Selection, Vector<App> AnaylzedDocuments) {
		this.Selection = Selection;
		this.AnaylzedDocuments = AnaylzedDocuments;
		this.extractComparisonList();
		this.compareDocuments();
	}
	private void compareDocuments() {
		for(Document item : this.Comparables) {
			for(int i = 0; i<item.Block.size(); i++) {
				for(int j = 0; j<item.Block.elementAt(i).topLOWords.size(); j++) {
					item.Block.elementAt(i).topLOWords.elementAt(j);
				}
			}
		}
	}
	private void extractComparisonList() {
		this.Comparables = new ArrayList<Document>();
		for(int i = 0; i<AnaylzedDocuments.size(); i++) {
			for(int j = 0; j<Selection.size(); j++) {
				if(AnaylzedDocuments.elementAt(i).getThreadID() == Selection.get(j).ID) {
					logger.trace("File Added: "+Selection.get(j).Filename+" ID: "+Selection.get(j).ID);
					this.Comparables.add(AnaylzedDocuments.elementAt(i).getProcessedDocument());
				}
			}
		}
	}
}
