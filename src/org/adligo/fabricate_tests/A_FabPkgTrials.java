package org.adligo.fabricate_tests;

import org.adligo.fabricate_tests.common.A_CommonPkgTrials;
import org.adligo.fabricate_tests.common.files.A_FilesTrials;
import org.adligo.fabricate_tests.depot.A_FabDepotTrials;
import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.fabricate_tests.models.A_ModelsPkgTrials;
import org.adligo.fabricate_tests.repository.A_RepositoryPkgTrials;
import org.adligo.fabricate_tests.routines.A_RoutinesPkgTrials;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_FabPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_FabPkgTrials me = new A_FabPkgTrials();
			params.addTrials(me);
			params.setMetaTrialClass(FabMetaTrial.class);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.addAll(new A_CommonPkgTrials().getTrials());
    trials.addAll(new A_FabDepotTrials().getTrials());
    trials.addAll(new A_FilesTrials().getTrials());
    trials.addAll(new A_ModelsPkgTrials().getTrials());
    trials.addAll(new A_RepositoryPkgTrials().getTrials());
    trials.addAll(new A_RoutinesPkgTrials().getTrials());
    
    trials.add(FabPackagesTrial.class);
    trials.add(FabricateFactoryTrial.class);
    trials.add(FabricateArgsSetupTrial.class);
    trials.add(FabricateOptsSetupTrial.class);
    trials.add(FabricateControllerTrial.class);
    return trials;
  }


}
