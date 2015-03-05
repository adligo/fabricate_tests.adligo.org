package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.ProcessOutputData;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

@SourceFileScope (sourceClass=ProcessOutputData.class, minCoverage=100.00)
public class ProcessOutputDataTrial extends MockitoSourceFileTrial {

  @Test
  public void testConstructorAndGetters() {
    ProcessOutputData pod = new ProcessOutputData("data", "charSet");
    assertEquals("data", pod.getData());
    assertEquals("charSet", pod.getCharSet());
  }
}
