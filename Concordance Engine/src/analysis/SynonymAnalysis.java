package analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import structs.Comparables;
import structs.Comparisons;
import structs.Document;
import structs.Paragraphs;
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
		logger.trace("Document Comparison = "+String.format("%.2f",LOWordComparison(thing)) +"%");
		Vector<Vector<String>> ParaLOWords = this.gatherParagraphsLOWords();
		logger.trace("Paragraph Comparison = "+String.format("%.2f",LOWordComparison(ParaLOWords))+"%");
		Vector<Vector<String>> SentLOWords = this.gatherSentencesLOWords();
		double sentenceovercharge = LOWordComparison(SentLOWords);
		logger.trace("Sentence Comparison = "+String.format("%.2f",sentenceovercharge) + "%");
		if(sentenceovercharge > 100){
			logger.trace("!!!Inner Words Repeated!!!");
			logger.trace(String.format("%.2f",sentenceovercharge - 100)+"% of the words repeated in other Sentences");
		}
		ArrayList<Vector<String>> Doc1LOWords = new ArrayList<Vector<String>>();
		ArrayList<Vector<String>> Doc2LOWords = new ArrayList<Vector<String>>();
		for(Paragraphs item : this.TestDocuments.get(0).NestedDocument.Block){
			Doc1LOWords.add(item.topLOWords);
		}
		for(Paragraphs item : this.TestDocuments.get(1).NestedDocument.Block){
			Doc2LOWords.add(item.topLOWords);
		}
		this.FlatComparison(Doc1LOWords, Doc2LOWords);
		Doc1LOWords = null;
		Doc2LOWords = null;
		
		double QualityScore = 0;
		double TotalSize = 0;
		for(Comparisons Item : this.DocumentComparison){
			if(Item.CmpQuality > 0){
				TotalSize++;
				logger.trace("The Quality for Comparison of Paragraphs "+(int)Item.ItemNumberDoc1+" to "+(int)Item.ItemNumberDoc2+" is "+Item.CmpQuality*100+"%");
				QualityScore += Item.CmpQuality;
			}
		}
		logger.trace( ((double)(QualityScore)/(double)(TotalSize))*100 );
		return QualityScore/TotalSize;
	}
	
	private Comparisons CmpLOWords(Collection<String> LOWords1, Collection<String> LOWords2){
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
	private double FlatComparison(Collection<Vector<String>> Doc1, Collection<Vector<String>> Doc2){
		ArrayList<Vector<String>> Doc1LOWords = (ArrayList<Vector<String>>) Doc1;
		ArrayList<Vector<String>> Doc2LOWords = (ArrayList<Vector<String>>) Doc2;
		logger.trace("Paragraph Flat Comparison");
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
		return 0;
	}
	private double LOWordComparison(Collection<Vector<String>> LOWords){
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
		//double closeto50 = ((double)hashcount/((double)count/2))*50;
		double closeto50 = (double)hashcount/(double)count;
		double hashdev = 1 - ((double)hashcount/(double)((double)count/2));
		return ((closeto50 + hashdev)*100)*2;
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
		int itemcount = 0;
		Vector<Vector<String>> SentenceLOWords = new Vector<Vector<String>>();
		for(Comparables item : this.TestDocuments) {
			for(Paragraphs pitem : item.NestedDocument.Block){
				System.out.println("Paragraph Sentence Count "+pitem.Paragraph.size());
				for(Sentences sitem : pitem.Paragraph){
					System.out.println("LOWord Count "+sitem.topLOWords.size());
					SentenceLOWords.addElement(retrieveSentenceLOWords(sitem));
					itemcount++;
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
	@SuppressWarnings("unused")
	private Vector<String> retrieveWordLOWords(Document FullDocument, int ParagraphNumber, int SentenceNumber, int WordNumber){
		return FullDocument.Block.elementAt(ParagraphNumber).Paragraph.elementAt(SentenceNumber).Sentence.elementAt(WordNumber).LOWords;
	}
	
}
