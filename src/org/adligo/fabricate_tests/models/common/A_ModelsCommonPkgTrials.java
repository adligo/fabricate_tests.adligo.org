package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_ModelsCommonPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_ModelsCommonPkgTrials me = new A_ModelsCommonPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    
    trials.add(AttributesOverlayTrial.class);
    
    trials.add(DuplicateRoutineExceptionTrial.class);
    
    trials.add(ExecutionEnvironmentMutantTrial.class);
    trials.add(ExecutionEnvironmentTrial.class);
    
    trials.add(I_AttributesContainerTrial.class);
    trials.add(I_AttributesOverlayTrial.class);
    
    trials.add(I_ExpectedRoutineInterfaceTrial.class);
    trials.add(I_ExecutionEnvironmentMutantTrial.class);
    trials.add(I_ExecutionEnvironmentTrial.class);
    trials.add(I_FabricationTrial.class);
    trials.add(I_FabricationMemoryTrial.class);
    trials.add(I_FabricationMemoryMutantTrial.class);
    trials.add(I_FabricationRoutineTrial.class);
    trials.add(I_ParameterTrial.class);
    trials.add(I_RoutineBriefTrial.class);
    trials.add(I_RoutineFactoryTrial.class);
    trials.add(I_RoutineMemoryMutantTrial.class);
    trials.add(I_RoutineMemoryTrial.class);
    trials.add(I_MemoryLockTrial.class);
    
    trials.add(ExpectedRoutineInterfaceTrial.class);
    trials.add(ExpectedRoutineInterfaceMutantTrial.class);
    
    trials.add(FabricationMemoryConstantsTrial.class);
    trials.add(FabricationMemoryTrial.class);
    trials.add(FabricationMemoryMutantTrial.class);
    trials.add(FabricationRoutineCreationExceptionTrial.class);
    
    trials.add(MemoryLockTrial.class);
    trials.add(ParameterMutantTrial.class);
    trials.add(ParameterTrial.class);
    
    trials.add(RoutineBriefMutantTrial.class);
    trials.add(RoutineBriefOriginTrial.class);
    trials.add(RoutineBriefTrial.class);
    
    
    return trials;
  }


}
