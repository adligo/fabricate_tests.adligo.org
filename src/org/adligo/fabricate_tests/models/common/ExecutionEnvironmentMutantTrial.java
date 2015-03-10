package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.common.en.SystemEnMessages;
import org.adligo.fabricate.models.common.ExecutionEnvironmentMutant;
import org.adligo.fabricate.models.common.MemoryLock;
import org.adligo.tests4j.shared.asserts.common.ExpectedThrowable;
import org.adligo.tests4j.shared.asserts.common.I_Thrower;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SourceFileScope (sourceClass=ExecutionEnvironmentMutant.class, minCoverage=100.0,
allowedCircularDependencies=CircularDependencies.AllowInPackage)
public class ExecutionEnvironmentMutantTrial extends MockitoSourceFileTrial {

  @Test
  public void testContstructor() {
    ExecutionEnvironmentMutant fmm = new ExecutionEnvironmentMutant(SystemEnMessages.INSTANCE);
    
    assertThrown(new ExpectedThrowable(new IllegalArgumentException(SystemEnMessages.INSTANCE.getLocksMustContainAtLeastOneAllowedCaller())),
        new I_Thrower() {
          
          @Override
          public void run() throws Throwable {
            fmm.addLock(new MemoryLock("key", Collections.singleton(null)));
          }
        });
  }
  
  @Test
  public void testMethodAddAllTo() {
    ExecutionEnvironmentMutant fmm = new ExecutionEnvironmentMutant(SystemEnMessages.INSTANCE);
    fmm.put("key", "value");
    
    Map<String,String> map = new HashMap<String,String>();
    fmm.addAllTo(map);
    assertEquals("value", map.get("key"));
  }
}
