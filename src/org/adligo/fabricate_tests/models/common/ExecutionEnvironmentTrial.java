package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.fabricate.models.common.ExecutionEnvironment;
import org.adligo.fabricate.models.common.ExecutionEnvironmentMutant;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.HashMap;
import java.util.Map;

@SourceFileScope (sourceClass=ExecutionEnvironment.class, minCoverage=100.0,
allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class ExecutionEnvironmentTrial extends MockitoSourceFileTrial {

  @Test
  public void testMethodAddAllTo() {
    ExecutionEnvironmentMutant fmm = new ExecutionEnvironmentMutant(SystemEnMessages.INSTANCE);
    
    fmm.put("key", "value");
    ExecutionEnvironment ee = new ExecutionEnvironment(fmm);
    
    Map<String,String> map = new HashMap<String,String>();
    ee.addAllTo(map);
    assertEquals("value", map.get("key"));
  }
}
