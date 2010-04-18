// This is really rough...
package com.codingcatholic.math.probability.bayesian;

import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayesianFilter extends BayesianFilter {

	public NaiveBayesianFilter(BayesianStorageEngine storageEngine) {
		super(storageEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	/* Very Naive Implementation of:
	// P(A|B) = P(B|A) * P(B) / P(A)
	// 	or
	//	P(DeterminedVocabulary | Document) = P(Document|DeterminedVocabulary) * P(Document) / P(DeterminedVocabulary)
	 */
	public double performFilter() {
		HashMap<String,Object> uniqueDocument = _storageEngine.getUniqueDocument();
		HashMap<String,Object> uniqueVocabulary = _storageEngine.getUniqueVocabulary();
		HashMap<String,Object> uniqueDeterminedVocabulary = _storageEngine.getUniqueDeterminedVocabulary();
		
		int docInDetermined = 0;
		int docInVocabulary = 0;
		int detInVocabulary = 0;
		
		for(String str : uniqueDocument.keySet()) {
			if(uniqueDeterminedVocabulary.containsKey(str)) {
				docInDetermined++;
			}
			if(uniqueVocabulary.containsKey(str)) {
				docInVocabulary++;
			}
		}
		
		for(String str : uniqueDeterminedVocabulary.keySet()) {
			if(uniqueVocabulary.containsKey(str)) {
				detInVocabulary++;
			}
		}
		
		return (((double)docInDetermined) / uniqueDeterminedVocabulary.size()) * (((double) docInVocabulary) / uniqueVocabulary.size()) / (((double) detInVocabulary) / uniqueVocabulary.size() );
	}
	
	public static void main(String[] args) {
		SimpleBayesianListStorageEngine engine = new SimpleBayesianListStorageEngine();
		
		class StupidClass implements StringContainer {
			private String _stuff = null;
			
			public StupidClass(String str) {
				_stuff = str;
			}
			
			public String getStringContent() {
				return _stuff;
			}
			
		}
		
		String[] determined = {"word","verbiage","loquacity","in","document","here","yay","this","place","was","was","was","great","lovely","hateable","hatred","there","Dasein"};
		String[] document = {"Dasein","word","in","document","here","yay","this","place","was","great","lovely","hateable","hatred","there"};
		
		ArrayList<StringContainer> detColl = new ArrayList<StringContainer>();
		ArrayList<StringContainer> docColl = new ArrayList<StringContainer>();
		
		for(String str : determined) {
			detColl.add(new StupidClass(str));
		}
		
		for(String str : document) {
			docColl.add(new StupidClass(str));
		}
		
		engine.addItemsToDeterminedVocabulary(detColl);  // Words in doc2 and synonyms
		engine.addItemsToDocument(docColl); // Words in doc1
		
		engine.addItemsToVocabulary(detColl); // All words
		engine.addItemsToVocabulary(docColl); 	// Being created here
		
		NaiveBayesianFilter filter = new NaiveBayesianFilter(engine);
		System.out.println(filter.performFilter());
	}
}
