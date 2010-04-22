package bayesian;

import java.util.Collection;
import java.util.HashMap;

public interface BayesianStorageEngine {	
	public Collection<String> getVocabulary();
	public Collection<String> getDocument();
	public Collection<String> getDeterminedVocabulary();
	
	public HashMap<String,Object> getUniqueDeterminedVocabulary();
	public HashMap<String,Object> getUniqueDocument();
	public HashMap<String,Object> getUniqueVocabulary();
}
