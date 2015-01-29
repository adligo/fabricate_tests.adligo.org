package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.ExecutionResultMutant;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ExecutionResultMutant.class)
public class ExecutionResultMutantTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetsAndSets() {
     ExecutionResultMutant erm = new ExecutionResultMutant();
     erm.setExitCode(0);
     assertEquals(0, erm.getExitCode());
     
     erm.setOutput("out");
     assertEquals("out", erm.getOutput());
     
     erm.setExitCode(1);
     assertEquals(1, erm.getExitCode());
     
     erm.setOutput("outA");
     assertEquals("outA", erm.getOutput());
  }
}
