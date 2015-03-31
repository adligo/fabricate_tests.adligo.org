package org.adligo.fabricate_tests.routines;

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
    
    trials.add(AbstractRoutineTrial.class);
    
    trials.add(I_CommandAwareTrial.class);
    trials.add(I_ConcurrencyAwareTrial.class);
    trials.add(I_CoverageAwareTrial.class);
    trials.add(I_FabricateAwareTrial.class);
    trials.add(I_GenericTypeAwareTrial.class);
    
    trials.add(I_InputAwareTrial.class);
    
    trials.add(I_OutputProducerTrial.class);
    
    trials.add(I_PlatformAwareTrial.class);
    trials.add(I_ParticipationAwareTrial.class);
    trials.add(I_PresentationAwareTrial.class);
    trials.add(I_PresenterFactoryTrial.class);
    trials.add(I_ProjectAwareTrial.class);
    trials.add(I_ProjectBriefAwareTrial.class);
    trials.add(I_ProjectProcessorTrial.class);
    trials.add(I_ProjectBriefsAwareTrial.class);
    trials.add(I_ProjectsAwareTrial.class);
    
    trials.add(I_RepositoryFactoryAwareTrial.class);
    trials.add(I_RepositoryManagerAwareTrial.class);
    trials.add(I_RoutineBuilderTrial.class);
    trials.add(I_RoutineFabricateFactoryAwareTrial.class);
    trials.add(I_RoutineFabricateFactoryTrial.class);
    
    trials.add(I_TaskProcessorTrial.class);
    trials.add(I_TestsAwareTrial.class);
    
    trials.add(I_ViewAwareTrial.class);
    trials.add(I_ViewFactoryTrial.class);
    
    trials.add(RoutineBuilderTrial.class);
    trials.add(RoutineExecutionEngineTrial.class);
    trials.add(RoutineFactoryTrial.class);
    trials.add(RoutineLocationInfoTrial.class);
    trials.add(RoutinePopulatorMutantTrial.class);
    return trials;
  }


}
