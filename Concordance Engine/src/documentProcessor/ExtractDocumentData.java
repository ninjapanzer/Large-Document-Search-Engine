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
						/*if (NounList.isNoun(WordSplit[k]))
						{
							this.Word.WordType = "noun";
						}else
						{
							this.Word.WordType = "non Noun";
						}*/
						//this.Word.Word = WordSplit[k];
						//this.Word.length = WordSplit[k].length();
						Words word = new Words();
						word.Word = WordSplit[k];
						String yourface = WordSplit[k];
						word.length = WordSplit[k].length();
						this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.addElement(word);
						System.out.println(word.Word + " " + i+ "|"+j+"|"+k + " " + this.FinalDocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).Word.toString());
						//this.Sentence.Sentence.addElement(this.Word);
						//System.out.println(wo++);
					}
				}
			}
			System.out.println(this.FinalDocument.Block.get(0).Paragraph.get(0).Sentence.get(0).Word);
			//System.out.println(this.FinalDocument.Block.get(0).Paragraph.get(2).Sentence.get(2).Word);
			//System.out.println(NounList.isNoun("0"));
		}
	}
}
