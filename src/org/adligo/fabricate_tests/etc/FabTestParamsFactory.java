package org.adligo.fabricate_tests.etc;

import org.adligo.tests4j.system.shared.api.AbstractParamsFactory;
import org.adligo.tests4j.system.shared.api.Tests4J_Params;
import org.adligo.tests4j_4jacoco.plugin.factories.MockitoPluginFactory;

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
    params.setCoveragePluginFactoryClass(MockitoPluginFactory.class);
    return params;
  }

}
