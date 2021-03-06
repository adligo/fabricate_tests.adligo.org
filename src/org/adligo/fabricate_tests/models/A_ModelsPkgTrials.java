package org.adligo.fabricate_tests.models;

import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.fabricate_tests.models.common.A_ModelsCommonPkgTrials;
import org.adligo.fabricate_tests.models.dependencies.A_ModelsDepsPkgTrials;
import org.adligo.fabricate_tests.models.fabricate.A_ModelsFabricatePkgTrials;
import org.adligo.fabricate_tests.models.project.A_ModelsProjectPkgTrials;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_ModelsPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_ModelsPkgTrials me = new A_ModelsPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.addAll(new A_ModelsCommonPkgTrials().getTrials());
    trials.addAll(new A_ModelsDepsPkgTrials().getTrials());
    trials.addAll(new A_ModelsFabricatePkgTrials().getTrials());
    trials.addAll(new A_ModelsProjectPkgTrials().getTrials());
    return trials;
  }
}
