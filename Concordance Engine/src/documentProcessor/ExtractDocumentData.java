package documentProcessor;

import java.io.File;
import java.util.Vector;

import structs.Document;
import structs.Paragraphs;
import structs.Sentences;
import structs.Words;
import wordlistProcessor.ThesaurusHandler;

import org.apache.log4j.Logger;

import comparison.WordListTools;

public class ExtractDocumentData
{
	private Logger loggerDocument = Logger.getLogger("documentProcessor.ExtractDocumentData.Document");
	private Logger loggerParagraph = Logger.getLogger("documentProcessor.ExtractDocumentData.Paragraph");
	private Logger loggerSentence = Logger.getLogger("documentProcessor.ExtractDocumentData.Sentence");
	private Document FinalDocument = new Document();
	private String WholeDocument;
	private WordManagement NounList = new WordManagement(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"noun.pnz");
	private WordManagement ConnectivesList = new WordManagement(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"connectives.pnz");
	private WordManagement AdverbList = new WordManagement(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"adv.pnz");
	private WordManagement AdjectiveList = new WordManagement(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"adj.pnz");
	private WordManagement VerbList = new WordManagement(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"verb.pnz");
	private double DocSize;
	private double CurrentWord;
	
	public ExtractDocumentData(){}
	
	public ExtractDocumentData(String ProcessedDocument, double DocSize)
	{
		this.WholeDocument = ProcessedDocument;
		this.DocSize = DocSize;
		this.CurrentWord= 0;
		this.BreakOnDocument();
		
	}
	public void setDocSize(double Size){
		this.DocSize = Size;
	}
	
	public Document getFinalDocument(){return this.FinalDocument;}
	
	public void setWholeDocument(String formattedfiledata){this.WholeDocument = formattedfiledata;}
	
	private void BreakOnDocument()
	{
		int TFail = 0;
		ThesaurusHandler Thesaurus = new ThesaurusHandler();
		if(this.WholeDocument != null || this.WholeDocument != "")
		{
			String[] ParagraphSplit = this.WholeDocument.split("\\t");
			String[] SentenceSplit;
			String[] WordSplit;
			Vector<String> DocTopKeywords = new Vector<String>();
			Vector<String> SentenceLOWords = new Vector<String>();
			Vector<String> ParagraphLOWords = new Vector<String>();
			Vector<String> DocumentLOWords = new Vector<String>();
			for(int i = 0; i < ParagraphSplit.length; i++)
			{
				final Paragraphs para = new Paragraphs();
				this.FinalDocument.Block.add(para);
				Vector<String> ParaTopKeywords = new Vector<String>();
				SentenceSplit = ParagraphSplit[i].split("\\.");
				for(int j = 0; j < SentenceSplit.length; j++)
				{
					final Sentences sent = new Sentences();
					this.FinalDocument.Block.elementAt(i).Paragraph.add(sent);
					Vector<String> KeywordList = new Vector<String>();
					WordSplit = SentenceSplit[j].split(" ");
					for(int k = 0; k < WordSplit.length; k++)
					{
						boolean hasID = false;
						Words word = new Words();
						if (NounList.isInList(WordSplit[k]) && hasID == false){
							word.WordType = "Noun";
							try{
								word.LOWords.addAll(Thesaurus.CorrelateThesaurusItems(WordSplit[k]));
								}
								catch (Exception e) {
									//System.out.println("Thesaurus failed at: "+WordSplit[k]);
									TFail++;
									loggerSentence.trace(word.Word+ " LOWord null");
									//e.printStackTrace();
									//word.LOWords = null;
								}
								hasID = true;
						}else if (ConnectivesList.isInList(WordSplit[k]) && hasID == false){
							//System.out.println(k+" a Connective "+WordSplit[k]);
							word.WordType = "Connective";
							hasID = true;
						}else if(AdverbList.isInList(WordSplit[k]) && hasID == false){
							//System.out.println(k+" a Adverb "+WordSplit[k]);
							word.WordType = "Adverb";
							hasID = true;
						}else if(AdjectiveList.isInList(WordSplit[k]) && hasID == false){
							//System.out.println(k+" a Adjective "+WordSplit[k]);
							word.WordType = "Adjective";
							hasID = true;
						}else if(VerbList.isInList(WordSplit[k]) && hasID == false){
							//System.out.println(k+" a Verb "+WordSplit[k]);
							word.WordType = "Verb";
							hasID = true;
						}else if(hasID == false){
							//System.out.println(k+" a Keyword "+WordSplit[k]);
							word.WordType = "Keyword";
							KeywordList.add(WordSplit[k]);
							word.LOWords = new Vector<String>();
							word.isKeyword = true;
						}
						word.Word = WordSplit[k];
						word.length = WordSplit[k].length();
						this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.addElement(word);
						//System.out.println(word.LOWords.size());
						try{SentenceLOWords.addAll(word.LOWords);
						}catch (Exception e) {
							System.out.println("failed at word "+ word.Word);
							e.printStackTrace();
						}
						CurrentWord++;
						//System.out.println(word.Word + "\t" + i+ "|"+j+"|"+k + "\t" + "****"+this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).Word.toString()+"****"+ " " + this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).WordType.toString());
					}
					sent.topLOWords = Thesaurus.ReduceSynonyms(SentenceLOWords);
					sent.topKeywords = (Vector<String>) WordListTools.TopItems(KeywordList, 5);
					ParaTopKeywords.addAll(sent.topKeywords);
					loggerSentence.trace(" ");
					loggerSentence.trace("Sentence "+j+" Paragraph "+i);
					loggerSentence.trace(" ");
					for (String sentwords : SentenceLOWords)
					{
						loggerSentence.trace(sentwords);
					}
					loggerSentence.trace(" ");
					try{ParagraphLOWords.addAll(sent.topLOWords);
					}catch (Exception e){}
					SentenceLOWords.clear();
				}
				para.topLOWords = Thesaurus.ReduceSynonyms(ParagraphLOWords);
				para.topKeywords = (Vector<String>) WordListTools.TopItems(ParaTopKeywords, 10);
				DocTopKeywords.addAll(para.topKeywords);
				loggerParagraph.trace(" ");
				loggerParagraph.trace("Paragraph "+i);
				loggerParagraph.trace(" ");
				for (String parawords : ParagraphLOWords)
				{
					loggerParagraph.trace(parawords);
				}
				loggerParagraph.trace(" ");
				try{DocumentLOWords.addAll(para.topLOWords);
				}catch (Exception e){}
				ParagraphLOWords.clear();
				System.out.println(((this.CurrentWord/this.DocSize)*100)+"% Completed");
			}
			this.FinalDocument.topLOWords = Thesaurus.ReduceSynonyms(DocumentLOWords);
			this.FinalDocument.topKeywords = (Vector<String>) WordListTools.TopItems(DocTopKeywords, 10);
			loggerDocument.trace("Top LOWords for the Document");
			for(String lovelytest : this.FinalDocument.topLOWords)
			{
				loggerDocument.trace(lovelytest);
			}
			loggerDocument.trace("Top Keywords for the Document");
			for(String lovelytest : this.FinalDocument.topKeywords)
			{
				loggerDocument.trace(lovelytest);
			}
			DocumentLOWords.clear();
		}
		System.out.println("Thesaurus Failed on "+TFail+" Words");
	}
}
