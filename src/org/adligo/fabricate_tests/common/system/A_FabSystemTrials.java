package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.I_RunMonitor;
import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_FabSystemTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_FabSystemTrials me = new A_FabSystemTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.add(I_ExecutionResultTrial.class);
    trials.add(I_ExecutorTrial.class);
    trials.add(I_LocatableRunableTrial.class);
    trials.add(I_FabSystemTrial.class);
    trials.add(I_RunMonitorTrial.class);
    
    trials.add(BufferedInputStreamTrial.class);
    trials.add(ComputerInfoDiscoveryTrial.class);
    trials.add(ExecutionResultMutantTrial.class);
    
    trials.add(FabSystemTrial.class);
    trials.add(FabSystemSetupTrial.class);
    trials.add(FabricateDefaultsTrial.class);
    trials.add(FabricateXmlDiscoveryTrial.class);
    trials.add(FabricateEnvironmentTrial.class);

    
    
    trials.add(ProcessBuilderWrapperTrial.class);
    trials.add(RunMonitorTrial.class);
    
    trials.add(ExecutorTrial.class);
    
    trials.add(CommandLineArgsTrial.class);
    return trials;
  }


}
