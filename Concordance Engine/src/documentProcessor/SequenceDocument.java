package documentProcessor;

import java.util.Vector;

import structs.*;

public class SequenceDocument
{
	private Vector<ReconstructDocument> SequencedDocument = new Vector<ReconstructDocument>();
	public Vector<ReconstructDocument> getSequencedDocument(){return this.SequencedDocument;}
	public SequenceDocument(Document wholedocument)
	{
		for(int i = 0; i< wholedocument.Block.size(); i++)
		{
			for(int j = 0; j < wholedocument.Block.elementAt(i).Paragraph.size(); j++)
			{
				for(int k = 0; k < wholedocument.Block.get(i).Paragraph.get(j).Sentence.size(); k++)
				{
					ReconstructDocument Temp = new ReconstructDocument();
					Temp.wordcontent = wholedocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).Word;
					Temp.wordID = k;
					Temp.SentenceID = j;
					Temp.paragraphID = i;
					Temp.wordLength = Temp.wordcontent.length();
					SequencedDocument.addElement(Temp);
					System.out.println(Temp.wordcontent+" "+Temp.paragraphID+"|"+Temp.SentenceID+"|"+Temp.wordID);
				}
				ReconstructDocument Temp = new ReconstructDocument();
				Temp.wordcontent = ".";
				Temp.wordID = -1;
				Temp.SentenceID = -1;
				Temp.paragraphID = -1;
				Temp.wordLength = Temp.wordcontent.length();
				SequencedDocument.addElement(Temp);
			}
			ReconstructDocument Temp = new ReconstructDocument();
			Temp.wordcontent = "\n\t";
			Temp.wordID = -2;
			Temp.SentenceID = -2;
			Temp.paragraphID = -2;
			Temp.wordLength = Temp.wordcontent.length();
			SequencedDocument.addElement(Temp);
		}
	}
}
