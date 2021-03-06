package org.adligo.fabricate_tests.common.files.xml_io;

import org.adligo.fabricate_tests.etc.FabTestParamsFactory;
import org.adligo.tests4j.run.api.Tests4J;
import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialList;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j.system.shared.trials.I_Trial;

import java.util.ArrayList;
import java.util.List;

public class A_XmlIoTrials implements I_Tests4J_TrialList {
	
	public static void main(String [] args) {
		try {
			Tests4J_Params params = new FabTestParamsFactory().create();
			
			A_XmlIoTrials me = new A_XmlIoTrials();
			params.addTrials(me);
			
			Tests4J.run(params);
		} catch (Exception x) {
			x.printStackTrace();
		}
	}


  @Override
  public List<Class<? extends I_Trial>> getTrials() {
    List<Class<? extends I_Trial>> trials = new ArrayList<Class<? extends I_Trial>>();
    trials.add(I_FabXmlFilesTrial.class);
    
    trials.add(DevIOTrial.class);
    trials.add(LibraryIOTrial.class);
    
    trials.add(FabricateJaxbContextsTrial.class);
    trials.add(FabricateIOTrial.class);
    trials.add(FabricateXmlUsesTrial.class);
    
    trials.add(ProjectIOTrial.class);
    trials.add(DepotIOTrial.class);
    trials.add(FabXmlFilesTrial.class);
    trials.add(SchemaLoaderTrial.class);
    trials.add(XmlIoPackageTrial.class);
    trials.add(ResultIOTrial.class);
    
    return trials;
  }


}
