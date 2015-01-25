package org.adligo.fabricate_tests.models.dependencies;

import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_ModelsDepsPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_ModelsDepsPkgTrials me = new A_ModelsDepsPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.add(I_IdeTrial.class);
    trials.add(IdeMutantTrial.class);
    trials.add(IdeTrial.class);
    
    trials.add(I_DependencyTrial.class);
    trials.add(DependencyMutantTrial.class);
    trials.add(DependencyTrial.class);
    
    trials.add(I_ProjectDependencyTrial.class);
    trials.add(ProjectDependencyMutantTrial.class);
    trials.add(ProjectDependencyTrial.class);
    
    trials.add(I_LibraryDependencyTrial.class);
    trials.add(LibraryDependencyMutantTrial.class);
    trials.add(LibraryDependencyTrial.class);
    return trials;
  }


}
