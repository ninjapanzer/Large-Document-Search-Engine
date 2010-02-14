package documentProcessor;

import structs.Document;
import structs.Paragraphs;
import structs.Sentences;
import structs.Words;

public class ExtractDocumentData
{
	private Document FinalDocument = new Document();
	private Paragraphs Paragraph = new Paragraphs();
	private Sentences Sentence = new Sentences();
	private Words Word = new Words();
	private String WholeDocument;
	private NounManagement NounList = new NounManagement();
	
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
		if(this.WholeDocument != null || this.WholeDocument != "")
		{
			String[] ParagraphSplit = this.WholeDocument.split("\\t");
			String[] SentenceSplit;
			String[] WordSplit;
			for(int i = 0; i < ParagraphSplit.length; i++)
			{
				SentenceSplit = ParagraphSplit[i].split("\\.");
				for(int j = 0; j < SentenceSplit.length; j++)
				{
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
						this.Word.Word = WordSplit[k];
						this.Word.length = WordSplit[k].length();
						this.Sentence.Sentence.addElement(this.Word);
					}
					System.out.println("Sentence "+j);
					this.Sentence.length = WordSplit.length;
					this.Paragraph.Paragraph.addElement(this.Sentence);
					this.Sentence.Sentence.clear();
				}
				System.out.println("Paragraph "+i);
				this.Paragraph.length = SentenceSplit.length;
				this.FinalDocument.Block.addElement(this.Paragraph);
				this.Paragraph.Paragraph.clear();
			}
			//System.out.println(this.FinalDocument.Block.get(2).Paragraph.get(2).Sentence.get(2).Word);
			//System.out.println(NounList.isNoun("0"));
		}
	}
}
