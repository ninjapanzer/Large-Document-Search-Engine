package app;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Vector;

import structs.ReconstructDocument;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;

public class DisplayGui
{
	private Vector<ReconstructDocument> SequencedDocument;
	public DisplayGui(Vector<ReconstructDocument> SequencedDocument)
	{
		this.SequencedDocument = SequencedDocument;
		PrepareGui();
	}
	private void PrepareGui()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("JTextArea Test");
	    frame.setLayout(new FlowLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    String text = "";
	    JTextArea textAreal = new JTextArea(text, 50, 50);
	    Highlighter h = textAreal.getHighlighter();
	    h.removeAllHighlights();
	    String thing = "\t";
	    for(int p = 0; p < this.SequencedDocument.size(); p++)
	    {
	    	 thing += (this.SequencedDocument.elementAt(p).wordcontent +" ");
	    	 //System.out.println(p);
	    }
	    textAreal.append(thing);
	    if (textAreal.getText().contains("Mom"))
	    {
	    	@SuppressWarnings("unused")
			String mom = textAreal.getText();
	    try {
			h.addHighlight(1, 2, DefaultHighlighter.DefaultPainter);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	    textAreal.setPreferredSize(new Dimension(50, 50));
	    //JTextArea textArea2 = new JTextArea(text, 5, 10);
	    //textArea2.setPreferredSize(new Dimension(100, 100));
	    JScrollPane scrollPane = new JScrollPane(textAreal,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    textAreal.setLineWrap(true);
	    //textArea2.setLineWrap(true);
	    //frame.add(textAreal);
	    frame.add(scrollPane);
	    frame.pack();
	    frame.setVisible(true);
	}
}
