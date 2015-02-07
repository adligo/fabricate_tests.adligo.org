package org.adligo.fabricate_tests.repository;

import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_RepositoryPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_RepositoryPkgTrials me = new A_RepositoryPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.add(I_DependenciesManagerTrial.class);
    trials.add(I_DependencyManagerTrial.class);
    trials.add(I_LibraryResolverTrial.class);
    trials.add(I_RepositoryFactoryTrial.class);
    trials.add(I_RepositoryPathBuilderTrial.class);
    
    trials.add(DefaultRepositoryPathBuilderTrial.class);
    trials.add(DependencyManagerTrial.class);
    trials.add(LibraryResolverTrial.class);
    return trials;
  }


}
