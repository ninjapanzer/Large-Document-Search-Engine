package analysis;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

import structs.Comparables;
import structs.Document;
import structs.PhraseAnaysisResults;
import structs.ReconstructDocument;

public class PhraseAnalysis implements ComponentIface {
	ArrayList<Comparables> TestList = null;
	String[] config = null;
	ArrayList<PhraseAnaysisResults> TestResults = null;
	
	@Override
	public void init(ArrayList<Comparables> comparables, String[] configVals)
			throws Exception {
		this.TestList = comparables;
		this.config = configVals;
		this.TestResults = new ArrayList<PhraseAnaysisResults>();
	}
	@Override
	
	public double runAnalysis() {
		// TODO Auto-generated method stub
		for(Comparables item : this.TestList){
			PhraseAnaysisResults result = new PhraseAnaysisResults();
			//result.BiWords.addAll(this.generateBiWords(item.SequencedDocument));
			//result.TriWords.addAll(this.generateTriWords(item.SequencedDocument));
			this.TestResults.add(result);
		}
		return 0;
	}
	private ArrayList<String> generateBiWords(ArrayList<ReconstructDocument> Source){
		System.out.println("your mom");
		//while()
		return null;
	}
	private ArrayList<String> generateTriWords(ArrayList<ReconstructDocument> Source){
		
		return null;
	}
}
