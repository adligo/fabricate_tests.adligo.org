package org.adligo.fabricate_tests.etc;

import org.adligo.tests4j.system.shared.api.AbstractParamsFactory;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j_4jacoco.plugin.factories.Tests4J_4MockitoPluginFactory;
import org.adligo.tests4j_4jacoco.plugin.instrumentation.TrialInstrumenter;

import java.util.ArrayList;
import java.util.List;

public class FabTestParamsFactory extends AbstractParamsFactory {

  @Override
  public Tests4J_Params create() {
    Tests4J_Params params = new Tests4J_Params();
    List<String> nonInstrumentedPackages = new ArrayList<String>();
    nonInstrumentedPackages.add("org.apache.");
    //somewhere in here I am using a System class loader
    //in stead of a current class loader, not sure where
    nonInstrumentedPackages.add("org.adligo.fabricate.xml.io_v1.common_v1_0.");
    nonInstrumentedPackages.add("org.adligo.fabricate.xml.io_v1.depot_v1_0.");
    nonInstrumentedPackages.add("org.adligo.fabricate.xml.io_v1.dev_v1_0.");
    nonInstrumentedPackages.add("org.adligo.fabricate.xml.io_v1.fabricate_v1_0.");
    nonInstrumentedPackages.add("org.adligo.fabricate.xml.io_v1.library_v1_0.");
    nonInstrumentedPackages.add("org.adligo.fabricate.xml.io_v1.project_v1_0.");
    nonInstrumentedPackages.add("org.adligo.fabricate.xml.io_v1.result_v1_0.");
   
    params.setAdditionalNonInstrumentedPackages(nonInstrumentedPackages);
    
    //params.setCoveragePluginFactoryClass(MockitoPluginFactory.class);
    
    params.setCoveragePluginFactoryClass(Tests4J_4MockitoPluginFactory.class);
    
    //params.setLogState(Tests4J_NotificationManager.class, true);
    //params.setLogState(ClassAndDependenciesInstrumenter.class, true);
    //params.setLogState(TrialInstrumenter.class, true);
    //params.setLogState(Recorder.class, true);
    //params.setLogState(CallJacocoInit.class, true);
    params.setLogState(TrialInstrumenter.class, true);
    return params;
  }

}
