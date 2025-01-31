package analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import comparison.WordListTools;

import structs.Comparables;
import structs.PhraseAnaysisResults;
import structs.ReconstructDocument;

public class PhraseAnalysis implements ComponentIface {
	ArrayList<Comparables> TestList = null;
	String[] config = null;
	ArrayList<PhraseAnaysisResults> TestResults = null;
	Logger logger = Logger.getLogger("analysis.AnalysisLoader.CompareDocumentThread");
	
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
			result.ID = item.ID;
			result.Filename = item.Filename;
			result.BiWords = this.generateBiWords(item.SequencedDocument);
			//result.BiWords = this.generateNounBiWords(item.SequencedDocument);
			result.TriWords = this.generateTriWords(item.SequencedDocument);
			this.TestResults.add(result);
		}
		ArrayList<String> ConfBiWords = this.compareBiWords();
		ArrayList<String> ConfTriWords = this.compareTriWords();
		
		double finalscore = 0;
		for(int i = 0; i< this.TestResults.size()-1; i++){
			Collection<String> topBiWords = WordListTools.TopItems(ConfBiWords, 10);
			logger.trace("------------Top BiWords");
			for(String item : topBiWords){
				logger.trace(item);
			}
			logger.trace("------------Score");
			double biwordscore = (double)topBiWords.size()/(double)ConfBiWords.size();
			logger.trace(biwordscore);
			Collection<String> topTriWords = WordListTools.TopItems(ConfTriWords, 10);
			logger.trace("------------Top TriWords");
			for(String item : topTriWords){
				logger.trace(item);
			}
			logger.trace("------------Score");
			double triwordscore = (double)topTriWords.size()/(double)ConfTriWords.size();
			logger.trace(triwordscore);
			finalscore += (biwordscore+triwordscore)/(double)2;
		}
		//Calculate the Common Vs Total phrases
		for(int i = 0; i< this.TestResults.size()-1; i++){
			logger.trace("Analysis for "+this.TestResults.get(i).Filename +" vs "+this.TestResults.get(i+1).Filename+":");
			double totalBiWordPhrases = this.TestResults.get(i).BiWords.size() + this.TestResults.get(i+1).BiWords.size();
			double totalTriWordPhrases = this.TestResults.get(i).TriWords.size() + this.TestResults.get(i).TriWords.size();
			logger.trace("BiWord Quality "+(double)((ConfBiWords.size()/totalBiWordPhrases)*100));
			logger.trace("TriWord Quality "+(double)((ConfTriWords.size()/totalTriWordPhrases)*100));
		}
		
		return finalscore/(double)this.TestResults.size();
		
	}
	private ArrayList<String> compareTriWords(){
		ArrayList<String> ConfirmedTriWords = new ArrayList<String>();
		for(int i = 0; i<this.TestResults.size()-1;i++){
			HashMap<String, Object> testmap = new HashMap<String, Object>();
			for(String TriWordItem : this.TestResults.get(i).TriWords){
				testmap.put(TriWordItem, null);
			}
			for(String TriWordItem : this.TestResults.get(i+1).TriWords){
				if(testmap.containsKey(TriWordItem)){
					//System.out.println("Confirmed "+ BiWordItem);
					ConfirmedTriWords.add(TriWordItem);
				}
			}
			logger.trace("Document "+this.TestResults.get(i).Filename +" vs "+ this.TestResults.get(i+1).Filename +"\n");
		}
		return ConfirmedTriWords;
	}
	private ArrayList<String> compareBiWords(){
		ArrayList<String> ConfirmedBiWords = new ArrayList<String>();
		for(int i = 0; i<this.TestResults.size()-1;i++){
			HashMap<String, Object> testmap = new HashMap<String, Object>();
			for(String BiWordItem : this.TestResults.get(i).BiWords){
				testmap.put(BiWordItem, null);
			}
			for(String BiWordItem : this.TestResults.get(i+1).BiWords){
				if(testmap.containsKey(BiWordItem)){
					//System.out.println("Confirmed "+ BiWordItem);
					ConfirmedBiWords.add(BiWordItem);
				}
			}
			logger.trace("Document "+this.TestResults.get(i).Filename +" vs "+ this.TestResults.get(i+1).Filename +"\n");
		}
		return ConfirmedBiWords;
	}
	
	private ArrayList<String> generateBiWords(ArrayList<ReconstructDocument> Source){
		long count = Source.size();
		ArrayList<String> biwords = new ArrayList<String>();
		for(long i = 0; i<count-1; i++){
			try{
				String biword = Source.get((int)i).wordcontent+Source.get((int)(i+1)).wordcontent;
			biwords.add(biword.replaceAll(".*?(\\.).*?(\\s+)", ""));
			}catch(Exception e){
				System.out.println(Source.get((int)i).wordcontent+" or "+Source.get((int)(i+1)).wordcontent+" Not a biWord Skipping");
			}
		}
		return biwords;
	}
	//testing noun identification
	@SuppressWarnings("unused")
	private ArrayList<String> generateNounBiWords(ArrayList<ReconstructDocument> Source){
		long count = Source.size();
		ArrayList<String> biwords = new ArrayList<String>();
		for(long i = 0; i<count-1; i++){
			try{
				if(Source.get((int)i).WordType == "Noun"){
					biwords.add(Source.get((int)i).wordcontent+Source.get((int)(i+1)).wordcontent);
				}
			}catch(Exception e){
				System.out.println(Source.get((int)i).wordcontent+" or "+Source.get((int)(i+1)).wordcontent+" Not a biWord Skipping");
			}
		}
		return biwords;
	}
	private ArrayList<String> generateTriWords(ArrayList<ReconstructDocument> Source){
		long count = Source.size();
		ArrayList<String> triwords = new ArrayList<String>();
		for(long i = 0; i<count-2; i++){
			try{
				String triword = Source.get((int)i).wordcontent+Source.get((int)(i+1)).wordcontent+Source.get((int)(i+2)).wordcontent;
				triwords.add(triword.replaceAll(".*?(\\.).*?(\\s+)", ""));
			}catch(Exception e){
				System.out.println(Source.get((int)i).wordcontent+" | "+Source.get((int)(i+1)).wordcontent+" | "+Source.get((int)(i+2)).wordcontent+" Not a biWord Skipping");
			}
		}
		return triwords;
	}
}
