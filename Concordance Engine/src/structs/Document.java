package structs;

import java.util.Vector;

public class Document
{
	public Vector<Paragraphs> Block = new Vector<Paragraphs> ();
	public Vector<String> topLOWords = new Vector<String> ();
	public Vector<String> topKeywords = new Vector<String> ();
	public Document(){}
}
