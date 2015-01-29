package org.adligo.fabricate_tests.common.system;

import org.adligo.fabricate.common.system.ExecutionResultMutant;
import org.adligo.fabricate.common.system.ProcessBuilderWrapper;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;
import java.io.IOException;

@SourceFileScope (sourceClass=ProcessBuilderWrapper.class)
public class ProcessBuilderWrapperTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testMethodsGetsAndSets() throws IOException {
    ProcessBuilder pb = new ProcessBuilder("echo","foo");
    ProcessBuilderWrapper pbr = new ProcessBuilderWrapper(pb);
    pbr.directory(new File("."));
    File dir = pb.directory();
    assertEquals(".", dir.getPath());
    
    assertSame(pb, pbr.getDelegate());
    pbr.redirectErrorStream(true);
    assertNotNull(pbr.start());
    
  }
}
