// This is very rough

package bayesian;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class SimpleBayesianListStorageEngine implements BayesianStorageEngine {
	private ArrayList<String> _vocabulary = new ArrayList<String>();
	private ArrayList<String> _document = new ArrayList<String>();
	private ArrayList<String> _determinedVocabulary = new ArrayList<String>();
	
	public ArrayList<String> getVocabulary() {
		return _vocabulary;
	}
	
	public void addItemsToVocabulary(Collection<String> strC) {
		for(String con : strC) {
			_vocabulary.add(con.toString());
		}
	}
	
	public ArrayList<String> getDocument() {
		return _document;
	}
	
	public void addItemsToDocument(Collection<String> strC) {
		for(String con : strC) {
			_document.add(con);
		}
	}

	public ArrayList<String> getDeterminedVocabulary() {
		return _determinedVocabulary;
	}
	
	public void addItemsToDeterminedVocabulary(Collection<String> document) {
		for(String con : document) {
			_determinedVocabulary.add(con);
		}
	}

	// Put into collections utils
	public HashMap<String,Object> getUniqueDeterminedVocabulary() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		for(String str : _determinedVocabulary) {
			map.put(str, new Object());
		}
		return map;
	}

	// Put logic into collections utils
	public HashMap<String,Object> getUniqueDocument() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		for(String str : _document) {
			map.put(str, new Object());
		}
		return map;
	}
	
	// Put logic into collections utils
	public HashMap<String,Object> getUniqueVocabulary() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		for(String str : _vocabulary) {
			map.put(str, new Object());
		}
		return map;
	}
}
