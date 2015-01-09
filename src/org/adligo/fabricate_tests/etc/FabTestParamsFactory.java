package org.adligo.fabricate_tests.etc;

import org.adligo.tests4j.run.helpers.Tests4J_NotificationManager;
import org.adligo.tests4j.system.shared.api.AbstractParamsFactory;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j_4jacoco.plugin.Recorder;
import org.adligo.tests4j_4jacoco.plugin.factories.Tests4J_4MockitoPluginFactory;
import org.adligo.tests4j_4jacoco.plugin.instrumentation.ClassAndDependenciesInstrumenter;
import org.adligo.tests4j_4jacoco.plugin.instrumentation.TrialInstrumenter;
import org.adligo.tests4j_4jacoco.plugin.instrumentation.common.CallJacocoInit;

import java.util.ArrayList;
import java.util.List;

public class FabTestParamsFactory extends AbstractParamsFactory {

  @Override
  public Tests4J_Params create() {
    Tests4J_Params params = new Tests4J_Params();
    List<String> nonInstrumentedPackages = new ArrayList<String>();
    nonInstrumentedPackages.add("org.adligo.tests4j.");
    nonInstrumentedPackages.add("org.adligo.tests4j_4mockito.");
    nonInstrumentedPackages.add("org.adligo.tests4j_tests.");
    nonInstrumentedPackages.add("org.apache.");
    params.setAdditionalNonInstrumentedPackages(nonInstrumentedPackages);
    
    
    //params.setCoveragePluginFactoryClass(MockitoPluginFactory.class);
    params.setCoveragePluginFactoryClass(Tests4J_4MockitoPluginFactory.class);
    
    params.setLogState(Tests4J_NotificationManager.class, true);
    params.setLogState(ClassAndDependenciesInstrumenter.class, true);
    params.setLogState(TrialInstrumenter.class, true);
    params.setLogState(Recorder.class, true);
    params.setLogState(CallJacocoInit.class, true);
    //params.setLogState(MultiProbeDataStore.class, true);
    return params;
  }

}
