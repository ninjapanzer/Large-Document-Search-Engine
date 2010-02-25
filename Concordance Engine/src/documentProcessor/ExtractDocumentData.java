package documentProcessor;

import java.util.Vector;

import structs.Document;
import structs.Paragraphs;
import structs.Sentences;
import structs.Words;
import wordlistProcessor.ThesaurusHandler;

public class ExtractDocumentData
{
	private Document FinalDocument = new Document();
	private String WholeDocument;
	private NounManagement NounList = new NounManagement("..\\Concordance\\Datafiles\\noun.pnz");
	
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
							try{
								word.LOWords.addAll(Thesaurus.CorrelateThesaurusItems(WordSplit[k]));
								}
								catch (Exception e) {
									word.LOWords = null;
								}
						}else
						{
							word.WordType = "non Noun";
							word.LOWords = null;
						}
						
						word.Word = WordSplit[k];
						word.length = WordSplit[k].length();
						this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.addElement(word);
						try{SentenceLOWords.addAll(word.LOWords);
						}catch (Exception e) {
							// TODO: handle exception
						}
						System.out.println(word.Word + " " + i+ "|"+j+"|"+k + " " + this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).Word.toString()+ " " + this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).WordType.toString());
					}
					sent.topLOWords = Thesaurus.ReduceSynonyms(SentenceLOWords);
					try{ParagraphLOWords.addAll(sent.topLOWords);
					}catch (Exception e){}
					SentenceLOWords.clear();
				}
				para.topLOWords = Thesaurus.ReduceSynonyms(ParagraphLOWords);
				try{DocumentLOWords.addAll(para.topLOWords);
				}catch (Exception e){}
				ParagraphLOWords.clear();
			}
			this.FinalDocument.topLOWords = Thesaurus.ReduceSynonyms(DocumentLOWords);
			for(String lovelytest : this.FinalDocument.topLOWords)
			{
				System.out.println(lovelytest);
			}
			DocumentLOWords.clear();
		}
	}
}
