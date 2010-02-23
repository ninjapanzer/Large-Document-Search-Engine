package documentProcessor;

import structs.Document;
import structs.Paragraphs;
import structs.Sentences;
import structs.Words;

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
		int wo = 0;
		if(this.WholeDocument != null || this.WholeDocument != "")
		{
			String[] ParagraphSplit = this.WholeDocument.split("\\t");
			String[] SentenceSplit;
			String[] WordSplit;
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
						}else
						{
							word.WordType = "non Noun";
						}
						word.Word = WordSplit[k];
						word.length = WordSplit[k].length();
						this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.addElement(word);
						System.out.println(word.Word + " " + i+ "|"+j+"|"+k + " " + this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).Word.toString()+ " " + this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).WordType.toString());
					}
				}
			}
		}
	}
}
