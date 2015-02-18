package org.adligo.fabricate_tests.routines;

import org.adligo.fabricate.repository.DependenciesNormalizer;
import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.fabricate_tests.routines.implicit.A_RoutinesImplicitPkgTrials;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_RoutinesPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_RoutinesPkgTrials me = new A_RoutinesPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.addAll(new A_RoutinesImplicitPkgTrials().getTrials());
    
    return trials;
  }


}
