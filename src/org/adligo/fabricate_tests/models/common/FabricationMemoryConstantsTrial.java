package org.adligo.fabricate_tests.models.common;

import org.adligo.fabricate.models.common.FabricationMemoryConstants;
import org.adligo.fabricate.models.common.I_ExecutionEnvironment;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.Test;
import org.adligo.tests4j_4mockito.MockitoSourceFileTrial;

import java.util.HashMap;
import java.util.Map;

@SourceFileScope (sourceClass=FabricationMemoryConstants.class, minCoverage=72.0)
public class FabricationMemoryConstantsTrial extends MockitoSourceFileTrial {

  @SuppressWarnings("boxing")
  @Test
  public void testConstants() {
    assertEquals("gitClonedProjects", FabricationMemoryConstants.CLONED_PROJECTS);
    assertEquals("environment", FabricationMemoryConstants.ENV);
    assertEquals("gitKeystorePassword", FabricationMemoryConstants.GIT_KEYSTORE_PASSWORD);
    assertEquals("setupSshAgent", FabricationMemoryConstants.SETUP_SSH_AGENT);
    I_ExecutionEnvironment executionEnv = FabricationMemoryConstants.EMPTY_ENV;
    Map<String,String> map = new HashMap<String,String>();
    executionEnv.addAllTo(map);
    assertEquals(0, map.size());
    FabricationMemoryConstants inst = FabricationMemoryConstants.INSTANCE;
    assertNotNull(inst);
  }
}
