package structs;

import java.util.Vector;

public class Sentences
{
	public Vector<Words> Sentence = new Vector<Words> ();
	public Vector<String> topLOWords = new Vector<String> ();
	public Vector<String> topKeywords = new Vector<String> ();
	public int length;
	public boolean contextSentence;
}
