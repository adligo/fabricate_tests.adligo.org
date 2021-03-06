package org.adligo.fabricate_tests.common.i18n;

import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_FabI18nPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_FabI18nPkgTrials me = new A_FabI18nPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.add(I_AttributeConstantsTrial.class);
    trials.add(I_CommandLineConstantsTrial.class);
    trials.add(I_FabricateConstantsTrial.class);
    trials.add(I_FileMessagesTrial.class);
    trials.add(I_GitMessagesTrial.class);
    trials.add(I_ImplicitTraitMessagesTrial.class);
    trials.add(I_ProjectMessagesTrial.class);
    trials.add(I_SystemMessagesTrial.class);
    return trials;
  }


}
