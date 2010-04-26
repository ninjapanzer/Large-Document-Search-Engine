package structs;

import java.util.Vector;
import bayesian.StringContainer;

public class ReconstructDocument implements StringContainer 
{
	public String wordcontent;
	public int paragraphID;
	public int SentenceID;
	public int wordID;
	public int wordLength;
	public boolean isKeyword;
	public String WordType;
	public Vector<String> LOWords = new Vector<String>();
	
	@Override
	public String getStringContent() {
		return wordcontent;
	}
	
}
