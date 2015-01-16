package org.adligo.fabricate_tests.common;

import org.adligo.fabricate_tests.common.en.A_FabEnPkgTrials;
import org.adligo.fabricate_tests.common.i18n.A_FabI18nPkgTrials;
import org.adligo.fabricate_tests.common.log.A_LogPkgTrials;
import org.adligo.fabricate_tests.common.log.ThreadLocalPrintStreamTrial;
import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_CommonPkgTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_CommonPkgTrials me = new A_CommonPkgTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    // can't test yet circular dependency
    //trials.add(FabricateXmlDiscoveryTrial.class);
    trials.addAll(new A_FabEnPkgTrials().getTrials());
    trials.addAll(new A_FabI18nPkgTrials().getTrials());
    trials.addAll(new A_LogPkgTrials().getTrials());
    return trials;
  }


}
