package analysis;

import java.util.ArrayList;

public interface ComponentIface {
	public void init(ArrayList<String> l1, ArrayList<String> l2, String[] configVals) throws Exception;
	public double runAnalysis();
}
