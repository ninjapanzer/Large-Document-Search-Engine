// This is really rough...
package com.codingcatholic.math.probability.bayesian;

public abstract class BayesianFilter { 
	protected BayesianStorageEngine _storageEngine;
	
	public BayesianFilter(BayesianStorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}
	
	public abstract double performFilter();
}
