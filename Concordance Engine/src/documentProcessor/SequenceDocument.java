package documentProcessor;

import structs.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SequenceDocument
{
	private Vector<ReconstructDocument> SequencedDocument = new Vector<ReconstructDocument>();
	public Vector<ReconstructDocument> getSequencedDocument(){return this.SequencedDocument;}
	public SequenceDocument(Document wholedocument)
	{
		//System.out.println(wholedocument.Block.get(0).Paragraph.get(0).Sentence.get(0).Word);
		for(int i = 0; i< wholedocument.Block.size(); i++)
		{
			//Block.get(2).Paragraph.get(2).Sentence.get(2).Word
			int size = wholedocument.Block.size();
			//size = wholedocument.Block.get(i).Paragraph.g
			for(int j = 0; j < wholedocument.Block.elementAt(i).Paragraph.size(); j++)
			{
				size = size = wholedocument.Block.size();
				size = size;
				for(int k = 0; k < wholedocument.Block.get(i).Paragraph.get(j).Sentence.size(); k++)
				{
					ReconstructDocument Temp = new ReconstructDocument();
					Temp.wordcontent = wholedocument.Block.elementAt(i).Paragraph.elementAt(j).Sentence.elementAt(k).Word;
					Temp.wordID = k;
					Temp.SentenceID = j;
					Temp.paragraphID = i;
					SequencedDocument.addElement(Temp);
					System.out.println(Temp.wordcontent+" "+Temp.paragraphID+"|"+Temp.SentenceID+"|"+Temp.wordID);
				}
			}
		}
	}
}
