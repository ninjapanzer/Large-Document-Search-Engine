package analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import app.App;

import structs.Comparables;
import structs.Comparisons;
import structs.Document;
import structs.Paragraphs;
import structs.SelectionItems;
import structs.Sentences;

public class SynonymAnalysis implements ComponentIface{
	private Logger logger = Logger.getLogger("analysis.AnalysisLoader.CompareDocumentThread");
	private ArrayList<Comparables> TestDocuments = null;
	private ArrayList<Comparisons> DocumentComparison = null;
	@Override
	public void init(ArrayList<Comparables> comparables, String[] configVals)
			throws Exception {
		this.TestDocuments = comparables;
		
		//this.Selection = election;
		//this.AnaylzedDocuments = AnaylzedDocuments;
		this.DocumentComparison = new ArrayList<Comparisons>();
		//this.extractComparisonList();
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double runAnalysis() {
		Vector<Vector<String>> thing = new Vector<Vector<String>>();
		for(Comparables item : this.TestDocuments){
			thing.addElement(this.retrieveDocumentLOWords(item.NestedDocument));
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
		for(Paragraphs item : this.TestDocuments.get(0).NestedDocument.Block){
			Doc1LOWords.add(item.topLOWords);
		}
		for(Paragraphs item : this.TestDocuments.get(1).NestedDocument.Block){
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
		double QualityScore = 0;
		double TotalSize = this.DocumentComparison.size();
		for(Comparisons Item : this.DocumentComparison){
			System.out.println("The Quality for Comparison of Paragraphs "+(int)Item.ItemNumberDoc1+" to "+(int)Item.ItemNumberDoc2+" is "+Item.CmpQuality*100+"%");
			QualityScore += Item.CmpQuality;
		}
		System.out.println(QualityScore/(TotalSize*10));
		return QualityScore/TotalSize;
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
		if(hashcount == 0){
			cmp.CmpQuality = 0;
		}else{
		cmp.CmpQuality = (double)count/(double)hashcount;
		}
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
					hashcount++;
				}
				count++;
			}
			outercount++;
		}
		double closeto50 = ((double)hashcount/((double)count/2))*50;
		return (closeto50/50)*100;
	}
	private Vector<Vector<String>> gatherParagraphsLOWords(){
		Vector<Vector<String>> ParagraphLOWords = new Vector<Vector<String>>();
		for(Comparables item : this.TestDocuments){
			
			for(Paragraphs pitem : item.NestedDocument.Block){
				ParagraphLOWords.addElement(retrieveParagraphLOWords(pitem));
			}
		}
		return ParagraphLOWords;
	}
	private Vector<Vector<String>> gatherSentencesLOWords(){
		Vector<Vector<String>> SentenceLOWords = new Vector<Vector<String>>();
		for(Comparables item : this.TestDocuments) {
			for(Paragraphs pitem : item.NestedDocument.Block){
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
	
}
