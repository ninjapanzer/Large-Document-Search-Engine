package documentProcessor;

import java.io.File;
import java.util.Vector;

import structs.Document;
import structs.Paragraphs;
import structs.Sentences;
import structs.Words;
import wordlistProcessor.ThesaurusHandler;

import org.apache.log4j.Logger;

public class ExtractDocumentData
{
	private Logger loggerDocument = Logger.getLogger("documentProcessor.ExtractDocumentData.Document");
	private Logger loggerParagraph = Logger.getLogger("documentProcessor.ExtractDocumentData.Paragraph");
	private Logger loggerSentence = Logger.getLogger("documentProcessor.ExtractDocumentData.Sentence");
	private Document FinalDocument = new Document();
	private String WholeDocument;
	private NounManagement NounList = new NounManagement(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"noun.pnz");
	
	public ExtractDocumentData(){}
	
	public ExtractDocumentData(String ProcessedDocument)
	{
		this.WholeDocument = ProcessedDocument;
		this.BreakOnDocument();
	}
	
	public Document getFinalDocument(){return this.FinalDocument;}
	
	public void setWholeDocument(String formattedfiledata){this.WholeDocument = formattedfiledata;}
	
	private void BreakOnDocument()
	{
		ThesaurusHandler Thesaurus = new ThesaurusHandler();
		if(this.WholeDocument != null || this.WholeDocument != "")
		{
			String[] ParagraphSplit = this.WholeDocument.split("\\t");
			String[] SentenceSplit;
			String[] WordSplit;
			Vector<String> SentenceLOWords = new Vector<String>();
			Vector<String> ParagraphLOWords = new Vector<String>();
			Vector<String> DocumentLOWords = new Vector<String>();
			for(int i = 0; i < ParagraphSplit.length; i++)
			{
				final Paragraphs para = new Paragraphs();
				this.FinalDocument.Block.add(para);
				SentenceSplit = ParagraphSplit[i].split("\\.");
				for(int j = 0; j < SentenceSplit.length; j++)
				{
					final Sentences sent = new Sentences();
					this.FinalDocument.Block.elementAt(i).Paragraph.add(sent);
					WordSplit = SentenceSplit[j].split(" ");
					for(int k = 0; k < WordSplit.length; k++)
					{
						Words word = new Words();
						if (NounList.isNoun(WordSplit[k]))
						{
							word.WordType = "noun";
							System.out.println("Thesaurus failed at: "+WordSplit[k]);
							try{
								word.LOWords.addAll(Thesaurus.CorrelateThesaurusItems(WordSplit[k]));
								}
								catch (Exception e) {
									loggerSentence.trace(word.Word+ " LOWord null");
									e.printStackTrace();
									//word.LOWords = null;
								}
						}else
						{
							word.WordType = "non Noun";
							word.LOWords = new Vector<String>();
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
						//System.out.println(word.Word + "\t" + i+ "|"+j+"|"+k + "\t" + "****"+this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).Word.toString()+"****"+ " " + this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).WordType.toString());
					}
					sent.topLOWords = Thesaurus.ReduceSynonyms(SentenceLOWords);
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
			}
			this.FinalDocument.topLOWords = Thesaurus.ReduceSynonyms(DocumentLOWords);
			for(String lovelytest : this.FinalDocument.topLOWords)
			{
				loggerDocument.trace(lovelytest);
			}
			DocumentLOWords.clear();
		}
	}
}
