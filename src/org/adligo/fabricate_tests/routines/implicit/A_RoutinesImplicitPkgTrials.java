package org.adligo.fabricate_tests.routines.implicit;

import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_RoutinesImplicitPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_RoutinesImplicitPkgTrials me = new A_RoutinesImplicitPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    
    trials.add(AddFilesTaskTrial.class);
    
    trials.add(CommonBuildDirTrial.class);
    trials.add(CompileSourceTaskTrial.class);
    trials.add(CopyRoutineTrial.class);
    trials.add(CreateJarTaskTrial.class);
    
    trials.add(DecryptCommandTrial.class);
    trials.add(DecryptTraitTrial.class);
    trials.add(DependenciesFilterTrial.class);
    trials.add(DepositJarTaskTrial.class);
    trials.add(DoNothingRoutineTrial.class);
    trials.add(DownloadDependenciesRoutineTrial.class);
    
    trials.add(EncryptCommandTrial.class);
    trials.add(EncryptTraitTrial.class);
    
    trials.add(FabricateAwareRoutineTrial.class);
    trials.add(FindSrcTraitTrial.class);
    
    trials.add(GenerateSourceTaskTrial.class);
    trials.add(GitCheckoutRoutineTrial.class);
    trials.add(GitCloneRoutineTrial.class);
    trials.add(GitCommitCommandTrial.class);
    trials.add(GitPushRoutineTrial.class);
    trials.add(GitStageTaskTrial.class);
    trials.add(GitUpdateRoutineTrial.class);
    
    trials.add(I_JarFileNameAwareTrial.class);
    trials.add(I_ScmContextAwareTrial.class);
    
    trials.add(ImplicitArchiveStagesTrial.class);
    trials.add(ImplicitCommandsTrial.class);
    trials.add(ImplicitFacetsTrial.class);
    trials.add(ImplicitRoutineFactoryTrial.class);
    trials.add(ImplicitStagesTrial.class);
    trials.add(ImplicitTraitsTrial.class);
    
    trials.add(JarRoutineTrial.class);
    
    trials.add(LoadProjectTaskTrial.class);
    
    trials.add(NameJarTraitTrial.class);
    
    trials.add(ProjectAwareRoutineTrial.class);
    trials.add(ProjectBriefAwareRoutineTrial.class);
    trials.add(PublishCommandTrial.class);
    
    trials.add(ScmContextTrial.class);
    trials.add(ScmContextInputAwareRoutineTrial.class);
    trials.add(ScmRoutineTrial.class);
    trials.add(SetupProjectsRoutineTrial.class);
    
    trials.add(SshAgentHelperTrial.class);
    
    trials.add(RoutineFabricateFactoryTrial.class);
    return trials;
  }


}
