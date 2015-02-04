package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.FabricateDefaults;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.io.File;

@SourceFileScope (sourceClass=FabricateDefaults.class, minCoverage=100.0)
public class FabricateDefaultsTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstants() {
    assertEquals("16m", FabricateDefaults.JAVA_XMS_DEFAULT);
    assertEquals("64m", FabricateDefaults.JAVA_XMX_DEFAULT);
    assertEquals(Runtime.getRuntime().availableProcessors() * 2,
        FabricateDefaults.JAVA_THREADS);
    
    assertEquals(System.getProperty("user.home") + 
        File.separator + "local_repository", FabricateDefaults.LOCAL_REPOSITORY);
  }
  
}
