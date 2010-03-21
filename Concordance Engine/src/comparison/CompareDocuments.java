package comparison;

import java.util.ArrayList;
import java.util.HashMap;
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
		System.out.println(this.Comparables.size());
	}
	private void compareDocuments() {
		Vector<Vector<String>> thing = new Vector<Vector<String>>();
		for(Document item : this.Comparables) {
			thing.addElement(this.retrieveDocumentLOWords(item));
		}
		int count = 0;
		HashMap<String, Object> test = new HashMap<String, Object>();
		test.put("", null);
		for(Vector<String> item : thing){
			for(String subitem : item){
				System.out.println(subitem);
				if(!test.containsKey(subitem)){
					test.put(subitem, null);
					System.out.println(subitem);
					count++;
				}
			}
		}
		System.out.println((count/test.size()-1)*100+"%");
	}
	private Vector<String> retrieveDocumentLOWords(Document FullDocument){
		return FullDocument.topLOWords;
	}
	private Vector<String> retrieveParagraphLOWords(Document FullDocument, int ParagraphNumber){
		return FullDocument.Block.elementAt(ParagraphNumber).topLOWords;
	}
	private Vector<String> retrieveSentenceLOWords(Document FullDocument, int ParagraphNumber, int SentenceNumber){
		return FullDocument.Block.elementAt(ParagraphNumber).Paragraph.elementAt(SentenceNumber).topLOWords;
	}
	private Vector<String> retrieveWordLOWords(Document FullDocument, int ParagraphNumber, int SentenceNumber, int WordNumber){
		return FullDocument.Block.elementAt(ParagraphNumber).Paragraph.elementAt(SentenceNumber).Sentence.elementAt(WordNumber).LOWords;
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
