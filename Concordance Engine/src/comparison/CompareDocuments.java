package comparison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import app.App;

import structs.*;

public class CompareDocuments {
	private ArrayList<SelectionItems> Selection = null;
	private Logger logger = Logger.getLogger(comparison.CompareDocuments.class);
	private Vector<App> AnaylzedDocuments = null;
	private ArrayList<Document> Comparables = null;
	private ArrayList<Comparisons> DocumentComparison = null;
	public CompareDocuments(ArrayList<SelectionItems> Selection, Vector<App> AnaylzedDocuments) {
		this.Selection = Selection;
		this.AnaylzedDocuments = AnaylzedDocuments;
		this.DocumentComparison = new ArrayList<Comparisons>();
		this.extractComparisonList();
		this.compareDocuments();
		logger.trace(this.Comparables.size());
	}
	private void compareDocuments() {
		Vector<Vector<String>> thing = new Vector<Vector<String>>();
		for(Document item : this.Comparables) {
			thing.addElement(this.retrieveDocumentLOWords(item));
		}
		System.out.print("Document Comparison = ");
		System.out.format("%.2f",LOWordComparison(thing));
		System.out.println("%");
		Vector<Vector<String>> ParaLOWords = this.gatherParagraphsLOWords();
		System.out.print("Paragraph Comparison = ");
		System.out.format("%.2f",LOWordComparison(ParaLOWords));
		System.out.println("%");
		Vector<Vector<String>> SentLOWords = this.gatherSentencesLOWords();
		System.out.print("Sentence Comparison = ");
		System.out.format("%.2f",LOWordComparison(SentLOWords));
		System.out.println("%");
		ArrayList<Vector<String>> Doc1LOWords = new ArrayList<Vector<String>>();
		ArrayList<Vector<String>> Doc2LOWords = new ArrayList<Vector<String>>();
		for(Paragraphs item : this.Comparables.get(0).Block){
			Doc1LOWords.add(item.topLOWords);
		}
		for(Paragraphs item : this.Comparables.get(1).Block){
			Doc2LOWords.add(item.topLOWords);
		}
		System.out.println("Paragraph Flat Comparison");
		for(int i = 0; i<Doc1LOWords.size(); i++){
			for(int j = 0; j<Doc2LOWords.size(); j++){
				Comparisons cmp = new Comparisons();
				cmp = this.CmpLOWords(Doc1LOWords.get(i), Doc2LOWords.get(j));
				cmp.CmpType = "Paragraph";
				cmp.ItemNumberDoc1 = i;
				cmp.ItemNumberDoc2 = j;
				cmp.CmpQuality = 1-cmp.CmpQuality;
				this.DocumentComparison.add(cmp);
			}
		}
		for(Comparisons Item : this.DocumentComparison){
			System.out.println("The Quality for Comparison of Paragraphs "+(int)Item.ItemNumberDoc1+" to "+(int)Item.ItemNumberDoc2+" is "+Item.CmpQuality*100+"%");
		}
		//DocumentComparison.add(this.CmpLOWords(thing.elementAt(0), thing.elementAt(1)));
		//System.out.println(100-(allowance.CmpQuality*100));
		/*int count = 0;
		int hashcount = 0;
		HashMap<String, Object> test = new HashMap<String, Object>();
		test.put("", null);
		for(Vector<String> item : thing){
			for(String subitem : item){
				//System.out.println(subitem);
				if(!test.containsKey(subitem)){
					test.put(subitem, null);
					System.out.println(subitem);
					hashcount++;
				}
				count++;
			}
		}
		//double performance = (count/test.size()-1)*100;
		//performance = performance;
		//System.out.println(hashcount);
		//System.out.println(count);
		double closeto50 = (1-((double)(hashcount)/((double)count)))*100;
		//System.out.println(closeto50);
		System.out.print("Accuracy = ");
		System.out.format("%.2f",(closeto50/50)*100);
		System.out.println("%");*/
	}
	
	private Comparisons CmpLOWords(Vector<String> LOWords1, Vector<String> LOWords2){
		int count = 0;
		int hashcount;
		Comparisons cmp = new Comparisons();
		HashMap<String, Object> test = new HashMap<String, Object>();
		test.put("", null);
		for(String item : LOWords1){
			test.put(item, null);
		}
		hashcount = test.size() -1;
		for(String item : LOWords2){
			if(!test.containsKey(item)){
				count++;
			}
		}
		//count += hashcount;
		//System.out.println(count);
		//System.out.println(hashcount);
		cmp.CmpQuality = (double)count/(double)hashcount;
		//System.out.println(100-(cmp.CmpQuality*100));
		return cmp;
	}
	private double LOWordComparison(Vector<Vector<String>> LOWords){
		int count = 0,outercount=0;
		int hashcount = 0;
		HashMap<String, Object> test = new HashMap<String, Object>();
		test.put("", null);
		for(Vector<String> item : LOWords){
			for(String subitem : item){
				if(!test.containsKey(subitem)){
					test.put(subitem, null);
					//System.out.println(subitem);
					hashcount++;
				}
				count++;
			}
			outercount++;
		}
		double closeto50 = ((double)hashcount/((double)count/2))*50;
		/*System.out.println("hashcount "+hashcount);
		System.out.println("outercount "+outercount);
		System.out.println("Loop Count "+count);
		System.out.println("Loop Count reduced "+(double)count/2);
		System.out.println("Ratio "+closeto50);
		System.out.println("Percentage of 50 "+(closeto50/50)*100);*/
		return (closeto50/50)*100;
	}
	private Vector<Vector<String>> gatherParagraphsLOWords(){
		Vector<Vector<String>> ParagraphLOWords = new Vector<Vector<String>>();
		for(Document item : this.Comparables) {
			for(Paragraphs pitem : item.Block){
				ParagraphLOWords.addElement(retrieveParagraphLOWords(pitem));
			}
		}
		return ParagraphLOWords;
	}
	private Vector<Vector<String>> gatherSentencesLOWords(){
		Vector<Vector<String>> SentenceLOWords = new Vector<Vector<String>>();
		for(Document item : this.Comparables) {
			for(Paragraphs pitem : item.Block){
				for(Sentences sitem : pitem.Paragraph){
					SentenceLOWords.addElement(retrieveSentenceLOWords(sitem));
				}
			}
		}
		return SentenceLOWords;
	}
	private Vector<String> retrieveDocumentLOWords(Document FullDocument){
		return FullDocument.topLOWords;
	}
	private Vector<String> retrieveParagraphLOWords(Paragraphs Paragraph){
		return Paragraph.topLOWords;
	}
	private Vector<String> retrieveSentenceLOWords(Sentences Sentence){
		return Sentence.topLOWords;
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
