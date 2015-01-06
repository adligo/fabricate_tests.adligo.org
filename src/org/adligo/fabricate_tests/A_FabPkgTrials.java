package org.adligo.fabricate_tests;

import org.adligo.fabricate_tests.common.en.A_FabEnPkgTrials;
import org.adligo.fabricate_tests.common.i18n.A_FabI18nPkgTrials;
import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.fabricate_tests.files.xml_io.A_XmlIoTrials;
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
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.add(FabPackagesTrial.class);
    trials.add(FabricateSetupTrial.class);
    trials.addAll(new A_FabEnPkgTrials().getTrials());
    trials.addAll(new A_FabI18nPkgTrials().getTrials());
    trials.addAll(new A_XmlIoTrials().getTrials());
    return trials;
  }


}
